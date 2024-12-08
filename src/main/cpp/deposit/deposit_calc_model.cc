#include "deposit_calc_model.h"

#include "../smart_calc/smart_calc_model.h"

namespace s21 {

void DepositCalcModel::CalculateDeposit() {
  double capitalized = 0;
  interest_ = 0;
  for (int i = 0; i < month_term_; ++i) {
    deposit_amount_ += replenishment_[i];
    if (deposit_amount_ <= withdrawals_[i]) {
      throw std::out_of_range("Unable to withdraw more than deposit amount");
    }
    deposit_amount_ -= withdrawals_[i];
    double monthly_interest = deposit_amount_ * interest_rate_ / 12 / 100;
    if (is_capitalized_) {
      DepositCapitalization(&capitalized, monthly_interest, i);
    }
    interest_ += monthly_interest;
  }
  if (!is_capitalized_) {
    deposit_amount_ += interest_;
  }
  deposit_amount_ += capitalized;
  taxes_ = interest_ * tax_rate_ / 100;
  deposit_amount_ -= taxes_;
}

void DepositCalcModel::SetDepositAmount(double deposit_amount) {
  deposit_amount_ = deposit_amount;
}

void DepositCalcModel::SetInterestRate(double interest_rate) {
  interest_rate_ = interest_rate;
}

void DepositCalcModel::SetTaxRate(double tax_rate) { tax_rate_ = tax_rate; }

void DepositCalcModel::SetMonthTerm(int month_term) {
  month_term_ = month_term;
}

void DepositCalcModel::SetIsCapitalized(bool is_capitalized) {
  is_capitalized_ = is_capitalized;
}

void DepositCalcModel::SetCapitalizationPeriodicity(
    int capitalization_periodicity) {
  capitalization_periodicity_ = capitalization_periodicity;
}

double DepositCalcModel::GetDepositAmount() const { return deposit_amount_; }

double DepositCalcModel::GetInterest() const { return interest_; }

double DepositCalcModel::GetTaxes() const { return taxes_; }

void DepositCalcModel::AddReplenishment(int month, double value) {
  replenishment_[month] += value;
}

void DepositCalcModel::AddWithdrawal(int month, double value) {
  withdrawals_[month] += value;
}

void DepositCalcModel::Clear() {
  replenishment_.clear();
  withdrawals_.clear();
}

void DepositCalcModel::DepositCapitalization(double *capitalized,
                                             double monthly_interest,
                                             int month) {
  *capitalized += monthly_interest;
  if (!capitalization_periodicity_) {
    deposit_amount_ += *capitalized;
    *capitalized = 0;
  } else if (capitalization_periodicity_ == 1) {
    if (!(month % 4)) {
      deposit_amount_ += *capitalized;
      *capitalized = 0;
    }
  } else if (capitalization_periodicity_ == 2) {
    if (!(month % 6)) {
      deposit_amount_ += *capitalized;
      *capitalized = 0;
    }
  } else {
    if (!(month % 12)) {
      deposit_amount_ += *capitalized;
      *capitalized = 0;
    }
  }
}

} // namespace s21