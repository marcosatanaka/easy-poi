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

	private static final DecimalFormat DECIMAL_FORMAT_WITH_GROUP_SEPARATOR;

	private static final DecimalFormat DECIMAL_FORMAT_WITHOUT_GROUP_SEPARATOR;

	private static final String GROUP_SEPARATOR_COMMA = ",";

	static {
		DecimalFormatSymbols symbolsWithGroupSeparators = DecimalFormatSymbols.getInstance(Locale.forLanguageTag("pt-BR"));
		symbolsWithGroupSeparators.setGroupingSeparator('.');
		symbolsWithGroupSeparators.setDecimalSeparator(',');
		DECIMAL_FORMAT_WITH_GROUP_SEPARATOR = new DecimalFormat("#,##0.0#", symbolsWithGroupSeparators);
		DECIMAL_FORMAT_WITH_GROUP_SEPARATOR.setParseBigDecimal(true);

		DecimalFormatSymbols symbolsWithoutGroupSeparators = DecimalFormatSymbols.getInstance(Locale.forLanguageTag("pt-BR"));
		symbolsWithoutGroupSeparators.setDecimalSeparator('.');
		DECIMAL_FORMAT_WITHOUT_GROUP_SEPARATOR = new DecimalFormat("#0.0#", symbolsWithoutGroupSeparators);
		DECIMAL_FORMAT_WITHOUT_GROUP_SEPARATOR.setParseBigDecimal(true);
	}

	private StringToBigDecimal() {
	}

	public static BigDecimal convert(String value) {
		String preparedValue = prepare(value);
		if (preparedValue.isEmpty()) {
			return null;
		}

		try {
			if (preparedValue.contains(GROUP_SEPARATOR_COMMA)) {
				return (BigDecimal) DECIMAL_FORMAT_WITH_GROUP_SEPARATOR.parse(preparedValue);
			}
			else {
				return (BigDecimal) DECIMAL_FORMAT_WITHOUT_GROUP_SEPARATOR.parse(preparedValue);
			}
		}
		catch (ParseException e) {
			throw new StringToBigDecimalException(e);
		}
	}

	private static String prepare(String value) {
		return isNull(value) ? "" : value.replaceAll(NON_NUMERIC_EXCEPT_DOT_AND_COMMA, "").trim();
	}

}
