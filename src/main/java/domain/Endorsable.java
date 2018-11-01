
package domain;

import java.util.Collection;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public abstract class Endorsable extends Actor {

	// Relationships

	private Collection<Endorsement>	endorsements;


	//----------Getters y Setters

	@NotNull
	@Valid
	public Collection<Endorsement> getEndorsements() {
		return this.endorsements;
	}

	public void setEndorsements(final Collection<Endorsement> endorsements) {
		this.endorsements = endorsements;
	}

}
