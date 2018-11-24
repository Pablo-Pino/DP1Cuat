
package services;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.RefereeRepository;
import security.Authority;
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


	@Override
	public Referee create() {
		final Referee res = new Referee();
		res.setBanned(false);
		res.setSuspicious(false);
		res.setComplaints(new ArrayList<Complaint>());
		res.setFolders(new ArrayList<Folder>());
		res.setReceivedMessages(new ArrayList<Message>());
		res.setSendedMessages(new ArrayList<Message>());
		return res;
	}

	@Override
	public Referee save(final Referee object) {
		final Referee referee = super.checkObjectSave(object);
		if (object.getId() == 0) {
			object.setBanned(false);
			object.setComplaints(new ArrayList<Complaint>());
			object.setFolders(this.folderService.createSystemFolders(object));
			object.setReceivedMessages(new ArrayList<Message>());
			object.setSendedMessages(new ArrayList<Message>());
			object.setSocialProfiles(new ArrayList<SocialProfile>());
			object.setSuspicious(false);
			super.checkPermisionActor(null, new String[] {
				Authority.ADMIN
			});
		} else {
			object.setBanned(referee.getBanned());
			object.setComplaints(referee.getComplaints());
			object.setFolders(referee.getFolders());
			object.setReceivedMessages(referee.getReceivedMessages());
			object.setSendedMessages(referee.getSendedMessages());
			object.setSocialProfiles(referee.getSocialProfiles());
			object.setSuspicious(referee.getSuspicious());
			object.setUserAccount(referee.getUserAccount());
			super.checkPermisionActor(referee, new String[] {
				Authority.REFEREE
			});
		}
		final Referee res = this.repository.save(object);
		return res;
	}

	public void changeBanned(final Referee referee) {
		final Referee ref = super.checkObject(referee);
		if (this.isSuspicious(ref))
			ref.setBanned(true);
		super.checkPermisionActor(null, new String[] {
			Authority.ADMIN
		});
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

}
