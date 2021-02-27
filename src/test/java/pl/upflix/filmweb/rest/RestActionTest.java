package pl.upflix.filmweb.rest;

import static org.assertj.core.api.BDDAssertions.then;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class RestActionTest {
	private static final String URL_MYSTIC_RIVER = "https://upflix.pl/Mystic River";
	private RestAction restAction;

	@BeforeClass
	public void setUp() {
		restAction = new RestAction();
	}

	@Test
	public void createURLTest() {
		then(restAction.createURL("Mystic River"))
				.isEqualTo(URL_MYSTIC_RIVER);
	}

	@DataProvider(name = "correctResponse")
	public Object[][] basicData() {
		return new Object[][]{
				{"znalezionych tytułów: 1"},
				{"Lista filmów i seriali VOD w Polsce | Najnowsze zmiany"},
				{"Wszystkie aktualności upflix.pl | Netflix | HBO GO | Amazon Prime Video | Chili | Cineman | Ipla | Rakuten | iTunes | Player | VOD.pl | TVP VOD | Apple TV+ | PLAY NOW"},
		};
	}

	@Test(dataProvider = "correctResponse")
	public void getHtmlFromURLTest(final String expectedText) {
		then(restAction.getHtmlFromURL(URL_MYSTIC_RIVER))
				.contains(expectedText);
	}
}
