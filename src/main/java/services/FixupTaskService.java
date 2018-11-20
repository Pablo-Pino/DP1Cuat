package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.FixupTask;

import repositories.FixupTaskRepository;

@Service
@Transactional
public class FixupTaskService {
	
	//Managed Repository

	@Autowired
	private FixupTaskRepository fixupTaskRepository;

	// Supporting Service
	
	
	// Simple CRUD methods
	
	public FixupTask create() {
		final FixupTask ft = new FixupTask();
		return ft;
	}
	
	public Collection<FixupTask> findAll() {
		return this.fixupTaskRepository.findAll();
	}

	public FixupTask findOne(final int fixupTaskId) {
		return this.fixupTaskRepository.findOne(fixupTaskId);
	}
	
	public FixupTask save(final FixupTask ft) {
		Assert.notNull(ft);
		return this.fixupTaskRepository.save(ft);
	}

	public void delete(final FixupTask ft) {
		Assert.notNull(ft);
		this.fixupTaskRepository.delete(ft);
	}
}