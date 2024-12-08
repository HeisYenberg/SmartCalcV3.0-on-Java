package edu.school21.butterba.smartcalc.model;

public enum CapitalizationPeriodicity {
  MONTHLY("Monthly"),
  QUARTERLY("Quarterly"),
  HALF_YEARLY("Half-Yearly"),
  YEARLY("Yearly");

  private final String periodicity;

  CapitalizationPeriodicity(final String periodicity) {
    this.periodicity = periodicity;
  }

  @Override
  public String toString() {
    return periodicity;
  }
}
