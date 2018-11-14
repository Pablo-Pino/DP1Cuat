
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public abstract class Endorsable extends Actor {

	// Properties

	private double					score;

	// Relationships

	private Collection<Endorsement>	sendedEndorsements;
	private Collection<Endorsement>	receivedEndorsements;


	//----------Getters y Setters

	@Range(min = -1, max = 1)
	public double getScore() {
		return this.score;
	}

	@NotNull
	@OneToMany(mappedBy = "sender")
	public Collection<Endorsement> getSendedEndorsements() {
		return this.sendedEndorsements;
	}

	public void setSendedEndorsements(final Collection<Endorsement> sendedEndorsements) {
		this.sendedEndorsements = sendedEndorsements;
	}

	@NotNull
	@OneToMany(mappedBy = "receiver")
	public Collection<Endorsement> getReceivedEndorsements() {
		return this.receivedEndorsements;
	}

	public void setReceivedEndorsements(final Collection<Endorsement> receivedEndorsements) {
		this.receivedEndorsements = receivedEndorsements;
	}

	public void setScore(final double score) {
		this.score = score;
	}
}
