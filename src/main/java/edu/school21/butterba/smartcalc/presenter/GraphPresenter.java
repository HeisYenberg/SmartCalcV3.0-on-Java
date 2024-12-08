package edu.school21.butterba.smartcalc.presenter;

import edu.school21.butterba.smartcalc.model.core.SmartCalcModel;
import edu.school21.butterba.smartcalc.view.controllers.GraphController;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;

public class GraphPresenter {

  private final SmartCalcModel model;
  private final GraphController view;

  public GraphPresenter(final SmartCalcModel model, final GraphController view) {
    this.model = model;
    this.view = view;
    this.view
        .getExpression()
        .setText(model.getExpression());
  }

  public void plotGraph() {
    Series<Number, Number> chart = new Series<>();
    view
        .getScatterChart()
        .getData()
        .clear();
    Double maxValue = view
        .getXMax()
        .getValue();
    Double minValue = view
        .getXMin()
        .getValue();
    double step = (maxValue - minValue) / 2500;
    for (double x = minValue; x <= maxValue; x += step) {
      model.setXValue(x);
      try {
        model.smartCalc();
        Data<Number, Number> data = new Data<>(x, model.getResult());
        chart.getData().add(data);
        view
            .getScatterChart()
            .getData()
            .add(chart);
      } catch (RuntimeException e) {
        if (e.getMessage().equals("Incorrect expression")) {
          view
              .getExpression()
              .setText(e.getMessage());
          return;
        }
      }
    }
  }
}
