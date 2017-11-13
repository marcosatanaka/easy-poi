package br.com.marcosatanaka.easypoi.util;

import br.com.marcosatanaka.easypoi.excel.ExcelColumn;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ReflectionUtilsTest {

	@Test
	public void deve_retornar_lista_com_valores_anotacao_excel_column() {
		List<String> excelColumnNames = ReflectionUtils.getExcelColumnNames(DtoTeste.class);

		assertThat(excelColumnNames).isNotNull();
		assertThat(excelColumnNames).hasSize(2);
		assertThat(excelColumnNames.get(0)).isEqualTo("NOME");
		assertThat(excelColumnNames.get(1)).isEqualTo("CPF");
	}

	@Test
	public void deve_retornar_lista_vazia_quando_nao_ha_atributos_com_anotacao_excel_column() {
		List<String> excelColumnNames = ReflectionUtils.getExcelColumnNames(DtoTeste2.class);

		assertThat(excelColumnNames).isNotNull();
		assertThat(excelColumnNames).hasSize(0);
	}

	@Test
	public void deve_retornar_lista_com_valores_dos_atributos_do_objeto() {
		DtoTeste2 dto = new DtoTeste2();
		dto.nome = "JOÃO DA SILVA";
		dto.cpf = "97181775316";

		List<Object> values = ReflectionUtils.getValues(dto);

		assertThat(values).isNotNull();
		assertThat(values).hasSize(2);
		assertThat(values.get(0)).isEqualTo(dto.nome);
		assertThat(values.get(1)).isEqualTo(dto.cpf);
	}

	@Test
	public void deve_setar_valor_no_atributo_do_objeto() {
		DtoTeste dto = new DtoTeste();
		ReflectionUtils.setField(dto, "NOME", "JOÃO");

		assertThat(dto.nome).isEqualTo("JOÃO");
	}

}

class DtoTeste {

	@ExcelColumn(name = "NOME")
	String nome;

	@ExcelColumn(name = "CPF")
	String cpf;

}

class DtoTeste2 {

	String nome;

	String cpf;

}