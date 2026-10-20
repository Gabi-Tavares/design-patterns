package br.pucpr.table.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.pucpr.table.Table;
import org.junit.jupiter.api.Test;

class PagedTableDataTest {
  private final TableData data =
      new TableData() {
        private final String[][] rows = {
          {"Ana", "ana@example.com"},
          {"Bia", "bia@example.com"},
          {"Caio", "caio@example.com"}
        };

        @Override
        public int rowCount() {
          return rows.length;
        }

        @Override
        public int colCount() {
          return 2;
        }

        @Override
        public String header(int col) {
          return col == 0 ? "Nome" : "E-mail";
        }

        @Override
        public String get(int row, int col) {
          return rows[row][col];
        }
      };

  @Test
  void delegatesTableDataAndShowsRequestedPage() {
    final var pagedData = new PagedTableData(data, 2);

    assertEquals(2, pagedData.getPageCount());
    assertEquals(2, pagedData.rowCount());
    assertEquals(2, pagedData.colCount());
    assertEquals("Nome", pagedData.header(0));
    assertEquals("Ana", pagedData.get(0, 0));
    assertEquals("bia@example.com", pagedData.get(1, 1));
    assertTrue(new Table(pagedData).toString().contains("Ana"));
    assertFalse(new Table(pagedData).toString().contains("Caio"));

    pagedData.setPage(1);

    assertEquals(1, pagedData.getPage());
    assertEquals(1, pagedData.rowCount());
    assertEquals("Caio", pagedData.get(0, 0));
    assertTrue(new Table(pagedData).toString().contains("Caio"));
    assertFalse(new Table(pagedData).toString().contains("Ana"));
  }

  @Test
  void rejectsInvalidPageSize() {
    assertThrows(IllegalArgumentException.class, () -> new PagedTableData(null, 2));
    assertThrows(IllegalArgumentException.class, () -> new PagedTableData(data, 0));
    assertThrows(IllegalArgumentException.class, () -> new PagedTableData(data, -1));
  }

  @Test
  void rejectsPageOutsideAvailableRange() {
    final var pagedData = new PagedTableData(data, 2);

    assertThrows(IndexOutOfBoundsException.class, () -> pagedData.setPage(-1));
    assertThrows(IndexOutOfBoundsException.class, () -> pagedData.setPage(2));
  }

  @Test
  void rejectsRowsOutsideCurrentPage() {
    final var pagedData = new PagedTableData(data, 2);
    pagedData.setPage(1);

    assertThrows(IndexOutOfBoundsException.class, () -> pagedData.get(1, 0));
  }

  @Test
  void emptyDataHasNoPages() {
    final var emptyData = new PagedTableData(new EmptyTableData(), 2);

    assertEquals(0, emptyData.getPageCount());
    assertEquals(0, emptyData.rowCount());
    assertThrows(IndexOutOfBoundsException.class, () -> emptyData.setPage(0));
  }

  private static class EmptyTableData implements TableData {
    @Override
    public int rowCount() {
      return 0;
    }

    @Override
    public int colCount() {
      return 0;
    }

    @Override
    public String header(int col) {
      throw new IndexOutOfBoundsException();
    }

    @Override
    public String get(int row, int col) {
      throw new IndexOutOfBoundsException();
    }
  }
}
