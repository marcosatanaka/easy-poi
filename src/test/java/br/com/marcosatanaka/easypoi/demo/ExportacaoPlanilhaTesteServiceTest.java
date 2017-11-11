package br.com.marcosatanaka.easypoi.demo;

import br.com.marcosatanaka.easypoi.dto.ArquivoDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ExportacaoPlanilhaTesteServiceTest {

	@InjectMocks
	private ExportacaoPlanilhaTesteService service;

	@InjectMocks
	private ImportacaoPlanilhaTesteService importacaoPlanilhaTesteService;

	@Test
	public void importarUsuarios() throws IOException {
		File arquivo = service.exportar();

		assertThat(arquivo).isNotNull();

		List<ImportacaoDTO> produtosImportados = importacaoPlanilhaTesteService.importar(ArquivoDTO.of("asd.xlsx", new FileInputStream(arquivo)));

		assertThat(produtosImportados).isNotNull();
		assertThat(produtosImportados).hasSize(2);
	}

}
