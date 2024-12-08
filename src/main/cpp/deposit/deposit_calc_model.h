#ifndef APJ2_SMARTCALC_V3_0_DESKTOP_JAVA_SMART_CALC_CPP_DEPOSIT_DEPOSIT_CALC_MODEL_H_
#define APJ2_SMARTCALC_V3_0_DESKTOP_JAVA_SMART_CALC_CPP_DEPOSIT_DEPOSIT_CALC_MODEL_H_

#include <map>

namespace s21 {

class DepositCalcModel {
public:
  DepositCalcModel() = default;
  ~DepositCalcModel() = default;

  void CalculateDeposit();
  void SetDepositAmount(double deposit_amount);
  void SetInterestRate(double interest_rate);
  void SetTaxRate(double tax_rate);
  void SetMonthTerm(int month_term);
  void SetIsCapitalized(bool is_capitalized);
  void SetCapitalizationPeriodicity(int capitalization_periodicity);
  double GetDepositAmount() const;
  double GetInterest() const;
  double GetTaxes() const;
  void AddReplenishment(int month, double value);
  void AddWithdrawal(int month, double value);
  void Clear();

private:
  std::map<int, double> replenishment_{};
  std::map<int, double> withdrawals_{};
  double interest_rate_{};
  double tax_rate_{};
  int month_term_{};
  bool is_capitalized_{};
  int capitalization_periodicity_{};
  double deposit_amount_{};
  double interest_{};
  double taxes_{};

  void DepositCapitalization(double *capitalized, double monthly_interest,
                             int month);
};

} // namespace s21

#endif // APJ2_SMARTCALC_V3_0_DESKTOP_JAVA_SMART_CALC_CPP_DEPOSIT_DEPOSIT_CALC_MODEL_H_