package edu.school21.butterba.smartcalc;

import edu.school21.butterba.smartcalc.model.core.SmartCalcModel;
import edu.school21.butterba.smartcalc.presenter.SmartCalcPresenter;
import edu.school21.butterba.smartcalc.view.controllers.SmartCalcController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

  public static final int WIDTH = 807;
  public static final int HEIGHT = 427;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(final Stage stage) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource(SmartCalcController.FXML));
    stage.setTitle(SmartCalcController.TITLE);
    stage.setScene(new Scene(loader.load(), WIDTH, HEIGHT));
    SmartCalcController controller = loader.getController();
    SmartCalcPresenter presenter = new SmartCalcPresenter(new SmartCalcModel(), controller);
    controller.setPresenter(presenter);
    stage.setResizable(false);
    stage.show();
  }
}
