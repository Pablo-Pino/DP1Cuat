package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import domain.Application;


@Service
@Transactional
public class ApplicationService {
	
	//Managed Repository

	@Autowired
	private ApplicationRepository applicationRepository;

	// Supporting Service
	
	
	// Simple CRUD methods
	
	public Application create() {
		final Application a = new Application();
		return a;
	}
	
	public Collection<Application> findAll() {
		return this.applicationRepository.findAll();
	}

	public Application findOne(final int applicationId) {
		return this.applicationRepository.findOne(applicationId);
	}
	
	public Application save(final Application a) {
		Assert.notNull(a);
		return this.applicationRepository.save(a);
	}

	public void delete(final Application a) {
		Assert.notNull(a);
		this.applicationRepository.delete(a);
	}
}
