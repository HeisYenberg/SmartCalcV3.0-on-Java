#include "credit_calc_model.h"

#include <cmath>

namespace s21 {

void CreditCalcModel::CalculateCredit() {
  monthly_payments_.clear();
  if (!is_differentiated_) {
    double coefficient = (interest_rate_ * pow(1 + interest_rate_, term_)) /
                         (pow(1 + interest_rate_, term_) - 1);
    monthly_payments_.push_back(credit_amount_ * coefficient);
    total_payment_ = monthly_payments_.back() * term_;
    overpayment_ = total_payment_ - credit_amount_;
  } else {
    CalculateDifferentiated();
  }
}

void CreditCalcModel::SetCreditAmount(double credit_amount) {
  credit_amount_ = credit_amount;
}

void CreditCalcModel::SetInterestRate(double interest_rate) {
  interest_rate_ = interest_rate / 12 / 100;
}

void CreditCalcModel::SetTerm(int term) { term_ = term; }

void CreditCalcModel::SetIsDifferentiated(bool is_differentiated) {
  is_differentiated_ = is_differentiated;
}

double CreditCalcModel::GetOverpayment() const { return overpayment_; }

double CreditCalcModel::GetTotalPayment() const { return total_payment_; }

std::vector<double> CreditCalcModel::GetMonthlyPayments() const {
  return monthly_payments_;
}

void CreditCalcModel::CalculateDifferentiated() {
  total_payment_ = 0;
  double monthly_payment = credit_amount_ / term_;
  double payment_left = credit_amount_;
  for (int i = 0; i < term_; ++i) {
    monthly_payments_.push_back(monthly_payment +
                                payment_left * interest_rate_);
    payment_left -= monthly_payment;
    total_payment_ += monthly_payments_.back();
  }
  overpayment_ = total_payment_ - credit_amount_;
}

}  // namespace s21