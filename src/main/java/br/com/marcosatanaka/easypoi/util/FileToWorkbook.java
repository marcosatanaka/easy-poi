package br.com.marcosatanaka.easypoi.util;

import br.com.marcosatanaka.easypoi.dto.ArquivoDTO;
import br.com.marcosatanaka.easypoi.exception.ExtensaoInvalidaException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;

public class FileToWorkbook {

	static final String XLS = "xls";

	static final String XLSX = "xlsx";

	static final String EXTENSAO_INVALIDA = "Extensão inválida";

	private FileToWorkbook() {
	}

	public static final Workbook toWorkbook(ArquivoDTO arquivo) throws IOException {
		String extensao = arquivo.getExtensaoArquivo();

		if (XLS.equalsIgnoreCase(extensao)) {
			return new HSSFWorkbook(arquivo.getArquivo());
		}
		if (XLSX.equalsIgnoreCase(extensao)) {
			return new XSSFWorkbook(arquivo.getArquivo());
		}

		throw new ExtensaoInvalidaException(EXTENSAO_INVALIDA);
	}

}