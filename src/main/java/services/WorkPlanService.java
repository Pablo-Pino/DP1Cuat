
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.WorkPlanRepository;
import domain.Phase;
import domain.WorkPlan;

@Service
@Transactional
public class WorkPlanService {

	//Managed Repository

	@Autowired
	private WorkPlanRepository	workPlanRepository;

	// Supporting Service
	private PhaseService		phaseService;


	// Simple CRUD methods

	public WorkPlan create() {
		final WorkPlan s = new WorkPlan();
		s.setPhases(new ArrayList<Phase>());
		return s;
	}

	public Collection<WorkPlan> findAll() {
		return this.workPlanRepository.findAll();
	}

	public WorkPlan findOne(final int workPlanId) {
		return this.workPlanRepository.findOne(workPlanId);
	}

	public WorkPlan save(final WorkPlan w) {
		Assert.notNull(w);
		return this.workPlanRepository.save(w);
	}

	public void delete(final WorkPlan w) {
		Assert.notNull(w);
		for (final Phase p : w.getPhases())
			this.phaseService.delete(p);
		this.workPlanRepository.delete(w);
	}
}
