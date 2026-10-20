package br.pucpr.table.model;

@FunctionalInterface
public interface DataChangedObserver {
  void onTableChange();
}
