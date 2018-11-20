package pl.upflix.filmweb.rest;

import static io.restassured.RestAssured.given;

import io.restassured.response.Response;

public class RestAction {
	private static final int STATUS_CODE_OK = 200;
	
	public RestAction() {
	}
	
	public String createURL(final String movie) {
		return "https://upflix.pl/" + movie;
	}
	
	public String getHtmlFromURL(final String url) {
		final Response response = given().relaxedHTTPSValidation()
				.when()
				.get(url)
				.then()
				.statusCode(STATUS_CODE_OK)
				.extract()
				.response();
		
		return response.asString();
	}
	
}
