
package services;

import java.util.Collection;
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
		FixupTask ft;
		ft = new FixupTask();
		return ft;
	}

	public Collection<FixupTask> findAll() {
		Collection<FixupTask> ft;
		ft = this.fixupTaskRepository.findAll();
		return ft;
	}

	public FixupTask findOne(final int fixupTaskId) {
		FixupTask res;
		res = this.fixupTaskRepository.findOne(fixupTaskId);
		return res;
		
	}

	public FixupTask save(final FixupTask f) {
		Assert.notNull(f);
		FixupTask res;
		res= this.fixupTaskRepository.save(f);
		return res;
	}

	public void delete(final FixupTask f) {
		Assert.notNull(f);
		//Assert.isTrue(p.getId() != 0);
		this.fixupTaskRepository.delete(f);
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
	
//	public Map<String, Double> getRatioFixupTasksWithComplaints() {
//		final Double ratio = this.fixupTaskRepository.getRatioFixupTasksWithComplaints();
//		final Map<String, Double> res = new HashMap<>();
//
//		res.put("Ratio", ratio);
//
//		return res;
//	}
	
	public Map<String, Double> fixupComplaintsStats() {
		final Double[] statistics = this.fixupTaskRepository.fixupComplaintsStats();
		final Map<String, Double> res = new HashMap<>();
		res.put("MIN", statistics[0]);
		res.put("MAX", statistics[1]);
		res.put("AVG", statistics[2]);
		res.put("STD", statistics[3]);

		return res;

	}
	
	
	
	

}
