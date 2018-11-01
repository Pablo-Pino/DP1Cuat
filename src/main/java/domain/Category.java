
package domain;

import java.util.Collection;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class Category extends DomainEntity {

	//--------Atributos-------------

	private String					name;
	

	//------------Relaciones-------------

	private Collection<FixupTask>	fixupTasks;
	private Collection<Category>	childsCategories;
	private Category				parentCategory;

	//--------Getters y Setters-------


	@Valid
	@NotNull
	public Collection<FixupTask> getFixupTasks() {
		return this.fixupTasks;
	}

	public void setFixupTasks(final Collection<FixupTask> fixupTasks) {
		this.fixupTasks = fixupTasks;
	}

	@NotBlank
	@NotNull
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}
	
	@Valid
	@NotNull
	public Collection<Category> getChildsCategories(){
		return this.childsCategories;
	}
	
	public void setChildsCategories(Collection<Category> childsCategories) {
		this.childsCategories = childsCategories;
	}
	
	@Valid
	@NotNull
	public Category getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(Category parentCategory) {
		this.parentCategory = parentCategory;
	}

	
	
	

}
