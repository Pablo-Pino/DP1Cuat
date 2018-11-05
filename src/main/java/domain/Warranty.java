
package domain;

import java.util.Collection;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class Warranty extends DomainEntity {

	//------------Atributos---------

	private String			title;
	private String			terms;
	private String			laws;
	private Boolean			draft;

	//-----------Relaciones-------------

	private Collection<FixupTask>	fixupTasks;

	//-------------Getters y Setters-------

	@NotBlank
	@NotNull
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	@NotNull
	public String getTerms() {
		return this.terms;
	}

	public void setTerms(final String terms) {
		this.terms = terms;
	}

	@NotBlank
	@NotNull
	public String getLaws() {
		return this.laws;
	}

	public void setLaws(final String laws) {
		this.laws = laws;
	}

	@NotNull
	public Boolean getDraft() {
		return this.draft;
	}

	public void setDraft(final Boolean draft) {
		this.draft = draft;
	}

	@NotNull
	@Valid
	public Collection<FixupTask> getFixupTasks() {
		return this.fixupTasks;
	}

	public void setFixupTasks(final Collection<FixupTask> fixupTasks) {
		this.fixupTasks = fixupTasks;
	}

}
