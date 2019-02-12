
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
/*
 * @Table(uniqueConstraints = {
 * 
 * @UniqueConstraint(columnNames = {
 * "name", "parentfolder_id"
 * })
 * })
 */
public class Folder extends DomainEntity {

	//------------Atributos---------

	private String	name;
	private boolean	cacharro;

	//------------Relaciones----------

	private Folder	parentFolder;
	private Actor	actor;


	//----------Getters y Setters-----

	@NotBlank
	@NotNull
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public boolean getCacharro() {
		return this.cacharro;
	}

	public void setCacharro(final boolean cacharro) {
		this.cacharro = cacharro;
	}

	@Valid
	@ManyToOne(optional = true)
	public Folder getParentFolder() {
		return this.parentFolder;
	}

	public void setParentFolder(final Folder parentFolder) {
		this.parentFolder = parentFolder;
	}

	@Valid
	@ManyToOne(optional = false)
	public Actor getActor() {
		return this.actor;
	}

	public void setActor(final Actor actor) {
		this.actor = actor;
	}

}
