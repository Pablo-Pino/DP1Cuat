
package services;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Endorsable;
import domain.Endorsement;
import domain.Settings;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SettingsServiceTest extends AbstractTest {

	// Services

	@Autowired
	private SettingsService		settingsService;
	@Autowired
	private EndorsableService	endorsableService;
	@Autowired
	private EndorsementService	endorsementService;


	// Tests

	public void findOneSettings(final Integer settingsId, final Class<?> expected) {
		Class<?> caught = null;
		try {
			this.settingsService.findOne(settingsId);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	public void findAllSettings(final Collection<Integer> settingsIds, final Class<?> expected) {
		Class<?> caught = null;
		try {
			this.settingsService.findAll(settingsIds);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	public void findAllSettings(final Class<?> expected) {
		Class<?> caught = null;
		try {
			this.settingsService.findAll();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	public void saveSettings(final String username, final String banner, final String countryCode, final Collection<String> creditCardMakes, final int finderCacheHours, final int maxCacheResults, final Collection<String> negativeWords,
		final Collection<String> positiveWords, final Collection<String> spamWords, final String systemName, final int vat, final String welcomeMessageEN, final String welcomeMessageES, final Integer settingsId, final Class<?> expected) {
		Class<?> caught = null;
		try {
			this.authenticate(username);
			Settings settings = null;
			settings = this.settingsService.findOne(settingsId);
			settings.setBanner(banner);
			settings.setCountryCode(countryCode);
			settings.setCreditCardMakes(creditCardMakes);
			settings.setFinderCacheHours(finderCacheHours);
			settings.setMaxCacheResults(maxCacheResults);
			settings.setNegativeWords((List<String>) negativeWords);
			settings.setPositiveWords((List<String>) positiveWords);
			settings.setSpamWords((List<String>) spamWords);
			settings.setSystemName(systemName);
			settings.setVat(vat);
			settings.setWelcomeMessageEnglish(welcomeMessageEN);
			settings.setWelcomeMessageSpanish(welcomeMessageES);
			final Settings savedSettings = this.settingsService.save(settings);
			this.settingsService.flush();
			Assert.isTrue(savedSettings.getBanner().equals(banner));
			Assert.isTrue(savedSettings.getCountryCode().equals(countryCode));
			Assert.isTrue(savedSettings.getCreditCardMakes().equals(creditCardMakes));
			Assert.isTrue(savedSettings.getFinderCacheHours() == (finderCacheHours));
			Assert.isTrue(savedSettings.getMaxCacheResults() == (maxCacheResults));
			Assert.isTrue(savedSettings.getNegativeWords().equals(negativeWords));
			Assert.isTrue(savedSettings.getPositiveWords().equals(positiveWords));
			Assert.isTrue(savedSettings.getSpamWords().equals(spamWords));
			Assert.isTrue(savedSettings.getSystemName().equals(systemName));
			Assert.isTrue(savedSettings.getVat() == vat);
			Assert.isTrue(savedSettings.getWelcomeMessageEnglish().equals(welcomeMessageEN));
			Assert.isTrue(savedSettings.getWelcomeMessageSpanish().equals(welcomeMessageES));
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void testFindOneSettings() {
		this.findOneSettings(super.getEntityId("settings1"), null);
	}

	@Test
	public void testFindOneSettingsNullId() {
		this.findOneSettings(null, IllegalArgumentException.class);
	}

	@Test
	public void testFindAllByIdsSettings() {
		this.findAllSettings(Arrays.asList(new Integer[] {
			super.getEntityId("settings1")
		}), null);
	}

	@Test
	public void testFindAllByIdsSettingsNullIds() {
		this.findAllSettings(null, IllegalArgumentException.class);
	}

	@Test
	public void testFindAllSettings() {
		this.findAllSettings(null);
	}

	@Test
	public void testSaveSettings() {
		final Settings settings = this.settingsService.findOne(this.getEntityId("settings1"));
		this.saveSettings("admin1", "http://banner", "+34", settings.getCreditCardMakes(), 24, 10, settings.getNegativeWords(), settings.getPositiveWords(), settings.getSpamWords(), "Acme-Handy-Worker", 21, "Hi", "Hola", null,
			IllegalArgumentException.class);
	}

	@Test
	public void testUpdateSettings() {
		final Settings settings = this.settingsService.findOne(this.getEntityId("settings1"));
		this.saveSettings("admin1", "http://banner", "+34", settings.getCreditCardMakes(), 24, 10, settings.getNegativeWords(), settings.getPositiveWords(), settings.getSpamWords(), "Acme-Handy-Worker", 21, "Hi", "Hola", this.getEntityId("settings1"),
			null);
	}

	@Test
	public void testUpdateSettingsUnauthenticated() {
		final Settings settings = this.settingsService.findOne(this.getEntityId("settings1"));
		this.saveSettings(null, "http://banner", "+34", settings.getCreditCardMakes(), 24, 10, settings.getNegativeWords(), settings.getPositiveWords(), settings.getSpamWords(), "Acme-Handy-Worker", 21, "Hi", "Hola", this.getEntityId("settings1"),
			IllegalArgumentException.class);
	}

	@Test
	public void testAddPositiveWord() {
		this.authenticate("admin2");
		final String s = "Stupendous!";
		this.settingsService.addPositiveWords(s);
		Assert.isTrue(this.settingsService.findSettings().getPositiveWords().contains("Stupendous!"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddPositiveWordIncorreto() {
		this.authenticate("admin2");
		final String s = "bueno";
		this.settingsService.addPositiveWords(s);
	}

	@Test
	public void testDeletePositiveWord() {
		this.authenticate("admin1");
		final String s = "bueno";
		this.settingsService.deletePositiveWords(s);
		Assert.isTrue(!(this.settingsService.findSettings().getPositiveWords().contains("bueno")));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDeletePositiveWordIncorreto() {
		this.authenticate("admin2");
		final String s = "iusbhdcoi9";
		this.settingsService.deletePositiveWords(s);
	}
	//---------------------------------------------------------------------------------------------------------------
	@Test
	public void testAddNegativeWord() {
		this.authenticate("admin2");
		final String s = "shit";
		this.settingsService.addNegativeWords(s);
		Assert.isTrue(this.settingsService.findSettings().getNegativeWords().contains("shit"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddNegativeWordIncorreto() {
		this.authenticate("admin2");
		final String s = "not";
		this.settingsService.addNegativeWords(s);
	}

	@Test
	public void testDeleteNegativeWord() {
		this.authenticate("admin1");
		final String s = "not";
		this.settingsService.deleteNegativeWords(s);
		Assert.isTrue(!(this.settingsService.findSettings().getNegativeWords().contains("not")));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDeleteNegativeWordIncorreto() {
		this.authenticate("admin2");
		final String s = "iusbhdcoi9";
		this.settingsService.deleteNegativeWords(s);
	}

	@Test
	public void testUpdatePositiveWord() {
		final String oldWord = "bueno";
		final String newWord = "buenisimo";
		this.settingsService.updatePositiveWord(oldWord, newWord);
		Assert.isTrue(!(this.settingsService.findSettings().getNegativeWords().contains("bueno")));
		Assert.isTrue(this.settingsService.findSettings().getPositiveWords().contains("buenisimo"));

	}

	@Test
	public void testUpdateNegativeWord() {
		final String oldWord = "malo";
		final String newWord = "malisimo";
		this.settingsService.updateNegativeWord(oldWord, newWord);
		Assert.isTrue(!(this.settingsService.findSettings().getNegativeWords().contains("malo")));
		Assert.isTrue(this.settingsService.findSettings().getNegativeWords().contains("malisimo"));

	}
	@Test
	public void testGenerateAllScores() {
		this.authenticate("admin1");

		final Endorsable e = this.endorsableService.findOne(this.getEntityId("customer1"));
		final Endorsable e2 = this.endorsableService.findOne(this.getEntityId("customer2"));
		System.out.println("\n Score de customer1 inicial (aleatorio)--> " + e.getScore());
		System.out.println(" Score de customer2 inicial (aleatorio)--> " + e2.getScore());
		this.settingsService.generateAllScore();
		System.out.println("\n Score de customer1 posterior (tiene que ser positivo)--> " + e.getScore());
		System.out.println(" Score de customer2 posterior (tiene que ser negativo)--> " + e2.getScore());

	}

	@Test
	public void testGenerateAllScores2() {
		this.authenticate("admin1");
		this.settingsService.generateAllScore();
		final Endorsable e = this.endorsableService.findOne(this.getEntityId("customer1"));
		System.out.println("\n Score de customer1 inicial (antes de editar el comment)--> " + e.getScore());

		final Endorsement end = this.endorsementService.findOne(this.getEntityId("endorsement1"));
		String comment = end.getComments();
		comment = comment + "not not not not bad malo mala";
		end.setComments(comment);

		this.authenticate("customer1");
		this.endorsementService.save(end);

		this.authenticate("admin1");
		this.settingsService.generateAllScore();
		System.out.println(" Score de customer1 posterior (añadiendo badWords)--> " + e.getScore());
	}
}
