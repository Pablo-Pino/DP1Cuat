
package services;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.RefereeRepository;
import security.Authority;
import security.UserAccount;
import domain.Complaint;
import domain.Folder;
import domain.Message;
import domain.Note;
import domain.Referee;
import domain.SocialProfile;
import domain.Url;

@Service
@Transactional
public class RefereeService extends GenericService<Referee, RefereeRepository> implements ServiceI<Referee> {

	// Repository
	
	@Autowired
	private RefereeRepository	repository;

	// Services
	
	@Autowired
	private ActorService actorService;
	@Autowired
	private FolderService		folderService;
	@Autowired
	private ServiceUtils		serviceUtils;

	// CRUD methods
	
	@Override
	public Referee create() {
		final Referee res = new Referee();
		res.setBanned(false);
		res.setSuspicious(false);
		res.setComplaints(new ArrayList<Complaint>());
		res.setFolders(new ArrayList<Folder>());
		res.setReceivedMessages(new ArrayList<Message>());
		res.setSendedMessages(new ArrayList<Message>());
		res.setSocialProfiles(new ArrayList<SocialProfile>());
		res.setUserAccount(new UserAccount()); //Create new account for a new referee
		return res;
	}

	@Override
	public Referee save(final Referee object) {
		this.serviceUtils.checkIdSave(object);
		Referee ref = object;
		if (object.getId() > 0)
			ref = this.repository.findOne(object.getId());
		if (object.getId() == 0) {
			object.setBanned(false);
			object.setComplaints(new ArrayList<Complaint>());
			object.setFolders(this.folderService.createSystemFolders(object));
			object.setReceivedMessages(new ArrayList<Message>());
			object.setSendedMessages(new ArrayList<Message>());
			object.setSocialProfiles(new ArrayList<SocialProfile>());
			object.setSuspicious(false);
			this.serviceUtils.checkAuthority(Authority.ADMIN);
		} else {
			object.setBanned(ref.getBanned());
			object.setComplaints(ref.getComplaints());
			object.setFolders(ref.getFolders());
			object.setReceivedMessages(ref.getReceivedMessages());
			object.setSendedMessages(ref.getSendedMessages());
			object.setSocialProfiles(ref.getSocialProfiles());
			object.setSuspicious(ref.getSuspicious());
			object.setUserAccount(ref.getUserAccount());
			this.serviceUtils.checkActor(ref);
			this.serviceUtils.checkAuthority(Authority.REFEREE);
		}
		final Referee res = this.repository.save(object);
		return res;
	}
	
	@Override
	public void delete(final Referee object) {
		throw new IllegalArgumentException("Unallowed method");
	}
	
	// Other methods
	public void changeBanned(final Referee referee) {
		this.serviceUtils.checkId(referee);
		Referee ref = referee;
		if (referee.getId() > 0)
			ref = this.repository.findOne(referee.getId());
		if (this.isSuspicious(ref))
			ref.setBanned(true);
		this.serviceUtils.checkAuthority(Authority.ADMIN);
		this.repository.save(ref);
	}

	public boolean isSuspicious(final Referee r) {
		boolean res = false;
		Assert.notNull(r);
		this.serviceUtils.checkId(r.getId());
		Referee referee = this.repository.findOne(r.getId());
		Assert.notNull(referee);
		res = this.actorService.isSuspicious(referee);
		if(!res) {
			for (Complaint c : referee.getComplaints()) {
				res = this.actorService.containsSpam(c.getDescription()) ||
						this.actorService.containsSpam(c.getReport().getDescription());
				if(!res) {
					for (Note n : c.getReport().getNotes()) {
						for (String comment : n.getComments()) {
							res = this.actorService.containsSpam(comment);
							if(res)
								break;
						}
						if(res) 
							break;
					}
				} if(!res) {
					for (Url u : c.getAttachments()) {
						res = this.actorService.containsSpam(u.getUrl());
						if(res)
							break;
					}
				} if(res)
					break;
			}
		}
		return res;
	}

	public void banActor(final Referee r) {
		Assert.notNull(r);
		this.serviceUtils.checkAuthority("ADMIN");
		r.setBanned(true);
		this.save(r);

	}

	public void unbanActor(final Referee r) {
		Assert.notNull(r);
		this.serviceUtils.checkAuthority("ADMIN");
		r.setBanned(false);
		this.save(r);

	}

	public void flush() {
		this.repository.flush();
	}

}
