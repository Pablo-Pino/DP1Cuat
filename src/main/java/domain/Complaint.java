
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Complaint extends Ticketable {

	//----------Atributos-----------

	private Date				moment;
	private String				description;
	private Collection<String>	attachments;

	//----------Relaciones---------

	private Report				report;
	private FixupTask			fixuptask;
	private Referee				referee;


	//----------Getters y Setters--------------------

	@Valid
	@OneToOne(optional = true)
	public Report getReport() {
		return this.report;
	}

	public void setReport(final Report report) {
		this.report = report;
	}

	@Valid
	@NotNull
	@OneToOne(optional = false)
	public FixupTask getFixuptask() {
		return this.fixuptask;
	}

	public void setFixuptask(final FixupTask fixuptask) {
		this.fixuptask = fixuptask;
	}

	@Past
	@NotNull
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotBlank
	@NotNull
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@NotNull
	@ElementCollection
	public Collection<String> getAttachments() {
		return this.attachments;
	}

	public void setAttachments(final Collection<String> attachments) {
		this.attachments = attachments;
	}

	@Valid
	@ManyToOne(optional = true)
	public Referee getReferee() {
		return this.referee;
	}

	public void setReferee(final Referee referee) {
		this.referee = referee;
	}

}
