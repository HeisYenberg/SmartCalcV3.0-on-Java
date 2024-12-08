package edu.school21.butterba.smartcalc.view.controllers;

import edu.school21.butterba.smartcalc.presenter.CreditCalcPresenter;
import edu.school21.butterba.smartcalc.view.ListCellView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.DoubleSpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import lombok.Getter;
import lombok.Setter;

@Getter
public class CreditCalcController implements Initializable {

  public static final String FXML = "/fxml/credit_calc.fxml";
  public static final String TITLE = "CreditCalc";
  public static final String STYLESHEET = "/css/credit_calc.css";
  @FXML
  private Spinner<Double> creditAmount;
  @FXML
  private Spinner<Integer> monthTerm;
  @FXML
  private Spinner<Double> interestRate;
  @FXML
  private ChoiceBox<String> creditType;
  @FXML
  private ListView<String> monthlyPayments;
  @FXML
  private Label overpayment;
  @FXML
  private Label totalPayment;

  @Setter
  private CreditCalcPresenter presenter;

  @Override
  public void initialize(final URL location, final ResourceBundle resources) {
    creditAmount.setValueFactory(new DoubleSpinnerValueFactory(0, Double.MAX_VALUE, 0));
    monthTerm.setValueFactory(new IntegerSpinnerValueFactory(0, 600, 0));
    interestRate.setValueFactory(new DoubleSpinnerValueFactory(0, 365.0, 0));
    creditType
        .getItems()
        .addAll("Annuity", "Differentiated");
    creditType.setValue("Annuity");
    monthlyPayments.setCellFactory(listView -> new ListCellView(STYLESHEET, "spinner"));
  }

  @FXML
  public void onCalculateClicked() {
    presenter.calculate();
  }
}
