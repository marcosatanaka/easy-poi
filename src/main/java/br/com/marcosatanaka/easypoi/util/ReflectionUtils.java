package br.com.marcosatanaka.easypoi.util;

import br.com.marcosatanaka.easypoi.excel.ExcelColumn;
import br.com.marcosatanaka.easypoi.exception.FieldByColumnNameNotFoundException;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

public class ReflectionUtils {

	private ReflectionUtils() {
	}

	public static List<String> getExcelColumnNames(Class clazz) {
		return Arrays.stream(clazz.getDeclaredFields())
				.map(field -> field.getAnnotation(ExcelColumn.class))
				.filter(Objects::nonNull)
				.map(ExcelColumn::name)
				.collect(Collectors.toList());
	}

	public static List<Object> getValues(Object obj) {
		return Arrays.stream(obj.getClass().getDeclaredFields())
				.map(field -> {
					try {
						boolean accessible = field.isAccessible();
						field.setAccessible(true);
						Object valor = field.get(obj);
						field.setAccessible(accessible);
						return valor;
					} catch (IllegalAccessException e) {
						return null;
					}
				})
				.collect(Collectors.toList());
	}

	public static void setField(Object dto, String excelColumnName, Object valor) {
		Field field = getFieldByAnnotationName(dto, excelColumnName);
		field.setAccessible(true);
		setField(field, dto, valor);
	}

	private static Field getFieldByAnnotationName(Object dto, String excelColumnName) {
		return Arrays.stream(dto.getClass().getDeclaredFields())
				.filter(field -> withAnnotationColumnName(excelColumnName, field))
				.findFirst()
				.orElseThrow(() -> new FieldByColumnNameNotFoundException(excelColumnName));
	}

	private static boolean withAnnotationColumnName(String columnName, Field field) {
		ExcelColumn columnAnnotation = field.getAnnotation(ExcelColumn.class);
		return !isNull(columnAnnotation) && columnAnnotation.name().equalsIgnoreCase(columnName);
	}

	private static void setField(Field field, Object target, Object value) {
		try {
			field.set(target, value);
		} catch (IllegalAccessException e) {
			throw new IllegalStateException("Unexpected reflection exception - " + e.getClass().getName() + ": " + e.getMessage());
		}
	}

}
