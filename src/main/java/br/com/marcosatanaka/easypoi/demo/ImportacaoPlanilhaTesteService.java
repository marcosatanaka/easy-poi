package br.com.marcosatanaka.easypoi.demo;

import br.com.marcosatanaka.easypoi.dto.ArquivoDTO;
import br.com.marcosatanaka.easypoi.service.ImportacaoExcelService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ImportacaoPlanilhaTesteService {

	public List<ImportacaoDTO> importar(ArquivoDTO file) throws IOException {
		return new ImportacaoExcelService<>(ImportacaoDTO.class).importar(file);
	}

}
