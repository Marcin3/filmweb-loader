package pl.upflix.filmweb.excel;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LoadExcelFileTest {
	
	private static final String path = ".\\src\\test\\java\\resource\\pl\\upflix\\filmweb\\excel\\movie.xls";
	private LoadExcelFile loadExcelFile;
	
	@BeforeClass
	public void loadFile() {
		loadExcelFile = new LoadExcelFile(path).writeMovieToList();
	}
	
	@Test
	public void checkSize() {
		Assert.assertEquals(loadExcelFile.getStringList().size(), 6);
	}
	
	@Test
	public void checkValue() {
		Assert.assertTrue(loadExcelFile.getStringList().contains("Okolofutbola"));
		Assert.assertTrue(loadExcelFile.getStringList().contains("Klimt"));
		Assert.assertTrue(loadExcelFile.getStringList().contains("Amok"));
		Assert.assertTrue(loadExcelFile.getStringList().contains("My Big Fat Greek Wedding 2"));
		Assert.assertTrue(loadExcelFile.getStringList().contains("Kynodontas"));
	}
	
}
