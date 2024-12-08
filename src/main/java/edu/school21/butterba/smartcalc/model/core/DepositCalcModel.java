package edu.school21.butterba.smartcalc.model.core;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Name;
import org.bytedeco.javacpp.annotation.Namespace;
import org.bytedeco.javacpp.annotation.Platform;

@Platform(include = "deposit/deposit_calc_model.h", link = "smart_calc")
@Namespace("s21")
public class DepositCalcModel extends Pointer {

  static {
    Loader.load();
  }

  public DepositCalcModel() {
    allocate();
  }

  private native void allocate();

  @Name("CalculateDeposit")
  public native void calculateDeposit();

  @Name("SetInterestRate")
  public native void setInterestRate(final double interest_rate);

  @Name("SetTaxRate")
  public native void setTaxRate(final double tax_rate);

  @Name("SetMonthTerm")
  public native void setMonthTerm(final int month_term);

  @Name("SetIsCapitalized")
  public native void setIsCapitalized(final boolean is_capitalized);

  @Name("SetCapitalizationPeriodicity")
  public native void setCapitalizationPeriodicity(
      final int capitalization_periodicity);

  @Name("GetDepositAmount")
  public native double getDepositAmount();

  @Name("SetDepositAmount")
  public native void setDepositAmount(final double deposit_amount);

  @Name("GetInterest")
  public native double getInterest();

  @Name("GetTaxes")
  public native double getTaxes();

  @Name("AddReplenishment")
  public native void addReplenishment(final int month, final double value);

  @Name("AddWithdrawal")
  public native void addWithdrawal(final int month, final double value);

  @Name("Clear")
  public native void clear();
}
