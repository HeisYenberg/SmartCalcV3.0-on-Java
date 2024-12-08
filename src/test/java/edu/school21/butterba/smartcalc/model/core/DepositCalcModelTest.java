package edu.school21.butterba.smartcalc.model.core;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DepositCalcModelTest {

  private static final double EPSILON = 1e-5;
  private static DepositCalcModel model;

  @BeforeEach
  public void setUp() {
    model = new DepositCalcModel();
    model.setDepositAmount(75000);
    model.setInterestRate(5);
    model.setTaxRate(13);
    model.setMonthTerm(24);
  }

  @AfterEach
  public void tearDown() {
    model.close();
  }

  @Test
  public void testDepositNotCapitalized() {
    model.setIsCapitalized(false);
    model.setCapitalizationPeriodicity(0);
    model.calculateDeposit();
    Assertions.assertEquals(model.getDepositAmount(), 81525.00, EPSILON);
    Assertions.assertEquals(model.getInterest(), 7500.00, EPSILON);
    Assertions.assertEquals(model.getTaxes(), 975.00, EPSILON);
  }

  @Test
  public void testDepositCapitalizedMonthly() {
    model.setIsCapitalized(true);
    model.setCapitalizationPeriodicity(0);
    model.calculateDeposit();
    Assertions.assertEquals(model.getDepositAmount(), 81847.422145, EPSILON);
    Assertions.assertEquals(model.getInterest(), 7870.600167, EPSILON);
    Assertions.assertEquals(model.getTaxes(), 1023.178022, EPSILON);
  }

  @Test
  public void testDepositCapitalizedQuarterly() {
    model.setIsCapitalized(true);
    model.setCapitalizationPeriodicity(1);
    model.calculateDeposit();
    Assertions.assertEquals(model.getDepositAmount(), 81806.683932, EPSILON);
    Assertions.assertEquals(model.getInterest(), 7823.774634, EPSILON);
    Assertions.assertEquals(model.getTaxes(), 1017.090702, EPSILON);
  }

  @Test
  public void testDepositCapitalizedHalfYearly() {
    model.setIsCapitalized(true);
    model.setCapitalizationPeriodicity(2);
    model.calculateDeposit();
    Assertions.assertEquals(model.getDepositAmount(), 81779.890689, EPSILON);
    Assertions.assertEquals(model.getInterest(), 7792.977804, EPSILON);
    Assertions.assertEquals(model.getTaxes(), 1013.087114, EPSILON);
  }

  @Test
  public void testDepositCapitalizedYearly() {
    model.setIsCapitalized(true);
    model.setCapitalizationPeriodicity(3);
    model.calculateDeposit();
    Assertions.assertEquals(model.getDepositAmount(), 81701.208984, EPSILON);
    Assertions.assertEquals(model.getInterest(), 7702.539062, EPSILON);
    Assertions.assertEquals(model.getTaxes(), 1001.330078, EPSILON);
  }

  @Test
  public void testWithdrawalAndReplenishment() {
    model.addReplenishment(1, 1000);
    model.addWithdrawal(2, 80000);
    Assertions.assertThrows(RuntimeException.class, () -> model.calculateDeposit());
  }

  @Test
  public void testClear() {
    model.addReplenishment(1, 1000);
    model.addWithdrawal(2, 80000);
    model.clear();
    Assertions.assertDoesNotThrow(() -> model.calculateDeposit());
  }
}
