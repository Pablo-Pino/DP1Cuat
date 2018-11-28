
package services;

import java.util.Arrays;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Settings;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SettingsServiceTest extends AbstractTest {

	// Services

	@Autowired
	private SettingsService	settingsService;


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

	public void createSettings(final String username, final Class<?> expected) {
		Class<?> caught = null;
		try {
			this.authenticate(username);
			final Settings settings = this.settingsService.create();
			Assert.notNull(settings);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	public void saveSettings(final String username, final String banner, final String countryCode, final Collection<String> creditCardMakes, final int finderCacheHours, final int maxCacheResults, final Collection<String> negativeWords,
		final Collection<String> positiveWords, final Collection<String> spamWords, final String systemName, final int vat, final String welcomeMessage, final Integer settingsId, final Class<?> expected) {
		Class<?> caught = null;
		try {
			this.authenticate(username);
			Settings settings = null;
			if (settingsId == null)
				settings = this.settingsService.create();
			else
				settings = this.settingsService.findOne(settingsId);
			settings.setBanner(banner);
			settings.setCountryCode(countryCode);
			settings.setCreditCardMakes(creditCardMakes);
			settings.setFinderCacheHours(finderCacheHours);
			settings.setMaxCacheResults(maxCacheResults);
			settings.setNegativeWords(negativeWords);
			settings.setPositiveWords(positiveWords);
			settings.setSpamWords(spamWords);
			settings.setSystemName(systemName);
			settings.setVat(vat);
			settings.setWelcomeMessage(welcomeMessage);
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
			Assert.isTrue(savedSettings.getWelcomeMessage().equals(welcomeMessage));
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	public void deleteSettings(final String username, final Integer settingsId, final Class<?> expected) {
		Class<?> caught = null;
		try {
			this.authenticate(username);
			final Settings settings = this.settingsService.findOne(settingsId);
			this.settingsService.delete(settings);
			this.settingsService.flush();
			Assert.isNull(this.settingsService.findOne(settingsId));
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
		Settings settings = settingsService.findOne(this.getEntityId("settings1"));
		this.saveSettings("admin1", "http://banner", "+34", settings.getCreditCardMakes(), 24, 10, settings.getNegativeWords(), settings.getPositiveWords(), settings.getSpamWords(), "Acme-Handy-Worker", 21, "Hola", null, IllegalArgumentException.class);
	}

	@Test
	public void testUpdateSettings() {
		Settings settings = settingsService.findOne(this.getEntityId("settings1"));
		this.saveSettings("admin1", "http://banner", "+34", settings.getCreditCardMakes(), 24, 10, settings.getNegativeWords(), settings.getPositiveWords(), settings.getSpamWords(), "Acme-Handy-Worker", 21, "Hola", this.getEntityId("settings1"), null);
	}

	@Test
	public void testUpdateSettingsUnauthenticated() {
		Settings settings = settingsService.findOne(this.getEntityId("settings1"));
		this.saveSettings(null, "http://banner", "+34", settings.getCreditCardMakes(), 24, 10, settings.getNegativeWords(), settings.getPositiveWords(), settings.getSpamWords(), "Acme-Handy-Worker", 21, "Hola", this.getEntityId("settings1"), IllegalArgumentException.class);
	}

	@Test
	public void testDeleteSettings() {
		this.deleteSettings("admin1", super.getEntityId("settings1"), IllegalArgumentException.class);
	}
	
}
