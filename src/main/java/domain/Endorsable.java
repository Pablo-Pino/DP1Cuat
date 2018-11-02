
package domain;

import java.util.Collection;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

public abstract class Endorsable extends Actor {

	// Properties

	private double					score;

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

	@Range(min = -1, max = 1)
	public double getScore() {
		return this.score;
	}

	public void setScore(final double score) {
		this.score = score;
	}
}
