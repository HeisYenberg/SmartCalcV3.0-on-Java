package edu.school21.butterba.smartcalc.model.core;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

public class SmartCalcModelTest {

  private static final Double EPSILON = 1e-5;
  private static SmartCalcModel model;

  @BeforeAll
  public static void setUp() {
    model = new SmartCalcModel();
  }

  @AfterAll
  public static void tearDown() {
    model.close();
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/binary_calculations.csv")
  void testBinaryCalculations(final String expression, final Double result) {
    model.setXValue(0);
    model.setExpression(expression);
    model.smartCalc();
    Assertions.assertEquals(result, model.getResult(), EPSILON);
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/function_calculations.csv")
  void testFunctionCalculations(final String expression, final Double result) {
    model.setXValue(0);
    model.setExpression(expression);
    model.smartCalc();
    Assertions.assertEquals(result, model.getResult(), EPSILON);
  }

  @ParameterizedTest
  @ValueSource(strings = {"2 3 + 2", "21 + (21", "21 - 21)",
      "21 (21*21)", "21^(*21*21)", "21 ++++21", "sin (x,x)",
      "cos()", "12.21 mo 12,21", "21.21."
  })
  void testIncorrectExpression(final String expression) {
    model.setXValue(0);
    model.setExpression(expression);
    Assertions.assertThrowsExactly(RuntimeException.class, () -> model.smartCalc(),
        "Incorrect expression");
  }

  @ParameterizedTest
  @ValueSource(strings = {
      "asin(21)", "asin(-21)", "acos(21)", "acos(-21)", "log(-21)",
      "log(-0.21)", "ln(-21)", "ln(-0.21)", "sqrt(-21)", "2^(-1)^(-0.5)"
  })
  void testCalculationError(final String expression) {
    model.setXValue(0);
    model.setExpression(expression);
    Assertions.assertThrowsExactly(RuntimeException.class, () -> model.smartCalc(),
        "Calculation error");
  }

  @ParameterizedTest
  @ValueSource(strings = {"12/0", "21%0"})
  void testDivisionByZero(final String expression) {
    model.setExpression(expression);
    Assertions.assertThrows(RuntimeException.class, () -> model.smartCalc(), "Division by zero");
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/binary_calculations.csv")
  void testGetExpression(final String expression) {
    model.setExpression(expression);
    Assertions.assertEquals(model.getExpression(), expression);
  }
}
