
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.WarrantyRepository;
import security.Authority;
import domain.FixupTask;
import domain.Warranty;

@Service
@Transactional
public class WarrantyService extends GenericService<Warranty, WarrantyRepository> implements ServiceI<Warranty> {

	@Override
	public Warranty findOne(final Integer id) {
		return super.findOne(id);
	}

	@Override
	public List<Warranty> findAll(final Collection<Integer> ids) {
		return super.findAll(ids);
	}

	@Override
	public List<Warranty> findAll() {
		return super.findAll();
	}

	@Override
	public Warranty create() {
		final Warranty res = new Warranty();
		res.setDraft(true);
		res.setFixupTasks(new ArrayList<FixupTask>());
		return res;
	}

	@Override
	public Warranty save(final Warranty object) {
		final Warranty warranty = super.checkObjectSave(object);
		super.checkPermisionActor(null, new String[] {
			Authority.ADMIN
		});
		Assert.isTrue(warranty.getDraft());
		if (object.getDraft() == true)
			Assert.isTrue(object.getFixupTasks().isEmpty());
		return this.save(object);
	}

	@Override
	public void delete(final Warranty object) {
		final Warranty warranty = super.checkObject(object);
		super.checkPermisionActor(null, new String[] {
			Authority.ADMIN
		});
		Assert.isTrue(warranty.getDraft());
		this.delete(object);
	}

}
