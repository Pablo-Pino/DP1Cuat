
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.FolderRepository;
import domain.Actor;
import domain.Folder;
import domain.Message;

@Service
@Transactional
public class FolderService {
	
	// Repository
	
	@Autowired
	private FolderRepository	repository;
	
	// Services
	
	@Autowired
	private ActorService		actorService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private ServiceUtils		serviceUtils;

	// CRUD
	
	public Folder findOne(final Integer id) {
		this.serviceUtils.checkId(id);
		return this.repository.findOne(id);
	}

	public Collection<Folder> findAll(final Collection<Integer> ids) {
		this.serviceUtils.checkIds(ids);
		return this.repository.findAll(ids);
	}

	public Collection<Folder> findAll() {
		return this.repository.findAll();
	}
	
	public List<Folder> findAllByActor(final Actor actor) {
		Assert.notNull(actor);
		Assert.isTrue(actor.getId() > 0);
		Assert.notNull(this.actorService.findOne(actor.getId()));
		return this.repository.findFoldersByActor(actor.getId());
	}

	public Folder create(final Actor a) {
		final Folder res = new Folder();
		res.setActor(a);
		res.setSystem(false);
		res.setParentFolder(res);
		return res;
	}

	public Folder save(Folder object) {
		Folder folder = (Folder) this.serviceUtils.checkObjectSave(object);
		if(folder.getId() == 0) {
			if(folder.getParentFolder() == null)
				folder.setParentFolder(folder);
			folder.setActor(this.actorService.findPrincipal());
		} else {
			folder.setParentFolder(object.getParentFolder());
			folder.setName(object.getName());
		}
		this.serviceUtils.checkActor(folder.getActor());
		Assert.isTrue(!folder.getSystem());
		return this.repository.save(folder);
	}

	public void delete(final Folder object) {
		final Folder folder = (Folder) this.serviceUtils.checkObject(object);
		this.serviceUtils.checkActor(folder.getActor());
		Assert.isTrue(!folder.getSystem());
		for(Message m : this.messageService.findByFolder(folder)) {
			this.messageService.delete(m);
		}
		for(Folder f : this.findByActorAndParent(folder.getActor(), folder)) {
			this.delete(f);
		}
		folder.setParentFolder(null);
		this.save(folder);
		this.flush();
		this.repository.delete(folder);
	}

	public Folder findFolderByActorAndName(final Actor actor, final String name) {
		Assert.notNull(actor);
		Assert.isTrue(actor.getId() > 0);
		Assert.notNull(this.actorService.findOne(actor.getId()));
		return this.repository.findFolderByActorAndName(actor.getId(), name);
	}

	public List<Folder> createSystemFolders(final Actor actor) {
		final List<Folder> resFolders = new ArrayList<Folder>();
		Assert.notNull(actor);
		Assert.isTrue(actor.getId() >= 0);
		if (actor.getId() > 0)
			Assert.notNull(this.actorService.findOne(actor.getId()));
		final String[] names = new String[] {
			"inbox", "outbox", "spambox", "trashbox"
		};
		for (final String name : names) {
			final Folder newFolder = this.create(actor);
			newFolder.setName(name);
			newFolder.setSystem(true);
			newFolder.setParentFolder(newFolder);
			Folder newFolderSaved = this.save(newFolder);
			resFolders.add(newFolderSaved);
		}
		return resFolders;
	}
	
	public Collection<Folder> findByParent(Folder parent) {
		Assert.notNull(parent);
		Assert.isTrue(parent.getId() > 0);
		Assert.notNull(this.repository.findOne(parent.getId()));
		return this.repository.findByParentId(parent.getId());
	}
	
	public Collection<Folder> findByActorAndParent(Actor actor, Folder parent) {
		Assert.notNull(parent);
		Assert.isTrue(parent.getId() > 0);
		Assert.notNull(this.repository.findOne(parent.getId()));
		Assert.notNull(actor);
		Assert.isTrue(actor.getId() > 0);
		Assert.notNull(this.actorService.findOne(actor.getId()));
		return this.repository.findByActorIdAndParentId(actor.getId(), parent.getId());
	}
	
	public Collection<Folder> findByActorWithoutParent(Actor actor) {
		Assert.notNull(actor);
		Assert.isTrue(actor.getId() > 0);
		Assert.notNull(this.actorService.findOne(actor.getId()));
		return this.repository.findByActorIdWithoutParent(actor.getId());
	}
	
	public void flush() {
		this.repository.flush();
	}
	
}
