package edu.school21.butterba.smartcalc.view.controllers;

import edu.school21.butterba.smartcalc.presenter.SmartCalcPresenter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.DoubleSpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import lombok.Getter;
import lombok.Setter;

@Getter
public class SmartCalcController implements Initializable {

  public static final String FXML = "/fxml/smart_calc.fxml";
  public static final String TITLE = "SmartCalc v3.0";
  public static final String STYLESHEET = "/css/smart_calc.css";

  @FXML
  public Button cleanHistory;
  @FXML
  private TextField expression;
  @FXML
  private TextField result;
  @FXML
  private ListView<String> history;
  @FXML
  private Spinner<Double> spinner;

  @Setter
  private SmartCalcPresenter presenter;

  @Override
  public void initialize(final URL location, final ResourceBundle resources) {
    DoubleSpinnerValueFactory valueFactory = new DoubleSpinnerValueFactory(
        Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 0.0, 0.1);
    spinner.setValueFactory(valueFactory);
    setHistory();
  }

  @FXML
  public void onDigitClicked(final MouseEvent event) {
    Button button = (Button) event.getSource();
    expression.appendText(button.getText());
  }

  @FXML
  public void onOperatorClicked(final MouseEvent mouseEvent) {
    Button button = (Button) mouseEvent.getSource();
    String operator = button.getText();
    switch (operator) {
      case "pow":
        expression.appendText("^");
        break;
      case "÷":
        expression.appendText("/");
        break;
      case "×":
        expression.appendText("*");
        break;
      default:
        expression.appendText(operator);
        break;
    }
  }

  @FXML
  public void onFunctionClicked(final MouseEvent mouseEvent) {
    Button button = (Button) mouseEvent.getSource();
    expression.appendText(button.getText() + "(");
  }

  @FXML
  public void onDelClicked() {
    String string = expression.getText();
    if (string.isEmpty()) {
      return;
    }
    expression.setText(string.substring(0, string.length() - 1));
  }

  @FXML
  public void onCleanClicked() {
    expression.clear();
    result.setText("0");
  }

  @FXML
  public void onCalculateClicked() {
    presenter.calculate();
  }

  @FXML
  public void onGraphClicked() {
    presenter.showGraph();
  }

  @FXML
  public void onCreditClicked() {
    presenter.showCreditCalc();
  }

  @FXML
  public void onDepositClicked() {
    presenter.showDepositCalc();
  }

  @FXML
  public void onClearHistoryClicked() {
    presenter.cleanHistory();
  }

  @FXML
  public void onHelpClicked() {
    presenter.showHelp();
  }

  private void setHistory() {
    history.setCellFactory(listView -> new ListCell<String>() {
      @Override
      protected void updateItem(final String item, final boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
          setText(null);
        } else {
          setText(item);
        }
        getStylesheets().add(STYLESHEET);
        getStyleClass().add("history");
        setOnMouseClicked((MouseEvent event) -> {
          if (event.getClickCount() == 2) {
            String expressionText = history
                .getSelectionModel()
                .getSelectedItem();
            expression.setText(expressionText);
          }
        });
      }
    });
  }
}
