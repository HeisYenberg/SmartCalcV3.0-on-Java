package edu.school21.butterba.smartcalc.view;

import javafx.scene.control.ListCell;

public class ListCellView extends ListCell<String> {

  private final String stylesheet;
  private final String styleClass;

  public ListCellView(final String stylesheet, final String styleClass) {
    this.stylesheet = stylesheet;
    this.styleClass = styleClass;
  }

  @Override
  protected void updateItem(final String item, final boolean empty) {
    super.updateItem(item, empty);
    if (item == null || empty) {
      setText(null);
    } else {
      setText(item);
    }
    getStylesheets().add(stylesheet);
    getStyleClass().add(styleClass);
  }
}
