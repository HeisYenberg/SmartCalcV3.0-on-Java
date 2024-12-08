package edu.school21.butterba.smartcalc.presenter;

import static edu.school21.butterba.smartcalc.Main.HEIGHT;
import static edu.school21.butterba.smartcalc.Main.WIDTH;

import edu.school21.butterba.smartcalc.model.HistoryModel;
import edu.school21.butterba.smartcalc.model.core.CreditCalcModel;
import edu.school21.butterba.smartcalc.model.core.DepositCalcModel;
import edu.school21.butterba.smartcalc.model.core.SmartCalcModel;
import edu.school21.butterba.smartcalc.view.controllers.CreditCalcController;
import edu.school21.butterba.smartcalc.view.controllers.DepositCalcController;
import edu.school21.butterba.smartcalc.view.controllers.GraphController;
import edu.school21.butterba.smartcalc.view.controllers.SmartCalcController;
import java.io.IOException;
import java.text.DecimalFormat;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SmartCalcPresenter {

  private static final String HELP_FXML = "/fxml/help.fxml";
  private static final String HELP_TITLE = "Help";
  private static final Logger LOGGER = LogManager.getLogger(SmartCalcPresenter.class);
  private final SmartCalcModel model;
  private final HistoryModel historyModel;
  private final SmartCalcController view;

  public SmartCalcPresenter(final SmartCalcModel model, final SmartCalcController view) {
    this.model = model;
    this.historyModel = new HistoryModel();
    this.view = view;
    this.view
        .getHistory()
        .setItems(historyModel.getHistory());
  }

  public void cleanHistory() {
    historyModel.clear();
  }

  public void calculate() {
    String expression = view
        .getExpression()
        .getText();
    if (expression.isEmpty()) {
      return;
    }
    try {
      Double xValue = view
          .getSpinner()
          .getValue();
      model.setXValue(xValue);
      model.setExpression(expression);
      model.smartCalc();
      historyModel.add(expression);
      DecimalFormat format = new DecimalFormat("#.#######");
      String result = format.format(model.getResult());
      view
          .getResult()
          .setText(result);
      LOGGER.info(String.format("expression: %s, x: %f, result: %s", expression, xValue, result));
    } catch (RuntimeException e) {
      view
          .getResult()
          .setText(e.getMessage());
      LOGGER.error(String.format("expression : %s, error: %s", expression, e.getMessage()));
    }
  }

  public void showGraph() {
    try {
      model.setExpression(view.getExpression().getText());
      Stage stage = new Stage();
      FXMLLoader loader = new FXMLLoader(getClass().getResource(GraphController.FXML));
      stage.setScene(new Scene(loader.load(), WIDTH, HEIGHT));
      GraphController view = loader.getController();
      GraphPresenter presenter = new GraphPresenter(model, view);
      view.setPresenter(presenter);
      stage.setTitle(GraphController.TITLE);
      stage.setResizable(false);
      stage.show();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void showCreditCalc() {
    try {
      Stage stage = new Stage();
      FXMLLoader loader = new FXMLLoader(getClass().getResource(CreditCalcController.FXML));
      stage.setScene(new Scene(loader.load(), WIDTH, HEIGHT));
      CreditCalcController view = loader.getController();
      CreditCalcPresenter presenter = new CreditCalcPresenter(new CreditCalcModel(), view);
      view.setPresenter(presenter);
      stage.setTitle(CreditCalcController.TITLE);
      stage.setResizable(false);
      stage.show();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void showDepositCalc() {
    try {
      Stage stage = new Stage();
      FXMLLoader loader = new FXMLLoader(getClass().getResource(DepositCalcController.FXML));
      stage.setScene(new Scene(loader.load(), WIDTH, HEIGHT));
      DepositCalcController view = loader.getController();
      DepositCalcPresenter presenter = new DepositCalcPresenter(new DepositCalcModel(), view);
      view.setPresenter(presenter);
      stage.setTitle(DepositCalcController.TITLE);
      stage.setResizable(false);
      stage.show();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void showHelp() {
    try {
      Stage stage = new Stage();
      FXMLLoader loader = new FXMLLoader(getClass().getResource(HELP_FXML));
      stage.setTitle(HELP_TITLE);
      stage.setScene(new Scene(loader.load(), WIDTH, HEIGHT));
      stage.setResizable(false);
      stage.show();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
