
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FixupTaskRepository;
import domain.FixupTask;

@Service
@Transactional
public class FixupTaskService {

	//Managed Repository

	@Autowired
	private FixupTaskRepository	fixupTaskRepository;


	// Supporting Service

	public FixupTaskService() {
		super();
	}
	// Simple CRUD methods

	public FixupTask create() {
		final FixupTask ft = new FixupTask();
		return ft;
	}

	public Collection<FixupTask> findAll() {
		Collection<FixupTask> ft;

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
	
	public Map<String, Double> getRatioFixupTasksWithComplaints() {
		final Double ratio = this.fixupTaskRepository.getRatioFixupTasksWithComplaints();
		final Map<String, Double> res = new HashMap<>();

		res.put("Ratio", ratio);

		return res;
	}
	
	
	
	

}
