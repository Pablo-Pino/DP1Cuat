
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
@Table(uniqueConstraints = {
	@UniqueConstraint(columnNames = {
		"name", "parentfolder_id"
	})
})
public class Folder extends DomainEntity {

	//------------Atributos---------

	private String				name;
	private boolean				system;

	//------------Relaciones----------

	private Collection<Message>	messages;
	private Collection<Folder>	childFolder;
	private Folder				parentFolder;
	private Actor				actor;


	//----------Getters y Setters-----

	@NotBlank
	@NotNull
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public boolean getSystem() {
		return this.system;
	}

	public void setSystem(final boolean system) {
		this.system = system;
	}

	@NotNull
	@Valid
	@ManyToMany
	public Collection<Message> getMessages() {
		return this.messages;
	}

	public void setMessages(final Collection<Message> messages) {
		this.messages = messages;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "parentFolder")
	public Collection<Folder> getChildFolder() {
		return this.childFolder;
	}

	public void setChildFolder(final Collection<Folder> childFolder) {
		this.childFolder = childFolder;
	}

	@Valid
	@ManyToOne(optional = false)
	public Folder getParentFolder() {
		return this.parentFolder;
	}

	public void setParentFolder(final Folder parentFolder) {
		this.parentFolder = parentFolder;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Actor getActor() {
		return this.actor;
	}

	public void setActor(final Actor actor) {
		this.actor = actor;
	}

}
