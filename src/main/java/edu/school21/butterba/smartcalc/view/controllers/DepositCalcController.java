package edu.school21.butterba.smartcalc.view.controllers;

import static edu.school21.butterba.smartcalc.model.CapitalizationPeriodicity.HALF_YEARLY;
import static edu.school21.butterba.smartcalc.model.CapitalizationPeriodicity.MONTHLY;
import static edu.school21.butterba.smartcalc.model.CapitalizationPeriodicity.QUARTERLY;
import static edu.school21.butterba.smartcalc.model.CapitalizationPeriodicity.YEARLY;

import edu.school21.butterba.smartcalc.model.CapitalizationPeriodicity;
import edu.school21.butterba.smartcalc.presenter.DepositCalcPresenter;
import edu.school21.butterba.smartcalc.view.ListCellView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.DoubleSpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import lombok.Getter;
import lombok.Setter;

@Getter
public class DepositCalcController implements Initializable {

  public static final String FXML = "/fxml/deposit_calc.fxml";
  public static final String TITLE = "DepositCalc";
  public static final String STYLESHEET = "/css/deposit_calc.css";

  @FXML
  private Spinner<Double> depositAmount;
  @FXML
  private Spinner<Integer> monthlyTerm;
  @FXML
  private Spinner<Double> interestRate;
  @FXML
  private Spinner<Double> taxRate;
  @FXML
  private ListView<String> replenishments;
  @FXML
  private Spinner<Integer> replenishmentMonth;
  @FXML
  private Spinner<Double> replenishmentAmount;
  @FXML
  private ListView<String> withdrawals;
  @FXML
  private Spinner<Integer> withdrawalMonth;
  @FXML
  private Spinner<Double> withdrawalAmount;
  @FXML
  private CheckBox capitalization;
  @FXML
  private ChoiceBox<CapitalizationPeriodicity> periodicity;
  @FXML
  private Label accruedInterest;
  @FXML
  private Label taxAmount;
  @FXML
  private Label depositEndAmount;

  @Setter
  private DepositCalcPresenter presenter;

  @Override
  public void initialize(final URL location, final ResourceBundle resources) {
    setSpinners();
    periodicity
        .getItems()
        .addAll(MONTHLY, QUARTERLY, HALF_YEARLY, YEARLY);
    periodicity.setValue(MONTHLY);
    setCellFactory(replenishments);
    setCellFactory(withdrawals);
  }

  @FXML
  public void onReplenishClicked() {
    presenter.replenish();
  }

  @FXML
  public void onWithdrawClicked() {
    presenter.withdraw();
  }

  @FXML
  public void onResetClicked() {
    presenter.reset();
  }

  @FXML
  public void onCalculateClicked() {
    presenter.calculate();
  }

  private void setSpinners() {
    depositAmount.setValueFactory(new DoubleSpinnerValueFactory(0, Double.MAX_VALUE, 0.0));
    monthlyTerm.setValueFactory(new IntegerSpinnerValueFactory(0, 600, 0));
    interestRate.setValueFactory(new DoubleSpinnerValueFactory(0, 50, 0.0));
    taxRate.setValueFactory(new DoubleSpinnerValueFactory(0, 99.99, 0.0));
    replenishmentMonth.setValueFactory(new IntegerSpinnerValueFactory(0, 12, 0));
    replenishmentAmount.setValueFactory(new DoubleSpinnerValueFactory(0, Double.MAX_VALUE, 0.0));
    withdrawalMonth.setValueFactory(new IntegerSpinnerValueFactory(0, 12, 0));
    withdrawalAmount.setValueFactory(new DoubleSpinnerValueFactory(0, Double.MAX_VALUE, 0.0));
  }

  private void setCellFactory(ListView<String> listView) {
    listView.setCellFactory(lw -> new ListCellView(STYLESHEET, "spinner"));
  }
}
