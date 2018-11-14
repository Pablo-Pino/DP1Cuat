
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Section extends DomainEntity {

	//------------Atributos------------

	private String					title;
	private String					text;
	private Collection<Url>			pictures;
	private int						numberOrder;

	//------------Relaciones-----------

	private Collection<Tutorial>	tutorials;


	//-----------Getters y Setters-------

	@NotNull
	@ManyToMany
	public Collection<Tutorial> getTutorials() {
		return this.tutorials;
	}

	public void setTutorials(final Collection<Tutorial> tutorials) {
		this.tutorials = tutorials;
	}

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
	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	@NotNull
	@ElementCollection
	public Collection<Url> getPictures() {
		return this.pictures;
	}

	public void setPictures(final Collection<Url> pictures) {
		this.pictures = pictures;
	}

	@Min(value = 0)
	public int getNumberOrder() {
		return this.numberOrder;
	}

	public void setNumberOrder(final int numberOrder) {
		this.numberOrder = numberOrder;
	}

}
