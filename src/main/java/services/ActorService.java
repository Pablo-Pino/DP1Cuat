
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;

@Service
@Transactional
public class ActorService {

	//--------------------Repositories--------------------------

	@Autowired
	private ActorRepository	actorRepository;

	//--------------------Services------------------------------

	@Autowired
	private FolderService	folderService;


	// ------------------CRUD methods-----------------------------

	public Actor findOne(final int actorId) {
		Assert.isTrue(actorId != 0);
		Actor result;
		result = this.actorRepository.findOne(actorId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Actor> findAll() {
		Collection<Actor> result;
		result = this.actorRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Actor save(final Actor actor) {
		Assert.notNull(actor);
		final Actor result = this.actorRepository.save(actor);
		return result;
	}

	public void delete(final Actor actor) {
		Assert.notNull(actor);
		Assert.isTrue(actor.getId() != 0);
		Assert.isTrue(this.actorRepository.exists(actor.getId()));

		this.actorRepository.delete(actor);
	}
	// -------------------------Other methods-------------------------

	public Actor findOneByUserAccount(final UserAccount userAccount) {
		return this.actorRepository.findOneByUserAccount(userAccount.getId());
	}

	public Actor findPrincipal() {
		final UserAccount userAccount = LoginService.getPrincipal();
		return this.actorRepository.findOneByUserAccount(userAccount.getId());
	}

}
