package edu.school21.butterba.smartcalc.presenter;

import edu.school21.butterba.smartcalc.model.core.CreditCalcModel;
import edu.school21.butterba.smartcalc.view.controllers.CreditCalcController;
import java.text.DecimalFormat;
import org.bytedeco.javacpp.DoublePointer;

public class CreditCalcPresenter {

  private final CreditCalcModel model;
  private final CreditCalcController view;
  private final DecimalFormat format = new DecimalFormat("#.##");

  public CreditCalcPresenter(final CreditCalcModel model, final CreditCalcController view) {
    this.model = model;
    this.view = view;
  }

  public void calculate() {
    Double creditAmount = view
        .getCreditAmount()
        .getValue();
    Integer monthTerm = view
        .getMonthTerm()
        .getValue();
    Double interestRate = view
        .getInterestRate()
        .getValue();
    boolean isDifferentiated = view
        .getCreditType()
        .getValue()
        .equals("Differentiated");
    if (creditAmount == 0 || monthTerm == 0 || interestRate == 0) {
      view
          .getTotalPayment()
          .setText("Incorrect input");
      return;
    }
    model.setCreditAmount(creditAmount);
    model.setTerm(monthTerm);
    model.setInterestRate(interestRate);
    model.setIsDifferentiated(isDifferentiated);
    model.calculateCredit();
    view
        .getOverpayment()
        .setText(format.format(model.getOverpayment()));
    view
        .getTotalPayment()
        .setText(format.format(model.getTotalPayment()));
    view
        .getMonthlyPayments()
        .getItems()
        .clear();
    DoublePointer payments = model.getMonthlyPayments();
    for (int i = 0; i < payments.capacity(); ++i) {
      view
          .getMonthlyPayments()
          .getItems()
          .add(format.format(payments.get(i)));
    }
  }
}
