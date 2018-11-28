
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.PhaseRepository;
import security.Authority;
import domain.Phase;
import domain.WorkPlan;

@Service
@Transactional
public class PhaseService extends GenericService<Phase, PhaseRepository> implements ServiceObjectDependantI<Phase, WorkPlan> {

	@Autowired
	private PhaseRepository	repository;

	@Autowired
	private WorkPlanService	workPlanService;
	@Autowired
	private ServiceUtils	serviceUtils;


	@Override
	public Collection<Phase> findAll(final WorkPlan dependency) {
		this.serviceUtils.checkId(dependency);
		Assert.notNull(this.workPlanService.findOne(dependency.getId()));
		return this.repository.findByWorkPlan(dependency.getId());
	}

	@Override
	public Phase create(final WorkPlan dependency) {
		final Phase res = new Phase();
		res.setWorkPlan(dependency);
		super.checkPermisionActor(res.getWorkPlan().getHandyWorker(), new String[] {
			Authority.HANDYWORKER
		});
		return res;
	}

	@Override
	public Phase save(final Phase object) {
		final Phase phase = super.checkObjectSave(object);
		Assert.isTrue(object.getEnd().before(object.getWorkPlan().getFixupTask().getEnd()));
		if (object.getId() > 0)
			object.setWorkPlan(phase.getWorkPlan());
		super.checkPermisionActor(object.getWorkPlan().getHandyWorker(), new String[] {
			Authority.HANDYWORKER
		});
		final Phase res = this.repository.save(object);
		return res;
	}

	@Override
	public void delete(final Phase object) {
		final Phase phase = super.checkObject(object);
		super.checkPermisionActor(phase.getWorkPlan().getHandyWorker(), new String[] {
			Authority.HANDYWORKER
		});
		this.repository.delete(phase);
	}

	public void flush() {
		this.repository.flush();
	}

}
