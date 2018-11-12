
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
/*
 * @Table(uniqueConstraints = {
 * 
 * @UniqueConstraint(columnNames = {
 * "name", "parentcategory_id"
 * })
 * })
 */
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
	@OneToMany(mappedBy = "category")
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
	@OneToMany(mappedBy = "parentCategory")
	public Collection<Category> getChildsCategories() {
		return this.childsCategories;
	}

	public void setChildsCategories(final Collection<Category> childsCategories) {
		this.childsCategories = childsCategories;
	}

	@Valid
	@ManyToOne(optional = false)
	public Category getParentCategory() {
		return this.parentCategory;
	}

	public void setParentCategory(final Category parentCategory) {
		this.parentCategory = parentCategory;
	}

}
