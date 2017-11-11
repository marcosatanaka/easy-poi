package br.com.marcosatanaka.easypoi.demo;


import br.com.marcosatanaka.easypoi.dto.ArquivoDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ImportacaoPlanilhaTesteServiceTest {

	private static final String ARQUIVO_XLS = "src/test/java/br/com/marcosatanaka/easypoi/demo/arquivo.xls";

	private static final String ARQUIVO_XLSX = "src/test/java/br/com/marcosatanaka/easypoi/demo/arquivo.xlsx";

	@InjectMocks
	private ImportacaoPlanilhaTesteService service;

	@Test
	public void deve_ler_e_conveter_arquivo_xls() throws Exception {
		List<ImportacaoDTO> result = service.importar(arquivo(ARQUIVO_XLS));

		assertThat(result).isNotNull();
		assertThat(result).hasSize(2);
		verify(result);
	}

	@Test
	public void deve_ler_e_conveter_arquivo_xlsx() throws Exception {
		List<ImportacaoDTO> result = service.importar(arquivo(ARQUIVO_XLSX));

		assertThat(result).isNotNull();
		assertThat(result).hasSize(2);
		verify(result);
	}

	private void verify(List<ImportacaoDTO> result) {
		assertThat(result.get(0).getNome()).isEqualTo("José dos Santos");
		assertThat(result.get(0).getCpf()).isEqualTo("11746757728");
		assertThat(result.get(0).getRenda()).isEqualTo(BigDecimal.valueOf(4567.89));

		assertThat(result.get(1).getNome()).isEqualTo("João da Silva");
		assertThat(result.get(1).getCpf()).isEqualTo("00058834230");
		assertThat(result.get(1).getRenda()).isEqualTo(BigDecimal.valueOf(1472.25));
	}

	private ArquivoDTO arquivo(String path) throws FileNotFoundException {
		File file = new File(path);
		return ArquivoDTO.of(file.getName(), new FileInputStream(file));
	}

}
