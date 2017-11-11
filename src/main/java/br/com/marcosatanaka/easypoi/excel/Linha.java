package br.com.marcosatanaka.easypoi.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static br.com.marcosatanaka.easypoi.util.ReflectionUtils.setField;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;

public class Linha {

	private final Planilha planilha;

	private final Row row;

	private Linha(Row row, Planilha planilha) {
		this.row = row;
		this.planilha = planilha;
	}

	public static Linha of(Row row, Planilha planilha) {
		checkNotNull(row);
		checkNotNull(planilha);
		return new Linha(row, planilha);
	}

	public void alimentarObjeto(Object dto) {
		celulas().forEach(celula -> setField(dto, celula.nomeColuna(), celula.valor()));
	}

	public void preencherCom(List<?> valores) {
		valores.forEach(valor -> novaCelula().setValor(valor));
	}

	protected Celula novaCelula() {
		return Celula.of(row.createCell(row.getPhysicalNumberOfCells()));
	}

	protected List<Celula> celulas() {
		return StreamSupport.stream(row.spliterator(), false)
				.map(cell -> Celula.of(cell, nomeColuna(cell)))
				.collect(Collectors.toList());
	}

	public boolean possuiValores() {
		return StreamSupport.stream(row.spliterator(), false)
				.map(Celula::of)
				.anyMatch(Celula::possuiValor);
	}

	public boolean possuiValorNa(Integer coluna) {
		Cell celula = row.getCell(coluna);
		return Celula.of(celula, nomeColuna(celula)).possuiValor();
	}

	public String valorNa(Integer coluna) {
		Cell celula = row.getCell(coluna);
		return Celula.of(celula, nomeColuna(celula)).valor();
	}

	protected String nomeColuna(Cell cell) {
		return planilha.nomeColuna(cell.getColumnIndex());
	}

	Row getRow() {
		return row;
	}

}
