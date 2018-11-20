package pl.upflix.filmweb.excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

public class WriteIntoExcel {
	private final HSSFWorkbook workbook = new HSSFWorkbook();
	private FileOutputStream fileOutputStream;
	public WriteIntoExcel() {
	}
	
	public WriteIntoExcel createWorkBook(final Map<String, List<String>> data) {
		final HSSFSheet sheet = workbook.createSheet("Sample sheet");
		final Set<String> keySet = data.keySet();
		int rowNumber = 0;
		for (final String key : keySet) {
			final Row row = sheet.createRow(rowNumber++);
			final List<String> movieList = data.get(key);
			int cellNumber = 0;
			row.createCell(cellNumber).setCellValue(key);
			for (final String obj : movieList) {
				row.createCell(++cellNumber).setCellValue(obj);
			}
		}
		return this;
	}
	
	public void saveToFile(final String path) {
		createFile(path);
		try {
			workbook.write(fileOutputStream);
			fileOutputStream.close();
			workbook.close();
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
	}
	
	private void createFile(final String path) {
		try {
			fileOutputStream = new FileOutputStream(new File(path));
		}
		catch (final FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
