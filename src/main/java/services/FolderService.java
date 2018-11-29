
package services;

import java.util.ArrayList;
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
public class FolderService extends GenericService<Folder, FolderRepository> implements ServiceActorDependantI<Folder> {

	@Autowired
	private FolderRepository	repository;
	@Autowired
	private ActorService		actorService;
	@Autowired
	private ServiceUtils		serviceUtils;


	@Override
	public List<Folder> findAllByActor(final Actor a) {
		Assert.notNull(this.actorService.findOne(a.getId()));
		return this.repository.findFoldersByActor(a.getId());
	}

	@Override
	public Folder create(final Actor a) {
		final Folder res = new Folder();
		res.setActor(a);
		res.setChildFolder(new ArrayList<Folder>());
		res.setSystem(false);
		res.setMessages(new ArrayList<Message>());
		res.setParentFolder(res);
		return res;
	}

	@Override
	public Folder save(final Folder object) {
		final Folder folder = super.checkObjectSave(object);
		//this.serviceUtils.checkActor(folder.getActor());
		Assert.isTrue(!folder.getSystem());
		object.setActor(folder.getActor());
		return this.repository.save(object);
	}

	@Override
	public void delete(final Folder object) {
		final Folder folder = super.checkObject(object);
		this.serviceUtils.checkActor(folder.getActor());
		Assert.isTrue(!folder.getSystem());
		this.repository.delete(object);
	}

	public List<Folder> findFoldersByActor(final Actor actor) {
		Assert.notNull(actor);
		Assert.isTrue(actor.getId() > 0);
		Assert.notNull(this.actorService.findOne(actor.getId()));
		return this.repository.findFoldersByActor(actor.getId());
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
			resFolders.add(newFolder);
		}
		return resFolders;
	}
}
