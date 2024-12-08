package edu.school21.butterba.smartcalc.model.core;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CreditCalcModelTest {

  private static final double EPSILON = 1e-5;
  private static CreditCalcModel model;

  @BeforeEach
  public void setUp() {
    model = new CreditCalcModel();
    model.setCreditAmount(1000);
    model.setInterestRate(10);
    model.setTerm(3);
  }

  @AfterEach
  public void tearDown() {
    model.close();
  }

  @Test
  public void testCreditAnnuity() {
    model.setIsDifferentiated(false);
    model.calculateCredit();
    Assertions.assertEquals(model.getMonthlyPayments().get(), 338.904257, EPSILON);
    Assertions.assertEquals(model.getOverpayment(), 16.712771, EPSILON);
    Assertions.assertEquals(model.getTotalPayment(), 1016.712771, EPSILON);
  }

  @Test
  public void testCreditDifferentiated() {
    model.setIsDifferentiated(true);
    model.calculateCredit();
    Assertions.assertEquals(model.getMonthlyPayments().get(0), 341.666667, EPSILON);
    Assertions.assertEquals(model.getMonthlyPayments().get(1), 338.888889, EPSILON);
    Assertions.assertEquals(model.getMonthlyPayments().get(2), 336.111111, EPSILON);
    Assertions.assertEquals(model.getOverpayment(), 16.666667, EPSILON);
    Assertions.assertEquals(model.getTotalPayment(), 1016.666667, EPSILON);
  }
}
