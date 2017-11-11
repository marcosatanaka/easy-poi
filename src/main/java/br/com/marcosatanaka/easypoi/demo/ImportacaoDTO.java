package br.com.marcosatanaka.easypoi.demo;

import br.com.marcosatanaka.easypoi.excel.ExcelColumn;
import br.com.marcosatanaka.easypoi.util.StringToBigDecimal;
import com.google.common.base.MoreObjects;

import java.math.BigDecimal;
import java.util.Objects;

public class ImportacaoDTO {

	@ExcelColumn(name = "NOME")
	private String nome;

	@ExcelColumn(name = "CPF")
	private String cpf;

	@ExcelColumn(name = "RENDA")
	private String renda;

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ImportacaoDTO) {
			ImportacaoDTO that = (ImportacaoDTO) obj;
			return Objects.equals(nome, that.nome) &&
					Objects.equals(cpf, that.cpf) &&
					Objects.equals(renda, that.renda);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(nome, cpf, renda);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("nome", nome)
				.add("cpf", cpf)
				.add("renda", renda)
				.toString();
	}

	public String getNome() {
		return nome;
	}

	public String getCpf() {
		return cpf;
	}

	public BigDecimal getRenda() {
		return StringToBigDecimal.convert(renda);
	}

}
