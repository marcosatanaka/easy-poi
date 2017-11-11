package br.com.marcosatanaka.easypoi.excel;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.iterators.ArrayListIterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Spliterator;
import java.util.Spliterators;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class LinhaTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Mock
	private Planilha planilha;

	@Mock
	private Row row;

	@Mock
	private Cell cell;

	@Test
	public void deve_lancar_excecao_ao_construir_sem_row() {
		expectedException.expect(NullPointerException.class);
		Linha.of(null, planilha);
	}

	@Test
	public void deve_lancar_excecao_ao_construir_sem_planilha() {
		expectedException.expect(NullPointerException.class);
		Linha.of(row, null);
	}

	@Test
	public void deve_retornar_nome_da_coluna_que_a_celula_pertence() {
		doReturn(1).when(cell).getColumnIndex();
		doReturn("Nome da coluna").when(planilha).nomeColuna(1);
		Linha linha = Linha.of(row, planilha);

		assertThat(linha.nomeColuna(cell)).isEqualTo("Nome da coluna");
	}

	@Test
	public void deve_ser_linha_vazia() {
		ArrayListIterator celulasIterator = mock(ArrayListIterator.class);
		doReturn(Spliterators.spliteratorUnknownSize(celulasIterator, 0)).when(row).spliterator();
		Linha linha = Linha.of(row, planilha);

		assertThat(linha.possuiValores()).isFalse();
	}

	@Test
	public void nao_deve_ser_linha_vazia() {
		preparaCelulaString();
		doReturn(spliterator(cell)).when(row).spliterator();
		Linha linha = Linha.of(row, planilha);

		assertThat(linha.possuiValores()).isTrue();
	}

	@Test
	public void deve_ter_valor_na_coluna() {
		preparaCelulaString();
		doReturn(cell).when(row).getCell(1);
		Linha linha = Linha.of(row, planilha);

		assertThat(linha.possuiValorNa(1)).isTrue();
	}

	@Test
	public void nao_deve_ter_valor_na_coluna() {
		doReturn(CellType.BLANK).when(cell).getCellTypeEnum();
		doReturn(cell).when(row).getCell(1);
		Linha linha = Linha.of(row, planilha);

		assertThat(linha.possuiValorNa(1)).isFalse();
	}

	private Spliterator<Cell> spliterator(Cell... rows) {
		ArrayListIterator<Cell> iterator = new ArrayListIterator<>(rows);
		return Spliterators.spliteratorUnknownSize(iterator, 0);
	}

	private void preparaCelulaString() {
		doReturn(CellType.STRING).when(cell).getCellTypeEnum();
		RichTextString richTextString = mock(RichTextString.class);
		doReturn("TESTE").when(richTextString).getString();
		doReturn(richTextString).when(cell).getRichStringCellValue();
	}

}