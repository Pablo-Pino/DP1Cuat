
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import domain.Actor;
import domain.Finder;
import domain.HandyWorker;

@Transactional
@Service
public class FinderService extends GenericService<Finder, FinderRepository> implements ServiceActorDependantI<Finder> {

	//---------------Managed Repository---------------------------------------
	@Autowired
	private FinderRepository	finderRepository;


	//---------------Supporting Services----------------------------------------

	@Autowired
	private HandyWorkerService handyWorkerService;
	
	// Constructors -----------------------------------------------------------

	public FinderService() {
		super();
	}

	//------------------Simple CRUD methods----------------------------------
	public Finder create() {
		Finder res;
		res = new Finder();
		Assert.notNull(res);
		return res;
	}

	public Finder findOne(final int finderId) {
		Finder res;
		res = this.finderRepository.findOne(finderId);
		return res;
	}
	public Collection<Finder> findAll() {
		Collection<Finder> res;
		res = this.finderRepository.findAll();
		return res;
	}
	public Finder save(final Finder finder) {
		Assert.notNull(finder);
		Finder res;
		res = this.finderRepository.save(finder);
		return res;
	}

	public void delete(final Finder finder) {
		Assert.notNull(finder);
		this.finderRepository.delete(finder);
	}

	public Finder findByHandyWorker(HandyWorker h) {
		Assert.notNull(h);
		Assert.isTrue(h.getId() > 0);
		Assert.notNull(this.handyWorkerService.findOne(h.getId()));
		return this.finderRepository.findByHandyWorkerId(h.getId());
	}

	@Override
	public Collection<Finder> findAllByActor(Actor a) {
		Assert.notNull(a);
		Assert.isTrue();
	}

	@Override
	public Finder create(Actor a) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
