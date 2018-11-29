
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SocialProfileRepository;
import domain.Actor;
import domain.SocialProfile;

@Service
@Transactional
public class SocialProfileService extends GenericService<SocialProfile, SocialProfileRepository> implements ServiceActorDependantI<SocialProfile> {

	// Repository
	
	@Autowired
	private SocialProfileRepository	repository;

	// Services
	
	@Autowired
	private ActorService			actorService;
	@Autowired
	private ServiceUtils serviceUtils;

	// CRUD methods
	
	@Override
	public Collection<SocialProfile> findAllByActor(final Actor a) {
		Assert.notNull(a);
		Assert.isTrue(a.getId() > 0);
		Assert.notNull(this.actorService.findOne(a.getId()));
		return this.repository.findByActor(a.getId());
	}

	@Override
	public SocialProfile create(final Actor a) {
		final SocialProfile res = new SocialProfile();
		res.setActor(a);
		return res;
	}

	@Override
	public SocialProfile save(final SocialProfile object) {
		final SocialProfile socialProfile = this.checkObjectSave(object);
		if (object.getId() == 0)
			object.setActor(this.actorService.findPrincipal());
		else
			object.setActor(socialProfile.getActor());
		this.serviceUtils.checkActor(socialProfile.getActor());
		final SocialProfile res = this.repository.save(object);
		return res;
	}

	@Override
	public void delete(final SocialProfile object) {
		final SocialProfile socialProfile = super.checkObject(object);
		this.serviceUtils.checkActor(socialProfile.getActor());
		this.repository.delete(socialProfile);
	}

	// Other methods
	
	public void flush() {
		this.repository.flush();
	}

}
