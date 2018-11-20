package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import repositories.HandyWorkerRepository;
import domain.HandyWorker;

@Service
@Transactional
public class HandyWorkerService {
	
	//Managed Repository

	@Autowired
	private HandyWorkerRepository handyWorkerRepository;

	// Supporting Service
	
	
	// Simple CRUD methods
	
	public HandyWorker create() {
		final HandyWorker hw = new HandyWorker();
		return hw;
	}
	
	public Collection<HandyWorker> findAll() {
		return this.handyWorkerRepository.findAll();
	}

	public HandyWorker findOne(final int handyWorkerId) {
		return this.handyWorkerRepository.findOne(handyWorkerId);
	}
	
	public HandyWorker save(final HandyWorker hw) {
		Assert.notNull(hw);
		return this.handyWorkerRepository.save(hw);
	}

	public void delete(final HandyWorker hw) {
		Assert.notNull(hw);
		this.handyWorkerRepository.delete(hw);
	}
}