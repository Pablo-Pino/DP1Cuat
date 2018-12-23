
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Access(AccessType.PROPERTY)
public class WorkPlan extends DomainEntity {

	//----------Relaciones--------

	private FixupTask			fixupTask;
	private HandyWorker			handyWorker;
	private Collection<Phase> phases; 


	//----------Getters y Setters-----

	@Valid
	@ManyToOne(optional = false)
	public HandyWorker getHandyWorker() {
		return this.handyWorker;
	}

	public void setHandyWorker(final HandyWorker handyWorker) {
		this.handyWorker = handyWorker;
	}

	@Valid
	@OneToOne(optional = false)
	public FixupTask getFixupTask() {
		return this.fixupTask;
	}

	public void setFixupTask(final FixupTask fixupTask) {
		this.fixupTask = fixupTask;
	}

	@Valid
	@OneToMany(mappedBy = "workPlan", cascade = CascadeType.ALL)
	@NotEmpty
	@NotNull
	public Collection<Phase> getPhases() {
		return phases;
	}

	public void setPhases(Collection<Phase> phases) {
		this.phases = phases;
	}

}
