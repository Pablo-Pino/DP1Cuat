
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.SocialProfileRepository;
import domain.Actor;
import domain.SocialProfile;

@Service
@Transactional
public class SocialProfileService extends GenericService<SocialProfile, SocialProfileRepository> implements ServiceActorDependantI<SocialProfile> {

	@Autowired
	private SocialProfileRepository	repository;

	@Autowired
	private ActorService			actorService;


	@Override
	public Collection<SocialProfile> findAllByActor(final Actor a) {
		this.actorService.checkObject(a);
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
		super.checkPermisionActor(object.getActor(), null);
		final SocialProfile res = this.repository.save(object);
		return res;
	}

	@Override
	public void delete(final SocialProfile object) {
		final SocialProfile socialProfile = super.checkObject(object);
		super.checkPermisionActor(socialProfile.getActor(), null);
		this.repository.delete(socialProfile);
	}

}
