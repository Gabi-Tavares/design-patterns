package br.pucpr.table.model;

public interface TableData {
  int rowCount();

  int colCount();

  String header(int col);

  String get(int row, int col);

  void addObserver(DataChangedObserver observer);

  void removeObserver(DataChangedObserver observer);

  void fireTableDataChanged();
}
