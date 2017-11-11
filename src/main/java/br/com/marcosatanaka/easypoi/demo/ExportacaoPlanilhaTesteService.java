package br.com.marcosatanaka.easypoi.demo;

import br.com.marcosatanaka.easypoi.service.ExportacaoExcelService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Service
public class ExportacaoPlanilhaTesteService {

	public File exportar() throws IOException {
		return new ExportacaoExcelService<>(ExportacaoDTO.class)
				.exportar(produtos());
	}

	private List<ExportacaoDTO> produtos() {
		return newArrayList(
				ExportacaoDTO.of("José", "58811584159", BigDecimal.ONE),
				ExportacaoDTO.of("João", "96776772843", BigDecimal.TEN)
		);
	}

}
