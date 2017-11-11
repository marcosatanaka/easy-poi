package br.com.marcosatanaka.easypoi.util;

import br.com.marcosatanaka.easypoi.exception.StringToBigDecimalException;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;

import static java.util.Objects.isNull;

public class StringToBigDecimal {

	private static final String NON_NUMERIC_EXCEPT_DOT_AND_COMMA = "[^0-9 .,]|(?<!\\d)[.,]|[.,](?!\\d)";

	private static final DecimalFormat DECIMAL_FORMAT;

	static {
		DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(Locale.forLanguageTag("pt-BR"));
		symbols.setGroupingSeparator('.');
		symbols.setDecimalSeparator(',');
		DECIMAL_FORMAT = new DecimalFormat("#,##0.0#", symbols);
		DECIMAL_FORMAT.setParseBigDecimal(true);
	}

	private StringToBigDecimal() {
	}

	public static BigDecimal convert(String value) {
		String preparedValue = prepare(value);
		if (preparedValue.isEmpty()) {
			return null;
		}

		try {
			return (BigDecimal) DECIMAL_FORMAT.parse(preparedValue);
		}
		catch (ParseException e) {
			throw new StringToBigDecimalException(e);
		}
	}

	private static String prepare(String value) {
		return isNull(value) ? "" : value.replaceAll(NON_NUMERIC_EXCEPT_DOT_AND_COMMA, "").trim();
	}

}
