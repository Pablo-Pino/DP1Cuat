
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Customer extends Endorsable {

	//---------Atributos-----------

	//----------Relaciones---------

	private Collection<Note>		notes;
	private Collection<Complaint>	complaints;
	private Collection<FixupTask>	fixupTasks;


	//----------Getters y Setters------

	@Valid
	@NotNull
	@OneToMany(mappedBy = "actor")
	public Collection<Note> getNotes() {
		return this.notes;
	}

	public void setNotes(final Collection<Note> notes) {
		this.notes = notes;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "customer")
	public Collection<Complaint> getComplaints() {
		return this.complaints;
	}

	public void setComplaints(final Collection<Complaint> complaints) {
		this.complaints = complaints;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "customer")
	public Collection<FixupTask> getFixupTasks() {
		return this.fixupTasks;
	}

	public void setFixupTasks(final Collection<FixupTask> fixupTasks) {
		this.fixupTasks = fixupTasks;
	}

}
