package br.com.marcosatanaka.easypoi.excel;

import org.apache.commons.collections4.iterators.ArrayListIterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

import static java.util.Collections.addAll;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class PlanilhaTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Mock
	public Sheet sheet;

	@Mock
	public Row row;

	@Mock
	public Cell cell;

	@Test
	public void deve_lancar_excecao_ao_construir_sem_sheet() {
		expectedException.expect(NullPointerException.class);
		Planilha.of(null);
	}

	@Test
	public void deve_retornar_primeira_linha_da_planilha() {
		doReturn(row).when(sheet).getRow(0);

		assertThat(Planilha.of(sheet).cabecalho())
				.isEqualTo(row);
	}

	@Test
	public void deve_retornar_nome_da_coluna_neste_indice() {
		int indiceColuna = 1;
		doReturn(row).when(sheet).getRow(0);
		doReturn(cell).when(row).getCell(indiceColuna);
		doReturn("Nome da coluna").when(cell).getStringCellValue();

		assertThat(Planilha.of(sheet).nomeColuna(indiceColuna))
				.isEqualTo("Nome da coluna");
	}

	@Test
	public void deve_retornar_todas_as_linhas_exceto_a_primeira() {
		Row linha1 = mock(Row.class);
		Row linha2 = mock(Row.class);
		Row linha3 = mock(Row.class);
		doReturn(spliterator(linha1, linha2, linha3)).when(sheet).spliterator();

		Cell cell1 = mock(Cell.class);
		Cell cell2 = mock(Cell.class);
		Cell cell3 = mock(Cell.class);

		doReturn(1.0).when(cell1).getNumericCellValue();
		doReturn(1.0).when(cell2).getNumericCellValue();
		doReturn(1.0).when(cell3).getNumericCellValue();
		doReturn(CellType.NUMERIC).when(cell1).getCellTypeEnum();
		doReturn(CellType.NUMERIC).when(cell2).getCellTypeEnum();
		doReturn(CellType.NUMERIC).when(cell3).getCellTypeEnum();
		doReturn(iterator(cell1, cell2, cell3)).when(linha1).iterator();
		doReturn(iterator(cell1, cell2, cell3)).when(linha2).iterator();
		doReturn(iterator(cell1, cell2, cell3)).when(linha3).iterator();

		List<Linha> linhas = Planilha.of(sheet).linhasIgnorandoCabecalho();

		assertThat(linhas).hasSize(2);
		assertThat(linhas.get(0).getRow()).isEqualTo(linha2);
		assertThat(linhas.get(1).getRow()).isEqualTo(linha3);
	}

	@Test
	public void deve_retornar_todas_a_partir_da_linha_informada() {
		Row linha1 = mock(Row.class);
		Row linha2 = mock(Row.class);
		Row linha3 = mock(Row.class);
		Row linha4 = mock(Row.class);
		Row linha5 = mock(Row.class);
		doReturn(spliterator(linha1, linha2, linha3, linha4, linha5)).when(sheet).spliterator();
		doReturn(0).when(linha1).getRowNum();
		doReturn(1).when(linha2).getRowNum();
		doReturn(2).when(linha3).getRowNum();
		doReturn(3).when(linha4).getRowNum();
		doReturn(4).when(linha5).getRowNum();

		List<Linha> linhas = Planilha.of(sheet).linhasAPartirDa(3);

		assertThat(linhas).hasSize(3);
		assertThat(linhas.get(0).getRow()).isEqualTo(linha3);
		assertThat(linhas.get(1).getRow()).isEqualTo(linha4);
		assertThat(linhas.get(2).getRow()).isEqualTo(linha5);
	}

	private Spliterator<Row> spliterator(Row... rows) {
		ArrayListIterator<Row> celulasIterator = new ArrayListIterator<>(rows);
		return Spliterators.spliteratorUnknownSize(celulasIterator, 0);
	}

	private Iterator<Cell> iterator(Cell... cells) {
		ArrayList<Cell> colunas = new ArrayList<>();
		addAll(colunas, cells);
		return colunas.iterator();
	}
}
