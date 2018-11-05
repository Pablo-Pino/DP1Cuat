
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class HandyWorker extends Endorsable {

	//------------Atributos---------

	private String					make;

	//-------------Relaciones---------

	private Curriculum				curriculum;
	private Finder					finder;
	private Collection<Tutorial>	tutorials;
	private Collection<Note>		notes;
	private Collection<Application>	applications;
	private Collection<WorkPlan>	workPlans;


	//-------------Getters y Setters----

	@NotNull
	@Valid
	public Collection<Note> getNotes() {
		return this.notes;
	}

	public void setNotes(final Collection<Note> notes) {
		this.notes = notes;
	}

	@Override
	@Valid
	public Curriculum getCurriculum() {
		return this.curriculum;
	}

	@Override
	public void setCurriculum(final Curriculum curriculum) {
		this.curriculum = curriculum;
	}

	@NotBlank
	@NotNull
	public String getMake() {
		return this.make;
	}

	public void setMake(final String make) {
		this.make = make;
	}

	@NotNull
	@Valid
	public Finder getFinder() {
		return this.finder;
	}

	public void setFinder(final Finder finder) {
		this.finder = finder;
	}

	@NotNull
	@Valid
	public Collection<Tutorial> getTutorials() {
		return this.tutorials;
	}

	public void setTutorials(final Collection<Tutorial> tutorials) {
		this.tutorials = tutorials;
	}

	@NotNull
	@Valid
	public Collection<Application> getApplications() {
		return this.applications;
	}

	public void setApplications(final Collection<Application> applications) {
		this.applications = applications;
	}

	@NotNull
	@Valid
	public Collection<WorkPlan> getWorkPlans() {
		return this.workPlans;
	}

	public void setWorkPlans(final Collection<WorkPlan> workPlans) {
		this.workPlans = workPlans;
	}

}
