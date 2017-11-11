package br.com.marcosatanaka.easypoi.demo;

import br.com.marcosatanaka.easypoi.excel.ExcelColumn;

import java.math.BigDecimal;

public class ExportacaoDTO {

	@ExcelColumn(name = "NOME")
	private String nome;

	@ExcelColumn(name = "CPF")
	private String cpf;

	@ExcelColumn(name = "RENDA")
	private BigDecimal renda;

	public ExportacaoDTO() {
	}

	private ExportacaoDTO(String nome, String cpf, BigDecimal renda) {
		this.nome = nome;
		this.cpf = cpf;
		this.renda = renda;
	}

	public static ExportacaoDTO of(String nome, String cpf, BigDecimal renda) {
		return new ExportacaoDTO(nome, cpf, renda);
	}

}
