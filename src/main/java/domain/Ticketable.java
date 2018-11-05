package domain;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

public abstract class Ticketable  extends DomainEntity {

	//----------------Atributos---------------
	
	private String	ticker;
	
	//--------getters y setters------------
	
	@NotBlank
	@Column(unique=true)
	@Pattern(regexp="^\\d{6}-([A-Z]|\\d){6}$") //comprobar patrón
	@NotNull
	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	
}
