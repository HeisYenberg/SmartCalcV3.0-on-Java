#ifndef APJ2_SMARTCALC_V3_0_DESKTOP_JAVA_SMART_CALC_CPP_SMART_CALC_SMART_CALC_MODEL_H_
#define APJ2_SMARTCALC_V3_0_DESKTOP_JAVA_SMART_CALC_CPP_SMART_CALC_SMART_CALC_MODEL_H_

#include <stack>
#include <string>

namespace s21 {

class SmartCalcModel {
 public:
  SmartCalcModel() = default;
  ~SmartCalcModel() = default;
  void SmartCalc();
  void SetExpression(const std::string &expression);
  void SetXValue(double x);
  std::string GetExpression() const;
  double GetResult() const;

 private:
  struct Lexeme {
    char type;
    double value;
  };

  std::string expression_;
  double x_{};
  double result_{};
  std::stack<Lexeme> numbers_;
  std::stack<Lexeme> operations_;

  void CleanStacks();
  void ParseExpression();
  char HandleUnarySign(char type, char last_read);
  void ReadMod(std::size_t *pos);
  std::size_t ReadDigit(const std::string &expression);
  void ReadBinaryOperation(char type, char *last_read, std::size_t *pos);
  void ReadBrackets(char type);
  std::size_t ReadFunction(const std::string &expression);
  int CheckRank(char type);

  void Calculate();
  void BinaryCalculation();
  void FunctionCalculation();
};

}  // namespace s21

#endif  // APJ2_SMARTCALC_V3_0_DESKTOP_JAVA_SMART_CALC_CPP_SMART_CALC_SMART_CALC_MODEL_H_
