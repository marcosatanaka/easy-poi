package br.com.marcosatanaka.easypoi.excel;

import br.com.marcosatanaka.easypoi.dto.ArquivoDTO;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import static br.com.marcosatanaka.easypoi.util.FileToWorkbook.toWorkbook;
import static com.google.common.base.Preconditions.checkNotNull;

public class PastaTrabalho {

	private final Workbook workbook;

	private PastaTrabalho(Workbook workbook) {
		this.workbook = workbook;
	}

	public static PastaTrabalho nova() {
		Workbook workbook = new XSSFWorkbook();
		workbook.createSheet();
		return new PastaTrabalho(workbook);
	}

	public static PastaTrabalho nova(ArquivoDTO arquivo) throws IOException {
		checkNotNull(arquivo);
		return new PastaTrabalho(toWorkbook(arquivo));
	}

	public Planilha planilhaPrincipal() {
		return Planilha.of(workbook.getSheetAt(0));
	}

	public File exportar() throws IOException {
		File arquivo = File.createTempFile(UUID.randomUUID().toString(), ".xlsx");

		try (OutputStream outputStream = new FileOutputStream(arquivo)) {
			workbook.write(outputStream);
			workbook.close();
		}

		return arquivo;
	}

}
