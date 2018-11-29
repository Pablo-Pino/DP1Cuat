
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
import domain.Referee;
import domain.SocialProfile;

@Service
@Transactional
public class RefereeService extends GenericService<Referee, RefereeRepository> implements ServiceI<Referee> {

	@Autowired
	private RefereeRepository	repository;

	@Autowired
	private FolderService		folderService;
	@Autowired
	private ServiceUtils		serviceUtils;


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

	public Boolean isSuspicious(final Referee referee) {
		// TODO implementar esto
		return null;
	}

	@Override
	public void delete(final Referee object) {
		throw new IllegalArgumentException("Unallowed method");
	}

	public void banActor(final Referee r) {
		Assert.notNull(r);
		this.serviceUtils.checkAuthority("ADMIN");
		r.setBanned(true);
		this.repository.save(r);

	}

	public void unbanActor(final Referee r) {
		Assert.notNull(r);
		this.serviceUtils.checkAuthority("ADMIN");
		r.setBanned(false);
		this.repository.save(r);

	}

	public void flush() {
		this.repository.flush();
	}

}
