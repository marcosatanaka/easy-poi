package br.com.marcosatanaka.easypoi.excel;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

public class PastaTrabalhoTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void deve_lancar_excecao_ao_construir_sem_file() throws IOException {
		expectedException.expect(NullPointerException.class);
		PastaTrabalho.nova(null);
	}

}
