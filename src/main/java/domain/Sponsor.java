
package domain;

import java.util.Collection;

import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class Sponsor extends Actor {

	//----------Relaciones----------

	private Collection<Sponsorship>	sponsorships;


	//----------Getters y Setters------

	@Valid
	@NotNull
	@OneToMany(mappedBy = "sponsor")
	public Collection<Sponsorship> getSponsorships() {
		return this.sponsorships;
	}

	public void setSponsorships(final Collection<Sponsorship> sponsorships) {
		this.sponsorships = sponsorships;
	}

}
