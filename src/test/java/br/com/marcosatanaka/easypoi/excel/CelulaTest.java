package br.com.marcosatanaka.easypoi.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class CelulaTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void deve_lancar_excecao_ao_construir_sem_cell() {
		expectedException.expect(NullPointerException.class);
		Celula.of(null, "");
	}

	@Test
	public void deve_retornar_nome_coluna() {
		Celula celula = Celula.of(mock(Cell.class), "coluna");
		assertThat(celula.nomeColuna()).isEqualTo("coluna");
	}

	@Test
	public void deve_retornar_se_possui_valor() {
		Celula celula = Celula.of(mock(Cell.class), "coluna");
		celula = spy(celula);

		doReturn(null).when(celula).valor();
		assertThat(celula.possuiValor()).isFalse();

		doReturn("").when(celula).valor();
		assertThat(celula.possuiValor()).isFalse();

		doReturn("asd").when(celula).valor();
		assertThat(celula.possuiValor()).isTrue();
	}

}
