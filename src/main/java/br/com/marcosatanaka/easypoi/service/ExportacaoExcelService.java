package br.com.marcosatanaka.easypoi.service;


import br.com.marcosatanaka.easypoi.excel.PastaTrabalho;
import br.com.marcosatanaka.easypoi.excel.Planilha;
import br.com.marcosatanaka.easypoi.util.ReflectionUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static br.com.marcosatanaka.easypoi.util.ReflectionUtils.getExcelColumnNames;
import static com.google.common.base.Preconditions.checkNotNull;

public class ExportacaoExcelService<T> {

	private final Class<T> classeExportar;

	public ExportacaoExcelService(Class<T> classeExportar) {
		checkNotNull(classeExportar);
		this.classeExportar = classeExportar;
	}

	public File exportar(List<T> objetos) throws IOException {
		checkNotNull(objetos);

		PastaTrabalho pastaTrabalho = PastaTrabalho.nova();
		Planilha planilha = pastaTrabalho.planilhaPrincipal();
		planilha.novaLinha().preencherCom(getExcelColumnNames(classeExportar));

		objetos.stream()
				.map(ReflectionUtils::getValues)
				.forEach(valores -> planilha.novaLinha().preencherCom(valores));

		return pastaTrabalho.exportar();
	}

}
