
package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import domain.Actor;
import domain.Folder;
import domain.Message;

@Service
@Transactional
public class MessageService extends GenericService<Message, MessageRepository> implements ServiceObjectDependantI<Message, Folder> {

	@Autowired
	private ActorService		actorService;
	@Autowired
	private MessageRepository	repository;
	@Autowired
	private FolderService		folderService;
	@Autowired
	private SettingsService		settingsService;


	@Override
	public List<Message> findAll(final Folder dependency) {
		return super.findAll();
	}

	@Override
	public Message create(final Folder dependency) {
		final Message res = new Message();
		res.setMoment(new Date(System.currentTimeMillis() - 1000));
		res.setFolders(new ArrayList<Folder>());
		res.setSender(this.actorService.findPrincipal());
		res.setTags(new ArrayList<String>());
		return res;
	}

	@Override
	public Message save(final Message object) {
		final Message message = super.checkObjectSave(object);
		super.checkPermisionActors(new Actor[] {
			message.getReceiver(), message.getSender()
		}, null);
		if (message.getId() == 0) {
			object.setMoment(new Date(System.currentTimeMillis() - 1000));
			if (this.containsSpam(object)) {
				final Folder spamReceiver = this.folderService.findFolderByActorAndName(object.getReceiver(), "spambox");
				message.getFolders().add(spamReceiver);
			} else {
				final Folder inReceiver = this.folderService.findFolderByActorAndName(object.getReceiver(), "inbox");
				message.getFolders().add(inReceiver);
			}
			final Folder outSender = this.folderService.findFolderByActorAndName(object.getSender(), "outbox");
			message.getFolders().add(outSender);
		} else if (message.getId() > 0) {
			object.setMoment(message.getMoment());
			object.setReceiver(message.getReceiver());
			object.setSender(message.getSender());
		}
		return this.repository.save(object);
	}

	@Override
	public void delete(final Message object) {
		final Message message = super.checkObject(object);
		super.checkPermisionActors(new Actor[] {
			message.getReceiver(), message.getSender()
		}, null);
		final Actor principal = this.actorService.findPrincipal();
		final Folder trashboxPrincipal = this.folderService.findFolderByActorAndName(principal, "trashbox");
		if (message.getFolders().contains(trashboxPrincipal))
			this.repository.delete(message);
		else {

		}
	}

	public boolean containsSpam(final Message message) {
		final Boolean spam1 = this.containsSpam(message.getBody()) && this.containsSpam(message.getSubject());
		Boolean spam2 = false;
		for (final String tag : message.getTags())
			if (this.containsSpam(tag)) {
				spam2 = true;
				break;
			}
		return spam1 && spam2;
	}

	public boolean containsSpam(final String s) {
		Boolean res = false;
		for (final String spamWord : this.settingsService.getSettings().getSpamWords())
			if (s.contains(spamWord)) {
				res = true;
				break;
			}
		return res;
	}

	public void moveMessage(final Message m, final Folder f) {
		final Message message = this.checkObject(m);
		final Folder folder = this.folderService.checkObject(f);
		Folder oldFolder = null;
		for (final Folder forFolder : message.getFolders())
			if (forFolder.getActor().equals(folder.getActor())) {
				oldFolder = forFolder;
				break;
			}
		Assert.notNull(oldFolder);
		this.checkPermisionActors(new Actor[] {
			message.getSender(), message.getReceiver()
		}, null);
		this.folderService.checkPermisionActor(folder.getActor(), null);
		message.getFolders().remove(oldFolder);
		message.getFolders().add(folder);
		this.repository.save(message);
	}

	//(Elena) Mensaje a todos los actores. Esta incompleto porque aun no se muy bien como hacerlo.

	public void sendMessageActors(final Message m) {
		final Message message = this.checkObject(m);
		for (final Actor a : this.actorService.findAll())
			message.setReceiver(a);

	}
}
