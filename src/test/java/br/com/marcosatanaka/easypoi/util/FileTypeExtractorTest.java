package br.com.marcosatanaka.easypoi.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class FileTypeExtractorTest {

	private final String filename;

	private final String filetype;

	public FileTypeExtractorTest(String filename, String filetype) {
		this.filename = filename;
		this.filetype = filetype;
	}

	@Parameters(name = "{index}: deve alimentar {0} em \"{1}\"")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][]{
				// Nomes inválidos
				{"Arquivo", null},
				{"", null},
				{null, null},

				// Nomes válidos
				{"Arquivo.txt", "txt"},
				{"Arquivo.novo.xls", "xls"},
				{"Nome com espaços.doc", "doc"},
				{"Extensão maior que três caracteres.docx", "docx"},
				{"Arquivo........pdf", "pdf"},
				{".......Arquivo.xls", "xls"},
				{"Nome com números 123456.zip", "zip"},
				{"Nome com caracteres especiais !\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~.jpg", "jpg"}
		});
	}

	@Test
	public void test() {
		assertEquals(filetype, FileTypeExtractor.extract(filename));
	}

}
