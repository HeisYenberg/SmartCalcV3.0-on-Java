package edu.school21.butterba.smartcalc.model.core;

import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Name;
import org.bytedeco.javacpp.annotation.Namespace;
import org.bytedeco.javacpp.annotation.Platform;
import org.bytedeco.javacpp.annotation.StdVector;

@Platform(include = "credit/credit_calc_model.h", link = "smart_calc")
@Namespace("s21")
public class CreditCalcModel extends Pointer {

  static {
    Loader.load();
  }

  public CreditCalcModel() {
    allocate();
  }

  private native void allocate();

  @Name("CalculateCredit")
  public native void calculateCredit();

  @Name("SetCreditAmount")
  public native void setCreditAmount(final double creditAmount);

  @Name("SetInterestRate")
  public native void setInterestRate(final double interestRate);

  @Name("SetTerm")
  public native void setTerm(final int term);

  @Name("SetIsDifferentiated")
  public native void setIsDifferentiated(final boolean isDifferentiated);

  @Name("GetOverpayment")
  public native double getOverpayment();

  @Name("GetTotalPayment")
  public native double getTotalPayment();

  @Name("GetMonthlyPayments")
  public native @StdVector DoublePointer getMonthlyPayments();
}
