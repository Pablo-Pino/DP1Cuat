
package domain;

import java.util.Collection;
import java.util.Date;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;


public class Complaint extends Ticketable {

	//----------Atributos-----------

	private Date					moment;
	private String					description;
	private Collection<String>		attachments;

	//----------Relaciones---------

	private Report	report;
	private FixupTask	fixuptask;
	private Referee referee;


	//----------Getters y Setters--------------------

	@Valid
	public Report getReport() {
		return this.report;
	}

	public void setReport(Report report) {
		this.report = report;
	}

	@Valid
	@NotNull
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
	@URL
	public Collection<String> getAttachments() {
		return this.attachments;
	}

	public void setAttachments(final Collection<String> attachments) {
		this.attachments = attachments;
	}

	@Valid
	public Referee getReferee() {
		return referee;
	}

	public void setReferee(Referee referee) {
		this.referee = referee;
	}

}
