package pl.upflix.filmweb;


import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.restassured.response.Response;

@SuppressWarnings("unchecked")
public class MainClass {
	
	private static final String TITLE_PATTERN = "href(.*)title=\"([A-Za-z0-9.,:\\s]{1,100})\"";
	private static final String URL = "https://upflix.pl/netflix:showmax:hbogo:amazon/wszystko/szukaj:Godfather/tlumaczenie:dowolne/dni:/zmieniono";
	private static final int responseCode = 200;
	
	/**
	 * 1. Check what happened here
	 * 2. Added loading List from file xls
	 * 3. How should work
	 * 	bash commend: file.java -path-to-xls-file
	 * 	response: name of movie in request: response list of movie
	 * 4. Ultimately this should work:
	 * 	bash commend: file.java -path-to-xls-file
	 * 	response: program click plus button
	 **/
	
	private static String getHtml() {
		final Response response = given()
				.relaxedHTTPSValidation()
				.when()
				.get(URL)
				.then()
				.statusCode(responseCode)
				.extract()
				.response();
		
		return response.asString();
	}
	
	private static List<String> getTitleFromHtml(final String html) {
		
		final List<String> titlesList = new ArrayList();
		final Matcher matcher = Pattern.compile(TITLE_PATTERN)
				.matcher(html);
		while (matcher.find()) {
			titlesList.add(matcher.group(2));
		}
		return titlesList;
	}
	
	public static void main(final String[] args) {
		final String html = getHtml();
		final List<String> titlesList = getTitleFromHtml(html);
		System.out.println(titlesList);
	}
}
