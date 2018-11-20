package pl.upflix.filmweb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pl.upflix.filmweb.excel.LoadExcelFile;
import pl.upflix.filmweb.excel.WriteIntoExcel;
import pl.upflix.filmweb.rest.RestAction;

/**
 * 1. Check what happened here  - DONE
 * 2. Added loading List from file xls
 * 3. How should work
 * bash commend: file.java -path-to-xls-file
 * response: name of movie in request: response list of movie
 * 4. Ultimately this should work:
 * bash commend: file.java -path-to-xls-file
 * response: program click plus button
 * <p>
 * 1. stworz mape nazwa filmu ->wyszukan lista
 * 2. trzeba to uruchomic tworzenie czyli ladowanie tego pliku
 * 3. potem ladowanie danych
 * 4. potem sortowanie mapy aby wartosc List<string?> byla sortowana
 * 5. export do excela
 **/
public class FilmwebLoader {
	
	private static final String TITLE_PATTERN = "href(.*)title=\"([A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ0-9.,:\\s]{1,100})\"";
	private static final Map<String, List<String>> resultMap = new HashMap<>();
	
	public static void main(final String[] args) {
		final List<String> movieList = new LoadExcelFile(args[0]).writeMovieToList()
				.getStringList();
		movieList.forEach(FilmwebLoader::createHtmlAndAddToList);
		resultMap.entrySet().removeIf(entry -> entry.getValue().size() == 0);
		System.out.println("Create resultMap with size: " + resultMap.size());
		new WriteIntoExcel().createWorkBook(resultMap)
				.saveToFile(args[1]);
	}
	
	private static List<String> getTitleFromHtml(final String html) {
		final List<String> titlesList = new ArrayList<>();
		final Matcher matcher = Pattern.compile(TITLE_PATTERN)
				.matcher(html);
		while (matcher.find()) {
			titlesList.add(matcher.group(2));
		}
		titlesList.remove("Chili");
		return titlesList;
	}
	
	private static void createHtmlAndAddToList(final String movie) {
		RestAction restAction = new RestAction();
		final String url = restAction.createURL(movie);
		final String html = restAction.getHtmlFromURL(url);
		resultMap.put(movie, getTitleFromHtml(html));
	}
	
}
