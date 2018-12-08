
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class WorkPlan extends DomainEntity {

	//----------Relaciones--------

	private FixupTask			fixupTask;
	private HandyWorker			handyWorker;


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

}
