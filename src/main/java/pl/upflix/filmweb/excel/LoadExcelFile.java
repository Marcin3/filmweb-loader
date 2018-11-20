package pl.upflix.filmweb.excel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import org.apache.poi.ss.usermodel.WorkbookFactory;

public class LoadExcelFile {
	private final String path;
	private Sheet sheet;
	private final List<String> stringList = new ArrayList<>();
	
	public LoadExcelFile(final String path) {
		this.path = path;
	}
	
	public List<String> getStringList() {
		return Collections.unmodifiableList(stringList);
	}
	
	public LoadExcelFile writeMovieToList() {
		sheet = createSheet();
		for (final Row row : sheet) {
			final String cellValue = "".equals(getOriginalTitle(row)) ? getPolishTitle(row) : getOriginalTitle(row);
			stringList.add(cellValue);
		}
		return this;
	}
	
	private Sheet createSheet() {
		try {
			sheet = WorkbookFactory.create(new File(path))
					.getSheetAt(0);
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return sheet;
	}
	
	private String getPolishTitle(final Row row) {
		return new DataFormatter().formatCellValue(row.getCell(1));
	}
	
	private String getOriginalTitle(final Row row) {
		return new DataFormatter().formatCellValue(row.getCell(2));
	}
	
}
