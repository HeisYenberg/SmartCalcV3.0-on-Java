#ifndef APJ2_SMARTCALC_V3_0_DESKTOP_JAVA_SMART_CALC_CPP_CREDIT_CREDIT_CALC_MODEL_H_
#define APJ2_SMARTCALC_V3_0_DESKTOP_JAVA_SMART_CALC_CPP_CREDIT_CREDIT_CALC_MODEL_H_

#include <vector>

namespace s21 {

class CreditCalcModel {
 public:
  CreditCalcModel() = default;
  ~CreditCalcModel() = default;

  void CalculateCredit();
  void SetCreditAmount(double credit_amount);
  void SetInterestRate(double interest_rate);
  void SetTerm(int term);
  void SetIsDifferentiated(bool is_differentiated);
  double GetOverpayment() const;
  double GetTotalPayment() const;
  std::vector<double> GetMonthlyPayments() const;

 private:
  double credit_amount_;
  double interest_rate_;
  int term_;
  bool is_differentiated_;
  double overpayment_{};
  double total_payment_{};
  std::vector<double> monthly_payments_;

  void CalculateDifferentiated();
};

}  // namespace s21

#endif  // APJ2_SMARTCALC_V3_0_DESKTOP_JAVA_SMART_CALC_CPP_CREDIT_CREDIT_CALC_MODEL_H_