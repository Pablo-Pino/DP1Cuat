
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.HandyWorkerRepository;
import domain.Application;
import domain.HandyWorker;
import domain.Tutorial;
import domain.WorkPlan;

@Service
@Transactional
public class HandyWorkerService {

	//Managed Repository

	@Autowired
	private HandyWorkerRepository	handyWorkerRepository;


	// Supporting Service

	public HandyWorkerService() {
		super();
	}

	// Simple CRUD methods

	public HandyWorker create() {
		final HandyWorker hw = new HandyWorker();
		hw.setApplications(new ArrayList<Application>());
		hw.setWorkPlans(new ArrayList<WorkPlan>());
		hw.setTutorials(new ArrayList<Tutorial>());
		return hw;
	}

	public Collection<HandyWorker> findAll() {
		return this.handyWorkerRepository.findAll();
	}

	public Collection<HandyWorker> findAll(final int handyWorkerId) {
		Collection<HandyWorker> hw;

		hw = this.handyWorkerRepository.findAll();
		Assert.notNull(hw);

		return hw;
	}

	public HandyWorker findOne(final int handyWorkerId) {
		Assert.notNull(this);
		return this.handyWorkerRepository.findOne(handyWorkerId);
	}

	public HandyWorker save(final HandyWorker hw) {
		Assert.notNull(hw);
		return this.handyWorkerRepository.save(hw);
	}

	public void delete(final HandyWorker hw) {
		Assert.notNull(hw);

		Assert.isTrue(hw.getId() != 0);
		this.handyWorkerRepository.delete(hw);
	}
}
