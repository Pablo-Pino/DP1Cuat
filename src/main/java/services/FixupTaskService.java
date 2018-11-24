package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import domain.Application;
import domain.Complaint;
import domain.FixupTask;

import repositories.FixupTaskRepository;

@Service
@Transactional
public class FixupTaskService {
	
	//Managed Repository

	@Autowired
	private FixupTaskRepository fixupTaskRepository;

	// Supporting Service
	
	public FixupTaskService() {
		super();
	}
	// Simple CRUD methods
	
	public FixupTask create() {
		final FixupTask ft = new FixupTask();
		ft.setComplaints(new ArrayList<Complaint>());
		ft.setApplications(new ArrayList<Application>());
		ft.setMoment(new Date(System.currentTimeMillis() - 1000));

		return ft;
	}
	
	public Collection<FixupTask> findAll() {
		Collection<FixupTask> ft;

		Assert.notNull(this.fixupTaskRepository);
		ft = this.fixupTaskRepository.findAll();
		Assert.notNull(ft);

		return ft;
	}

	public FixupTask findOne(final int fixupTaskId) {
		return this.fixupTaskRepository.findOne(fixupTaskId);
	}
	
	public FixupTask save(final FixupTask ft) {
		Assert.notNull(ft);
		Date moment;
		moment = new Date();
		Assert.isTrue(ft.getMoment().after(moment));
		return this.fixupTaskRepository.save(ft);
	}

	public void delete(final FixupTask ft) {
		Assert.notNull(ft);
		Assert.isTrue(ft.getId() != 0);
		
		this.fixupTaskRepository.delete(ft);
	}
	
	//Other methods
	
	public Map<String, Double> appsStats() {
		final Double[] statistics = this.fixupTaskRepository.appsStats();
		final Map<String, Double> res = new HashMap<>();

		res.put("MIN", statistics[0]);
		res.put("MAX", statistics[1]);
		res.put("AVG", statistics[2]);
		res.put("STD", statistics[3]);
		

		return res;
	}
	
	public Map<String, Double> maxFixupStaskStats() {
		final Double[] statistics = this.fixupTaskRepository.maxFixupStaskStats();
		final Map<String, Double> res = new HashMap<>();

		res.put("MIN", statistics[0]);
		res.put("MAX", statistics[1]);
		res.put("AVG", statistics[2]);
		res.put("STD", statistics[3]);
		

		return res;
	}
	
	
}