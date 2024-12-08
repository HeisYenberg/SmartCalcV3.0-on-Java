#include "smart_calc_model.h"

#include <cmath>
#include <locale>

namespace s21 {

void SmartCalcModel::SmartCalc() {
  setlocale(LC_ALL, "C");
  std::string temp(expression_);
  CleanStacks();
  ParseExpression();
  while (!operations_.empty()) {
    Calculate();
  }
  if (!numbers_.empty()) {
    result_ = numbers_.top().value;
    numbers_.pop();
    if (!numbers_.empty()) {
      throw std::out_of_range("Incorrect Expression");
    }
  }
  expression_ = std::move(temp);
}

void SmartCalcModel::SetExpression(const std::string &expression) {
  expression_ = expression;
}

std::string SmartCalcModel::GetExpression() const { return expression_; }

void SmartCalcModel::SetXValue(double x) { x_ = x; }

double SmartCalcModel::GetResult() const { return result_; }

void SmartCalcModel::CleanStacks() {
  while (!numbers_.empty()) {
    numbers_.pop();
  }
  while (!operations_.empty()) {
    operations_.pop();
  }
}

void SmartCalcModel::ParseExpression() {
  char last_read = 0;
  std::size_t expression_length = expression_.size();
  for (std::size_t i = 0; i < expression_length; ++i) {
    if (isspace(expression_[i])) {
      continue;
    } else if (strchr("acstl", expression_[i])) {
      last_read = expression_[i];
      std::size_t read = ReadFunction(expression_.substr(i));
      if (read != 0) {
        i += read;
      } else {
        throw std::out_of_range("Incorrect Expression");
      }
    } else if (isdigit(expression_[i]) || expression_[i] == 'x') {
      if (isdigit(last_read) || last_read == 'x') {
        throw std::out_of_range("Incorrect Expression");
      }
      i += ReadDigit(expression_.substr(i));
      last_read = expression_[i];
    } else if (expression_[i] == 'm') {
      ReadMod(&i);
    } else if (strchr("+-*%/^", expression_[i])) {
      expression_[i] = HandleUnarySign(expression_[i], last_read);
      ReadBinaryOperation(expression_[i], &last_read, &i);
    } else if (expression_[i] == '(' || expression_[i] == ')') {
      last_read = expression_[i];
      ReadBrackets(expression_[i]);
    } else {
      throw std::out_of_range("Incorrect Expression");
    }
  }
}

char SmartCalcModel::HandleUnarySign(char type, char last_read) {
  if (!isdigit(last_read) && last_read != 'x' && last_read != ')' &&
      (type == '-' || type == '+')) {
    if (type == '+') {
      type = '#';
    } else {
      type = '~';
    }
  }
  return type;
}

void SmartCalcModel::ReadMod(std::size_t *pos) {
  if (!expression_.compare(*pos, 3, "mod")) {
    expression_[*pos + 2] = '%';
    ++(*pos);
  } else {
    throw std::out_of_range("Incorrect Expression");
  }
}

std::size_t SmartCalcModel::ReadDigit(const std::string &expression) {
  std::size_t read = 0;
  double value = x_;
  if (expression[0] != 'x') {
    value = std::stod(expression, &read);
    --read;
  }
  numbers_.push({0, value});
  return read;
}

void SmartCalcModel::ReadBinaryOperation(char type, char *last_read,
                                         std::size_t *pos) {
  int previous_rank =
      (operations_.empty()) ? 0 : CheckRank(operations_.top().type);
  if (previous_rank && CheckRank(type) != 3 &&
      CheckRank(type) <= previous_rank) {
    Calculate();
    --(*pos);
  } else {
    *last_read = type;
    operations_.push({type, 0});
  }
}

void SmartCalcModel::ReadBrackets(char type) {
  if (type == '(') {
    operations_.push({type, 0});
  } else {
    if (operations_.empty()) {
      throw std::out_of_range("Incorrect Expression");
    }
    while (operations_.top().type != '(') {
      Calculate();
      if (operations_.empty()) {
        throw std::out_of_range("Incorrect Expression");
      }
    }
    if (!operations_.empty()) {
      operations_.pop();
    }
  }
}

std::size_t SmartCalcModel::ReadFunction(const std::string &expression) {
  std::size_t read = 0;
  char type = 0;
  if (!expression.compare(0, 5, "asin(")) {
    read += 3;
    type = 'S';
  } else if (!expression.compare(0, 5, "acos(")) {
    read += 3;
    type = 'C';
  } else if (!expression.compare(0, 5, "atan(")) {
    read += 3;
    type = 'T';
  } else if (!expression.compare(0, 4, "sin(")) {
    read += 2;
    type = 's';
  } else if (!expression.compare(0, 4, "cos(")) {
    read += 2;
    type = 'c';
  } else if (!expression.compare(0, 4, "tan(")) {
    read += 2;
    type = 't';
  } else if (!expression.compare(0, 4, "log(")) {
    read += 2;
    type = 'L';
  } else if (!expression.compare(0, 3, "ln(")) {
    read += 1;
    type = 'l';
  } else if (!expression.compare(0, 5, "sqrt(")) {
    read += 3;
    type = 'q';
  }
  operations_.push({type, 0});
  return read;
}

int SmartCalcModel::CheckRank(char type) {
  int rang = 0;
  if (type == '-' || type == '+')
    rang = 1;
  else if (type == '/' || type == '*' || type == '%')
    rang = 2;
  else if (type == '^')
    rang = 3;
  else if (type == '~' || type == '#')
    rang = 4;
  else if (type == 's' || type == 'c' || type == 't' || type == 'S' ||
           type == 'C' || type == 'T' || type == 'L' || type == 'l' ||
           type == 'q') {
    rang = 5;
  }
  return rang;
}

void SmartCalcModel::Calculate() {
  char type = operations_.top().type;
  if (strchr("+-*/%^", type)) {
    BinaryCalculation();
  } else if (strchr("~#sctSCTLlq", type)) {
    FunctionCalculation();
  } else {
    throw std::out_of_range("Incorrect Expression");
  }
}

void SmartCalcModel::BinaryCalculation() {
  if (numbers_.empty()) {
    throw std::out_of_range("Incorrect Expression");
  }
  char type = operations_.top().type;
  double a = numbers_.top().value;
  numbers_.pop();
  if (numbers_.empty()) {
    throw std::out_of_range("Incorrect Expression");
  }
  double b = numbers_.top().value;
  numbers_.pop();
  double c = 0;
  if (type == '-') {
    c = b - a;
  } else if (type == '+') {
    c = b + a;
  } else if (type == '*') {
    c = b * a;
  } else if (type == '/') {
    if (!a) {
      throw std::out_of_range("Division By Zero");
    }
    c = b / a;
  } else if (type == '%') {
    if (!a) {
      throw std::out_of_range("Division By Zero");
    }
    c = fmod(b, a);
  } else if (type == '^') {
    c = pow(b, a);
  }
  if (std::isnan(c)) {
    throw std::out_of_range("Calculation Error");
  }
  numbers_.push({0, c});
  operations_.pop();
}

void SmartCalcModel::FunctionCalculation() {
  if (numbers_.empty()) {
    throw std::out_of_range("Incorrect Expression");
  }
  char type = operations_.top().type;
  double a = numbers_.top().value;
  numbers_.pop();
  operations_.pop();
  double c = 0;
  if (type == '#') {
    c = a;
  } else if (type == '~') {
    c = -a;
  } else if (type == 's') {
    c = sin(a);
  } else if (type == 'c') {
    c = cos(a);
  } else if (type == 't') {
    c = tan(a);
  } else if (type == 'S') {
    c = asin(a);
  } else if (type == 'C') {
    c = acos(a);
  } else if (type == 'T') {
    c = atan(a);
  } else if (type == 'L') {
    c = log10(a);
  } else if (type == 'l') {
    c = log(a);
  } else if (type == 'q') {
    c = sqrt(a);
  }
  if (std::isnan(c)) {
    throw std::out_of_range("Calculation Error");
  }
  numbers_.push({0, c});
}

}  // namespace s21
