package edu.school21.butterba.smartcalc.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

@Getter
public class HistoryModel {

  private static final Path HISTORY_DIR = Paths.get("history/");
  private static final Path HISTORY_FILE = Paths.get("history/history.txt");
  private final ObservableList<String> history;

  public HistoryModel() {
    history = FXCollections.observableArrayList();
    loadHistory();
  }

  public void add(final String expression) {
    if (history.contains(expression)) {
      return;
    }
    history.add(expression);
    saveHistory((expression + '\n').getBytes(), StandardOpenOption.APPEND);
  }

  public void clear() {
    history.clear();
    saveHistory(new byte[0], StandardOpenOption.TRUNCATE_EXISTING);
  }

  private void loadHistory() {
    try {
      if (Files.exists(HISTORY_FILE)) {
        List<String> strings = Files.readAllLines(HISTORY_FILE);
        history.addAll(strings);
      } else {
        Files.createDirectory(HISTORY_DIR);
        Files.createFile(HISTORY_FILE);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void saveHistory(final byte[] bytes, final OpenOption openOption) {
    try {
      Files.write(HISTORY_FILE, bytes, openOption);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
