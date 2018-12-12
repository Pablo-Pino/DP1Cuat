
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
	public List<Folder> findAllByActor(final Actor actor) {
		Assert.notNull(actor);
		Assert.isTrue(actor.getId() > 0);
		Assert.notNull(this.actorService.findOne(actor.getId()));
		return this.repository.findFoldersByActor(actor.getId());
	}

	@Override
	public Folder create(final Actor a) {
		final Folder res = new Folder();
		res.setActor(a);
		res.setSystem(false);
		res.setParentFolder(res);
		return res;
	}

	@Override
	public Folder save(final Folder object) {
		final Folder folder = super.checkObjectSave(object);
		this.serviceUtils.checkActor(folder.getActor());
		Assert.isTrue(!folder.getSystem());
		return this.repository.save(object);
	}

	@Override
	public void delete(final Folder object) {
		final Folder folder = super.checkObject(object);
		this.serviceUtils.checkActor(folder.getActor());
		Assert.isTrue(!folder.getSystem());
		this.repository.delete(object);
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
	
}
