package edu.school21.butterba.smartcalc.view.controllers;

import edu.school21.butterba.smartcalc.presenter.GraphPresenter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.DoubleSpinnerValueFactory;
import lombok.Getter;
import lombok.Setter;

@Getter
public class GraphController implements Initializable {

  public static final String FXML = "/fxml/graph.fxml";
  public static final String TITLE = "Graph";

  @FXML
  private ScatterChart<Number, Number> scatterChart;
  @FXML
  private NumberAxis xAxis;
  @FXML
  private NumberAxis yAxis;
  @FXML
  private Spinner<Double> xMax;
  @FXML
  private Spinner<Double> xMin;
  @FXML
  private Spinner<Double> yMax;
  @FXML
  private Spinner<Double> yMin;
  @FXML
  private Label expression;

  @Setter
  private GraphPresenter presenter;

  @Override
  public void initialize(final URL location, final ResourceBundle resources) {
    setValueFactory();
    xAxis.setAutoRanging(false);
    yAxis.setAutoRanging(false);
    setAxes();
  }

  @FXML
  public void onGraphClicked() {
    setAxes();
    presenter.plotGraph();
  }

  private void setAxes() {
    Double xMaxValue = xMax.getValue();
    Double xMinValue = xMin.getValue();
    Double yMaxValue = yMax.getValue();
    Double yMinValue = yMin.getValue();
    xAxis.setUpperBound(xMaxValue);
    xAxis.setLowerBound(xMinValue);
    xAxis.setTickUnit((xMaxValue - xMinValue) / 4.0);
    yAxis.setUpperBound(yMaxValue);
    yAxis.setLowerBound(yMinValue);
    yAxis.setTickUnit((yMaxValue - yMinValue) / 4.0);
  }

  private void setValueFactory() {
    xMax.setValueFactory(new DoubleSpinnerValueFactory(0.5, 1000000, 10, 0.1));
    xMin.setValueFactory(new DoubleSpinnerValueFactory(-1000000, -0.5, -10, 0.1));
    yMax.setValueFactory(new DoubleSpinnerValueFactory(0.5, 1000000, 10, 0.1));
    yMin.setValueFactory(new DoubleSpinnerValueFactory(-1000000, -0.5, -10, 0.1));
  }
}
