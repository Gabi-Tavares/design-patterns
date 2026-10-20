package br.pucpr.table.model;

public class PagedTableData extends AbstractTableData {
  private final TableData data;
  private final int pageSize;
  private int page;

  public PagedTableData(TableData data, int pageSize) {
    if (data == null) {
      throw new IllegalArgumentException("Data cannot be null");
    }
    if (pageSize <= 0) {
      throw new IllegalArgumentException("Page size must be greater than zero");
    }

    this.data = data;
    this.pageSize = pageSize;
    this.data.addObserver(this::onDataChanged);
  }

  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    if (page < 0 || page >= getPageCount()) {
      throw new IndexOutOfBoundsException("Page out of range: " + page);
    }
    if (this.page == page) {
      return;
    }
    this.page = page;
    fireTableDataChanged();
  }

  public int getPageSize() {
    return pageSize;
  }

  public int getPageCount() {
    final var rows = data.rowCount();
    return rows / pageSize + (rows % pageSize == 0 ? 0 : 1);
  }

  @Override
  public int rowCount() {
    final var remainingRows = data.rowCount() - page * pageSize;
    return Math.max(0, Math.min(pageSize, remainingRows));
  }

  @Override
  public int colCount() {
    return data.colCount();
  }

  @Override
  public String header(int col) {
    return data.header(col);
  }

  @Override
  public String get(int row, int col) {
    if (row < 0 || row >= rowCount()) {
      throw new IndexOutOfBoundsException("Row out of range: " + row);
    }
    return data.get(page * pageSize + row, col);
  }

  private void onDataChanged() {
    final var pageCount = getPageCount();
    if (pageCount == 0) {
      page = 0;
    } else if (page >= pageCount) {
      page = pageCount - 1;
    }
    fireTableDataChanged();
  }
}
