package edu.school21.butterba.smartcalc.model.core;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Name;
import org.bytedeco.javacpp.annotation.Namespace;
import org.bytedeco.javacpp.annotation.Platform;
import org.bytedeco.javacpp.annotation.StdString;

@Platform(include = "smart_calc/smart_calc_model.h", link = "smart_calc")
@Namespace("s21")
public class SmartCalcModel extends Pointer {

  static {
    Loader.load();
  }

  public SmartCalcModel() {
    allocate();
  }

  private native void allocate();

  @Name("SmartCalc")
  public native void smartCalc();

  @Name("SetXValue")
  public native void setXValue(final double x);

  @Name("GetExpression")
  public native @StdString String getExpression();

  @Name("SetExpression")
  public native void setExpression(@StdString final String expression);

  @Name("GetResult")
  public native double getResult();
}
