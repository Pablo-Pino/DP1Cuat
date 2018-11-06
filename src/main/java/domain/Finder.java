
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity {

	//-------------Atributos---------

	private String		keyword;
	private Double		minPrice;
	private Double		maxPrice;
	private Date		start;
	private Date		end;
	private Date 		minimumDate;
	private Date		maximumDate;

	//--------------Relaciones--------

	private Warranty	warranty;
	private Category	category;


	//--------------Getters y Setters------
	
	

	@Valid
	@ManyToOne(optional = true)
	public Category getCategory() {
		return this.category;
	}

	public Date getMinimumDate() {
		return minimumDate;
	}

	public void setMinimumDate(Date minimumDate) {
		this.minimumDate = minimumDate;
	}

	public Date getMaximumDate() {
		return maximumDate;
	}

	public void setMaximumDate(Date maximumDate) {
		this.maximumDate = maximumDate;
	}

	public void setCategory(final Category category) {
		this.category = category;
	}

	@Valid
	@ManyToOne(optional = true)
	public Warranty getWarranty() {
		return this.warranty;
	}

	public void setWarranty(final Warranty warranty) {
		this.warranty = warranty;
	}

	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(final String keyword) {
		this.keyword = keyword;
	}

	@Min(value = 0)
	public Double getMinPrice() {
		return this.minPrice;
	}

	public void setMinPrice(final Double minPrice) {
		this.minPrice = minPrice;
	}

	@Min(value = 0)
	public Double getMaxPrice() {
		return this.maxPrice;
	}

	public void setMaxPrice(final Double maxPrice) {
		this.maxPrice = maxPrice;
	}

	public Date getStart() {
		return this.start;
	}

	public void setStart(final Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return this.end;
	}

	public void setEnd(final Date end) {
		this.end = end;
	}

}
