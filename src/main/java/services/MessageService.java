
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import security.LoginService;
import domain.Actor;
import domain.Folder;
import domain.Message;

@Service
@Transactional
public class MessageService {

	@Autowired
	private ActorService		actorService;
	@Autowired
	private MessageRepository	repository;
	@Autowired
	private FolderService		folderService;
	@Autowired
	private SettingsService		settingsService;
	@Autowired
	private ServiceUtils		serviceUtils;

	public Message findOne(Integer id) {
		this.serviceUtils.checkId(id);
		return this.repository.findOne(id);
	}
	
	public Collection<Message> findAll(final Collection<Integer> ids) {
		this.serviceUtils.checkIds(ids);
		return this.repository.findAll(ids);
	}

	public Collection<Message> findAll() {
		return this.repository.findAll();
	}
	
	public Collection<Message> findAll(final Folder dependency) {
		this.serviceUtils.checkObject(dependency);
		return this.findAll(dependency);
	}

	public Message create(final Folder dependency) {
		final Message res = new Message();
		res.setMoment(new Date(System.currentTimeMillis() - 1000));
		res.setFolder(new Folder());
		res.setSender(this.actorService.findPrincipal());
		res.setTags(new ArrayList<String>());
		return res;
	}

	public Message save(final Message object) {
		Message message = (Message) this.serviceUtils.checkObjectSave(object);
		if (message.getId() == 0) {
			message.setMoment(new Date(System.currentTimeMillis() - 1000));
			if(LoginService.getPrincipal().equals(message.getSender().getUserAccount())) {
				message.setFolder(this.folderService.findFolderByActorAndName(message.getSender(), "inbox"));
			} else if(LoginService.getPrincipal().equals(message.getReceiver().getUserAccount())) {
				if(this.containsSpam(message)) {
					message.setFolder(this.folderService.findFolderByActorAndName(message.getReceiver(), "spambox"));
				} else {
					message.setFolder(this.folderService.findFolderByActorAndName(message.getReceiver(), "outbox"));
				}
			}
		} else {
			message.setFolder(object.getFolder());
		}
		Assert.isTrue(message.getFolder().getActor().equals(this.actorService.findPrincipal()));
		this.serviceUtils.checkPermisionActors(new Actor[] {message.getSender(), message.getReceiver()}, null);
		return this.repository.save(message);
	}

	public void delete(final Message object) {
		Message message = (Message) this.serviceUtils.checkObject(object);
		final Actor principal = this.actorService.findPrincipal();
		final Folder trashboxPrincipal = this.folderService.findFolderByActorAndName(principal, "trashbox");
		if (message.getFolder().equals(trashboxPrincipal)) {
			Assert.isTrue(message.getFolder().getActor().equals(this.actorService.findPrincipal()));
			this.serviceUtils.checkPermisionActors(new Actor[] {message.getSender(), message.getReceiver()}, null);
			this.repository.delete(message);
		} else {
			message.setFolder(this.folderService.findFolderByActorAndName(object.getFolder().getActor(), "trashbox"));
			this.save(message);
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
		for (final String spamWord : this.settingsService.findSettings().getSpamWords())
			if (s.contains(spamWord)) {
				res = true;
				break;
			}
		return res;
	}
	
	//(Elena) Mensaje a todos los actores. Esta incompleto porque aun no se muy bien como hacerlo.

	public void sendMessageActors(final Message m) {
		final Message message = (Message) this.serviceUtils.checkObject(m);
		for (final Actor a : this.actorService.findAll())
			message.setReceiver(a);

	}
	
	public Collection<Message> findSendedMessages(Actor a) {
		Assert.notNull(a);
		Assert.isTrue(a.getId() > 0);
		Assert.notNull(this.actorService.findOne(a.getId()));
		return this.repository.findSendedMessages(a.getId());
	}
	
	public Collection<Message> findReceivedMessages(Actor a) {
		Assert.notNull(a);
		Assert.isTrue(a.getId() > 0);
		Assert.notNull(this.actorService.findOne(a.getId()));
		return this.repository.findReceivedMessages(a.getId());
	}
	
	public Collection<Message> findByFolder(Folder f) {
		Assert.notNull(f);
		Assert.isTrue(f.getId() > 0);
		Assert.notNull(this.actorService.findOne(f.getId()));
		return this.repository.findReceivedMessages(f.getId());
	}
	
}
