
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.WarrantyRepository;
import domain.FixupTask;
import domain.Warranty;

@Service
@Transactional
public class WarrantyService {

	//Managed Repository

	@Autowired
	private WarrantyRepository	warrantyRepository;

	// Supporting Service
	@Autowired
	private PhaseService		phaseService;
	@Autowired
	private HandyWorkerService	handyWorkerService;
	@Autowired
	private FixupTaskService	fixupTaskService;


	public Warranty findOne(final Integer id) {
		return this.warrantyRepository.findOne(id);
	}

	public List<Warranty> findAll(final Collection<Integer> ids) {
		return this.warrantyRepository.findAll(ids);
	}

	public List<Warranty> findAll() {
		return this.warrantyRepository.findAll();
	}

	public Warranty create() {
		final Warranty res = new Warranty();
		res.setDraft(true);
		res.setFixupTasks(new ArrayList<FixupTask>());
		return res;
	}

	public Warranty save(final Warranty object) {
		final Warranty warranty = object;
		if (warranty.getId() == 0)
			warranty.setFixupTasks(new ArrayList<FixupTask>());
		Assert.isTrue(warranty.getDraft());
		if (object.getDraft() == true)
			Assert.isTrue(object.getFixupTasks().isEmpty());
		return this.warrantyRepository.save(object);
	}

	public void delete(final Warranty object) {
		final Warranty warranty = object;
		Assert.isTrue(warranty.getDraft());
		if (warranty.getFixupTasks().isEmpty())
			this.warrantyRepository.delete(object);
		else
			throw new IllegalArgumentException("No puedes borrar una garantía que tenga tareas");
	}

}
