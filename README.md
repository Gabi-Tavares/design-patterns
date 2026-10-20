# Atividade

Vamos separar nossas classes em diferentes responsabilidades através do padrão de projetos Adapter.
---

## 🎯 Objetivo da Atividade

1. Crie a interface br.pucpr.table.model.TableData para representar os dados de uma tabela (TableData)
2. Refatore as classes UsersPrinter e PlanetasPrinter para ter uma única classe Table
3. A largura da coluna será dada pela largura do seu cabeçalho.

## Paginação

`PagedTableData` adiciona paginação envolvendo qualquer implementação de `TableData`. A classe `Table` não precisa ser alterada: basta passar o wrapper para ela e selecionar uma página, começando pelo índice `0`.

```java
var pagedData = new PagedTableData(tableData, 10);
new Table(pagedData).print();

pagedData.setPage(1);
new Table(pagedData).print();
```

O tamanho da página deve ser maior que zero, e só é possível selecionar páginas existentes.

---

## 🛠️ Requisitos para Execução

* **Linguagem:** Java 17 ou superior