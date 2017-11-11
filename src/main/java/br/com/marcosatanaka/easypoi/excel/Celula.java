package br.com.marcosatanaka.easypoi.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;

import java.math.BigDecimal;
import java.util.Locale;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.*;

public class Celula {

	private static final DataFormatter DATA_FORMATTER = new DataFormatter(Locale.forLanguageTag("pt-BR"));

	private final Cell cell;

	private final String nomeColuna;

	private Celula(Cell cell, String nomeColuna) {
		this.cell = cell;
		this.nomeColuna = nomeColuna;
	}

	public static Celula of(Cell cell) {
		checkNotNull(cell);
		return new Celula(cell, null);
	}

	public static Celula of(Cell cell, String nomeColuna) {
		checkNotNull(cell);
		return new Celula(cell, nomeColuna);
	}

	public void setValor(Object valor) {
		if (valor instanceof String) {
			cell.setCellValue((String) valor);
		}
		else if (valor instanceof BigDecimal) {
			cell.setCellValue(((BigDecimal) valor).doubleValue());
		}
	}

	public boolean possuiValor() {
		return !isNullOrEmpty(valor());
	}

	public String valor() {
		return DATA_FORMATTER.formatCellValue(cell);
	}

	public String nomeColuna() {
		return nomeColuna;
	}

}
