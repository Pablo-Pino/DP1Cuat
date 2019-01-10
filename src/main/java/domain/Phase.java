
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Phase extends DomainEntity {

	//----------Atributos-----------

	private String		title;
	private String		description;
	private Date		start;
	private Date		end;

	//------------Relaciones-----------

	private WorkPlan	workPlan;


	//---------Getters y Setters-----

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
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getStart() {
		return this.start;
	}

	public void setStart(final Date start) {
		this.start = start;
	}

	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getEnd() {
		return this.end;
	}

	public void setEnd(final Date end) {
		this.end = end;
	}

	@Valid
	@ManyToOne(optional = false)
	public WorkPlan getWorkPlan() {
		return this.workPlan;
	}

	public void setWorkPlan(final WorkPlan workPlan) {
		this.workPlan = workPlan;
	}

}
