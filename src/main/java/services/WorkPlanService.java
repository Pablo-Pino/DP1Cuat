
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.WorkPlanRepository;
import domain.FixupTask;
import domain.HandyWorker;
import domain.Phase;
import domain.WorkPlan;

@Service
@Transactional
public class WorkPlanService {

	//Managed Repository

	@Autowired
	private WorkPlanRepository	workPlanRepository;

	// Supporting Service
	@Autowired
	private PhaseService		phaseService;
	@Autowired
	private HandyWorkerService	handyWorkerService;
	@Autowired
	private FixupTaskService	fixupTaskService;


	// Simple CRUD methods

	public WorkPlan create() {
		final WorkPlan s = new WorkPlan();
		s.setPhases(new ArrayList<Phase>());
		Assert.notNull(s);
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
		if (w.getId() == 0)
			w.setPhases(new ArrayList<Phase>());
		return this.workPlanRepository.save(w);
	}

	public void delete(final WorkPlan w) {
		Assert.notNull(w);
		final HandyWorker hw = w.getHandyWorker();
		hw.getWorkPlans().remove(w);
		this.handyWorkerService.save(hw);
		final FixupTask ft = w.getFixupTask();
		ft.setWorkPlan(null);
		this.fixupTaskService.save(ft);
		this.workPlanRepository.delete(w);
	}
}
