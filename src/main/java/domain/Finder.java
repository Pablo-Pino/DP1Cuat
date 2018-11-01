
package domain;

import java.util.Date;


import javax.validation.Valid;
import javax.validation.constraints.Min;

public class Finder extends DomainEntity {

	//-------------Atributos---------

	private String			keyword;
	private Double			minPrice;
	private Double			maxPrice;
	private Date			start;
	private Date			end;

	//--------------Relaciones--------

	
	private Warranty    warranty;
	private Category	category;


	//--------------Getters y Setters------

	@Valid
	public Category getCategory() {
		return this.category;
	}

	public void setCategory(final Category category) {
		this.category = category;
	}

	@Valid
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
