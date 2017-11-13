package br.com.marcosatanaka.easypoi.excel;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.google.common.base.Preconditions.checkNotNull;

public class Planilha {

	private final Sheet sheet;

	private Planilha(Sheet sheet) {
		this.sheet = sheet;
	}

	public static Planilha of(Sheet sheet) {
		checkNotNull(sheet);
		return new Planilha(sheet);
	}

	public Linha novaLinha() {
		return Linha.of(sheet.createRow(sheet.getPhysicalNumberOfRows()), this);
	}

	public List<Linha> linhasIgnorandoCabecalho() {
		return StreamSupport.stream(sheet.spliterator(), false)
				.skip(1)
				.map(row -> Linha.of(row, this))
				.collect(Collectors.toList());
	}

	public List<Linha> linhasAPartirDa(Integer linha) {
		return StreamSupport.stream(sheet.spliterator(), false)
				.skip(linha - 1)
				.map(row -> Linha.of(row, this))
				.collect(Collectors.toList());
	}

	public String valorNa(Integer coluna, Integer linha) {
		return Linha.of(sheet.getRow(linha - 1), this).valorNa(coluna);
	}

	String nomeColuna(Integer indice) {
		return cabecalho().getCell(indice).getStringCellValue();
	}

	protected Row cabecalho() {
		return sheet.getRow(0);
	}

}
