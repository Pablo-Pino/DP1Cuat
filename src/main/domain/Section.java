
package domain;

import java.util.Collection;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class Section extends DomainEntity {

	//------------Atributos------------

	private String				title;
	private String				text;
	private Collection<String>	pictures;
	private int					numberOrder;

	//------------Relaciones-----------

	private Tutorial			tutorial;


	//-----------Getters y Setters-------

	@NotNull
	@Valid
	public Tutorial getTutorial() {
		return this.tutorial;
	}

	public void setTutorial(final Tutorial tutorial) {
		this.tutorial = tutorial;
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

	@URL
	public Collection<String> getPictures() {
		return this.pictures;
	}

	public void setPictures(final Collection<String> pictures) {
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
