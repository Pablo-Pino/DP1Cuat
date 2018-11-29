
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.WorkPlanRepository;
import domain.Application;
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
		Assert.isTrue(this.checkStatusApplicationAccepted(w));
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

	public Boolean checkStatusApplicationAccepted(final WorkPlan w) {
		Boolean res = false;
		final int fixupTaskId = w.getFixupTask().getId();
		final Collection<Application> applications = w.getHandyWorker().getApplications();
		for (final Application a : applications)
			if (a.getFixupTask().getId() == fixupTaskId) {
				if (a.getStatus().equals("ACCEPTED"))
					res = true;
				break;
			}
		return res;
	}
}
