
package domain;

import java.util.Collection;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class WorkPlan {

	//----------Relaciones--------

	private Collection<Phase>	phases;
	private FixupTask			fixupTask;


	//----------Getters y Setters-----

	@NotNull
	@NotEmpty
	@Valid
	public Collection<Phase> getPhases() {
		return this.phases;
	}

	public void setPhases(final Collection<Phase> phases) {
		this.phases = phases;
	}

	public FixupTask getFixupTask() {
		return this.fixupTask;
	}

	public void setFixupTask(final FixupTask fixupTask) {
		this.fixupTask = fixupTask;
	}

}
