package br.com.marcosatanaka.easypoi.service;

import br.com.marcosatanaka.easypoi.excel.Linha;
import br.com.marcosatanaka.easypoi.exception.NoDefaultConstructorException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static br.com.marcosatanaka.easypoi.service.ImportacaoExcelService.DTO_IMPORTADO_DEVE_TER_CONSTRUTOR_PADRAO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ImportacaoExcelServiceTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Mock
	private Linha linha;

	@Test
	public void deve_lancar_excecao_ao_instanciar_sem_file() {
		expectedException.expect(NullPointerException.class);
		new ImportacaoExcelService<>(null);
	}

	@Test
	public void deve_instanciar_dto() {
		ImportacaoExcelService<DtoParaTeste> importacaoExcelService = new ImportacaoExcelService(DtoParaTeste.class);

		DtoParaTeste objetoInstanciado = importacaoExcelService.instanciarDTO();

		assertThat(objetoInstanciado).isNotNull();
		assertThat(objetoInstanciado).isInstanceOf(DtoParaTeste.class);
	}

	@Test
	public void deve_lancar_excecao_pois_classe_nao_possui_construtor_padrao() {
		expectedException.expect(NoDefaultConstructorException.class);
		expectedException.expectMessage(String.format(DTO_IMPORTADO_DEVE_TER_CONSTRUTOR_PADRAO, DtoSemConstrutorPadrao.class.getSimpleName()));

		ImportacaoExcelService<DtoSemConstrutorPadrao> importacaoExcelService = new ImportacaoExcelService(DtoSemConstrutorPadrao.class);
		importacaoExcelService.instanciarDTO();
	}

	@Test
	public void deve_lancar_excecao_pois_classe_nao_possui_construtor_padrao_acessivel() {
		expectedException.expect(NoDefaultConstructorException.class);
		expectedException.expectMessage(String.format(DTO_IMPORTADO_DEVE_TER_CONSTRUTOR_PADRAO, DtoSemConstrutorAcessivel.class.getSimpleName()));

		ImportacaoExcelService<DtoSemConstrutorPadrao> importacaoExcelService = new ImportacaoExcelService(DtoSemConstrutorAcessivel.class);
		importacaoExcelService.instanciarDTO();
	}

	@Test
	public void deve_converter_linha_para_dto() {
		ImportacaoExcelService<DtoParaTeste> importacaoExcelService = spy(new ImportacaoExcelService(DtoParaTeste.class));
		DtoParaTeste dto = mock(DtoParaTeste.class);
		doReturn(dto).when(importacaoExcelService).instanciarDTO();

		DtoParaTeste dtoAlimentado = importacaoExcelService.converterParaDTO(linha);

		assertThat(dtoAlimentado).isEqualTo(dto);
		verify(linha, times(1)).alimentarObjeto(dto);
	}

}

class DtoParaTeste {
}

class DtoSemConstrutorPadrao {

	public DtoSemConstrutorPadrao(String nome) {
	}
}

class DtoSemConstrutorAcessivel {
	private DtoSemConstrutorAcessivel() {
	}
}