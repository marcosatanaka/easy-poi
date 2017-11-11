package br.com.marcosatanaka.easypoi.service;

import br.com.marcosatanaka.easypoi.dto.ArquivoDTO;
import br.com.marcosatanaka.easypoi.excel.Linha;
import br.com.marcosatanaka.easypoi.excel.PastaTrabalho;
import br.com.marcosatanaka.easypoi.exception.NoDefaultConstructorException;

import java.io.IOException;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.stream.Collectors.toList;

public class ImportacaoExcelService<T> {

	static final String DTO_IMPORTADO_DEVE_TER_CONSTRUTOR_PADRAO = "Classe %s deve possuir construtor padrão para esta importação.";

	private final Class<T> classeImportar;

	public ImportacaoExcelService(Class<T> classeImportar) {
		checkNotNull(classeImportar);
		this.classeImportar = classeImportar;
	}

	public List<T> importar(ArquivoDTO file) throws IOException {
		PastaTrabalho pastaTrabalho = PastaTrabalho.nova(file);

		return pastaTrabalho.planilhaPrincipal()
				.linhasIgnorandoCabecalho()
				.stream()
				.map(this::converterParaDTO)
				.collect(toList());
	}

	protected T converterParaDTO(Linha linha) {
		T dto = instanciarDTO();
		linha.alimentarObjeto(dto);
		return dto;
	}

	protected T instanciarDTO() {
		try {
			return classeImportar.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new NoDefaultConstructorException(
					String.format(DTO_IMPORTADO_DEVE_TER_CONSTRUTOR_PADRAO, classeImportar.getSimpleName()));
		}
	}

}
