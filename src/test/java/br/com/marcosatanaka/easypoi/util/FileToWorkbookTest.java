package br.com.marcosatanaka.easypoi.util;


import br.com.marcosatanaka.easypoi.dto.ArquivoDTO;
import br.com.marcosatanaka.easypoi.exception.ExtensaoInvalidaException;
import br.com.marcosatanaka.easypoi.util.FileToWorkbook;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

import static br.com.marcosatanaka.easypoi.util.FileToWorkbook.EXTENSAO_INVALIDA;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class FileToWorkbookTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Mock
	private ArquivoDTO file;

	@Test
	public void deve_lancar_excecao_quando_extensao_for_invalida() throws IOException {
		expectedException.expect(ExtensaoInvalidaException.class);
		expectedException.expectMessage(EXTENSAO_INVALIDA);

		doReturn("txt").when(file).getExtensaoArquivo();

		FileToWorkbook.toWorkbook(file);
	}

}
