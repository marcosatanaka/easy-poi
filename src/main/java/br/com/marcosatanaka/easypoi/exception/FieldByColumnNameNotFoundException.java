package br.com.marcosatanaka.easypoi.exception;

public class FieldByColumnNameNotFoundException extends RuntimeException {

	static final String FIELD_BY_COLUMN_NAME_NOT_FOUND = "Ops! Este arquivo esta incorreto, a coluna '%s' não faz parte do processo. Altere-o e tente novamente!";

	public FieldByColumnNameNotFoundException(String columnName) {
		super(String.format(FIELD_BY_COLUMN_NAME_NOT_FOUND, columnName));
	}

}
