package br.com.marcosatanaka.easypoi.dto;

import br.com.marcosatanaka.easypoi.util.FileTypeExtractor;
import com.google.common.base.MoreObjects;

import java.io.InputStream;
import java.util.Objects;

public class ArquivoDTO {

	private final String nome;

	private final InputStream arquivo;

	private ArquivoDTO(String nome, InputStream arquivo) {
		this.nome = nome;
		this.arquivo = arquivo;
	}

	public static ArquivoDTO of(String nome, InputStream arquivo) {
		return new ArquivoDTO(nome, arquivo);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ArquivoDTO) {
			ArquivoDTO other = (ArquivoDTO) obj;
			return Objects.equals(this.nome, other.nome) &&
					Objects.equals(this.arquivo, other.arquivo);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(nome, arquivo);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("nome", nome)
				.toString();
	}

	public String getNome() {
		return nome;
	}

	public String getExtensaoArquivo() {
		return FileTypeExtractor.extract(nome);
	}

	public InputStream getArquivo() {
		return arquivo;
	}

}
