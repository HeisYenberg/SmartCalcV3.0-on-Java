package edu.school21.butterba.smartcalc.presenter;

import edu.school21.butterba.smartcalc.model.core.DepositCalcModel;
import edu.school21.butterba.smartcalc.view.controllers.DepositCalcController;
import java.text.DecimalFormat;

public class DepositCalcPresenter {

  private final DepositCalcModel model;
  private final DepositCalcController view;

  private final DecimalFormat format = new DecimalFormat("#.##");

  public DepositCalcPresenter(final DepositCalcModel model, final DepositCalcController view) {
    this.model = model;
    this.view = view;
  }

  public void replenish() {
    Integer month = view
        .getReplenishmentMonth()
        .getValue();
    Double amount = view
        .getReplenishmentAmount()
        .getValue();
    if (month == 0 || amount == 0) {
      return;
    }
    model.addReplenishment(month, amount);
    view
        .getReplenishments()
        .getItems()
        .add(month + ") " + format.format(amount));
  }

  public void withdraw() {
    Integer month = view
        .getWithdrawalMonth()
        .getValue();
    Double amount = view
        .getWithdrawalAmount()
        .getValue();
    if (month == 0 || amount == 0) {
      return;
    }
    model.addWithdrawal(month, amount);
    view
        .getWithdrawals()
        .getItems()
        .add(month + ") " + format.format(amount));
  }

  public void reset() {
    model.clear();
    view
        .getReplenishments()
        .getItems()
        .clear();
    view
        .getWithdrawals()
        .getItems()
        .clear();
  }

  public void calculate() {
    model.setDepositAmount(view
        .getDepositAmount()
        .getValue());
    model.setMonthTerm(view
        .getMonthlyTerm()
        .getValue());
    model.setInterestRate(view
        .getInterestRate()
        .getValue());
    model.setTaxRate(view
        .getTaxRate()
        .getValue());
    model.setIsCapitalized(view
        .getCapitalization()
        .isSelected());
    model.setCapitalizationPeriodicity(view
        .getPeriodicity()
        .getValue()
        .ordinal());
    try {
      model.calculateDeposit();
      view
          .getAccruedInterest()
          .setText(format.format(model.getInterest()));
      view
          .getTaxAmount()
          .setText(format.format(model.getTaxes()));
      view
          .getDepositEndAmount()
          .setText(format.format(model.getDepositAmount()));
    } catch (RuntimeException e) {
      view
          .getDepositEndAmount()
          .setText(e.getMessage());
    }
  }
}
