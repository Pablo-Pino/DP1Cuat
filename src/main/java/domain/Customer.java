
package domain;

import java.util.Collection;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

public class Customer extends Endorsable {

	//---------Atributos-----------

	private double	score;


	//----------Relaciones---------
	private Collection<Note> 		notes;
	private Collection<Complaint> 	complaints;
	private Collection<FixupTask>	fixupTasks;
	
	
	
	//----------Getters y Setters------

	@Range(min = -1, max = 1)
	public double getScore() {
		return this.score;
	}

	public void setScore(final double score) {
		this.score = score;
	}

	@Valid
	@NotNull
	public Collection<Note> getNotes() {
		return notes;
	}

	public void setNotes(Collection<Note> notes) {
		this.notes = notes;
	}

	@Valid
	@NotNull
	public Collection<Complaint> getComplaints() {
		return complaints;
	}

	public void setComplaints(Collection<Complaint> complaints) {
		this.complaints = complaints;
	}

	@Valid
	@NotNull
	public Collection<FixupTask> getFixupTasks() {
		return fixupTasks;
	}

	public void setFixupTasks(Collection<FixupTask> fixupTasks) {
		this.fixupTasks = fixupTasks;
	}

}
