package br.pucpr.table.model;

import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class AbstractTableData implements TableData {
  private final CopyOnWriteArrayList<DataChangedObserver> observers = new CopyOnWriteArrayList<>();

  @Override
  public final void addObserver(DataChangedObserver observer) {
    observers.add(Objects.requireNonNull(observer, "Observer cannot be null"));
  }

  @Override
  public final void removeObserver(DataChangedObserver observer) {
    observers.remove(Objects.requireNonNull(observer, "Observer cannot be null"));
  }

  @Override
  public final void fireTableDataChanged() {
    for (var observer : observers) {
      observer.onTableChange();
    }
  }
}
