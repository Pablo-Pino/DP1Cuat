
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

@Embeddable
@Access(AccessType.PROPERTY)
public class Settings extends DomainEntity {

	//-----------Atributos-----------

	private String				systemName;
	private String				banner;
	private String				welcomeMessage;
	private Collection<String>	spamWords;
	private int					vat;
	private String				countryCode;
	private Collection<String>	creditCardMakes;
	private int					finderCacheHours;
	private int					maxCacheResults;
	private Collection<String>	positiveWords;
	private Collection<String>	negativeWords;


	//--------Getters y Setters-------

	@NotBlank
	@NotNull
	public String getSystemName() {
		return this.systemName;
	}

	public void setSystemName(final String systemName) {
		this.systemName = systemName;
	}

	@NotBlank
	@URL
	@NotNull
	public String getBanner() {
		return this.banner;
	}

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	@NotBlank
	@NotNull
	public String getWelcomeMessage() {
		return this.welcomeMessage;
	}

	public void setWelcomeMessage(final String welcomeMessage) {
		this.welcomeMessage = welcomeMessage;
	}

	@NotBlank
	@NotNull
	public Collection<String> getSpamWords() {
		return this.spamWords;
	}

	public void setSpamWords(final Collection<String> spamWords) {
		this.spamWords = spamWords;
	}

	@Min(value = 0)
	public int getVat() {
		return this.vat;
	}

	public void setVat(final int vat) {
		this.vat = vat;
	}

	@NotBlank
	@Pattern(regexp = "\\+\\d{1,3}")
	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(final String countryCode) {
		this.countryCode = countryCode;
	}

	@NotBlank
	@NotNull
	public Collection<String> getCreditCardMakes() {
		return this.creditCardMakes;
	}

	public void setCreditCardMakes(final Collection<String> creditCardMakes) {
		this.creditCardMakes = creditCardMakes;
	}

	@Range(min = 1, max = 24)
	public int getFinderCacheHours() {
		return this.finderCacheHours;
	}

	public void setFinderCacheHours(final int finderCacheHours) {
		this.finderCacheHours = finderCacheHours;
	}

	@Range(min = 1, max = 100)
	public int getMaxCacheResults() {
		return this.maxCacheResults;
	}

	public void setMaxCacheResults(final int maxCacheResults) {
		this.maxCacheResults = maxCacheResults;
	}

	@NotNull
	public Collection<String> getPositiveWords() {
		return this.positiveWords;
	}

	public void setPositiveWords(final Collection<String> positiveWords) {
		this.positiveWords = positiveWords;
	}

	@NotNull
	public Collection<String> getNegativeWords() {
		return this.negativeWords;
	}

	public void setNegativeWords(final Collection<String> negativeWords) {
		this.negativeWords = negativeWords;
	}

}
