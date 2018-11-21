package pl.upflix.filmweb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pl.upflix.filmweb.excel.LoadExcelFile;
import pl.upflix.filmweb.excel.WriteIntoExcel;
import pl.upflix.filmweb.rest.RestAction;

public class FilmwebLoader {
	
	private static final String TITLE_PATTERN = "href(.*)title=\"([A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ0-9.,:\\s]{1,100})\"";
	private static final Map<String, List<String>> resultMap = new HashMap<>();
	private static final Logger logger = Logger.getLogger(FilmwebLoader.class.getName());
	
	public static void main(final String[] args) {
		final List<String> movieList = new LoadExcelFile(args[0]).writeMovieToList()
				.getStringList();
		movieList.forEach(FilmwebLoader::createHtmlAndAddToList);
		resultMap.entrySet().removeIf(entry -> entry.getValue().isEmpty());
		logger.info("Create resultMap with size:  " + resultMap.size());
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
		final RestAction restAction = new RestAction();
		logger.info("Search for movie: " + movie);
		final String url = restAction.createURL(movie);
		final String html = restAction.getHtmlFromURL(url);
		resultMap.put(movie, getTitleFromHtml(html));
	}
	
}
