
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class FixupTask extends Ticketable {

	//-------------Atributos----------

	private Date					moment;
	private String					description;
	private String					address;
	private double					maximumPrice;
	private Date					start;
	private Date					end;

	//------------Relaciones----------

	private Collection<Complaint>	complaints;
	private Customer				customer;
	private Category				category;
	private Warranty				warranty;
	private Collection<Application>	applications;
	private WorkPlan				workPlan;


	//------------Getters y Setters-------
	@Valid
	@ManyToOne(optional = false)
	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(final Customer customer) {
		this.customer = customer;
	}

	@Valid
	@ManyToOne(optional = false)
	public Warranty getWarranty() {
		return this.warranty;
	}

	public void setWarranty(final Warranty warranty) {
		this.warranty = warranty;
	}

	@NotNull
	@OneToMany(mappedBy = "fixuptask")
	public Collection<Complaint> getComplaints() {
		return this.complaints;
	}

	public void setComplaints(final Collection<Complaint> complaints) {
		this.complaints = complaints;
	}

	@Valid
	@ManyToOne(optional = false)
	public Category getCategory() {
		return this.category;
	}

	public void setCategory(final Category category) {
		this.category = category;
	}

	@Past
	@NotNull
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@NotBlank
	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	@Digits(fraction = 2, integer = 100)
	@Min(value = 0)
	public double getMaximumPrice() {
		return this.maximumPrice;
	}

	public void setMaximumPrice(final double maximumPrice) {
		this.maximumPrice = maximumPrice;
	}

	@NotNull
	public Date getStart() {
		return this.start;
	}

	public void setStart(final Date start) {
		this.start = start;
	}

	@NotNull
	public Date getEnd() {
		return this.end;
	}

	public void setEnd(final Date end) {
		this.end = end;
	}

	@NotNull
	@OneToMany(mappedBy = "fixupTask")
	public Collection<Application> getApplications() {
		return this.applications;
	}

	public void setApplications(final Collection<Application> applications) {
		this.applications = applications;
	}

	@Valid
	@OneToOne(optional = true)
	public WorkPlan getWorkPlan() {
		return this.workPlan;
	}

	public void setWorkPlan(final WorkPlan workPlan) {
		this.workPlan = workPlan;
	}

}
