
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.RefereeRepository;
import security.Authority;
import security.UserAccount;
import domain.Complaint;
import domain.Note;
import domain.Referee;
import domain.Url;

@Service
@Transactional
public class RefereeService {

	// Repository
	
	@Autowired
	private RefereeRepository	repository;

	// Services
	
	@Autowired
	private ActorService actorService;
	@Autowired
	private FolderService		folderService;
	@Autowired
	private ComplaintService complaintService;
	@Autowired
	private NoteService noteService;
	@Autowired
	private ReportService reportService;
	@Autowired
	private ServiceUtils		serviceUtils;

	// CRUD methods
	
	public Referee findOne(Integer id) {
		this.serviceUtils.checkId(id);
		return this.repository.findOne(id);
	}

	public Collection<Referee> findAll(final Collection<Integer> ids) {
		this.serviceUtils.checkIds(ids);
		return this.repository.findAll(ids);
	}

	public Collection<Referee> findAll() {
		return this.repository.findAll();
	}
	
	public Referee create() {
		final Referee res = new Referee();
		res.setBanned(false);
		res.setSuspicious(false);
		res.setUserAccount(new UserAccount()); //Create new account for a new referee
		return res;
	}

	public Referee save(final Referee object) {
		Referee referee = (Referee) this.serviceUtils.checkObject(object);
		if (object.getId() == 0) {
			object.setBanned(false);
			this.folderService.createSystemFolders(object);
			object.setSuspicious(false);
			this.serviceUtils.checkAuthority(Authority.ADMIN);
		} else {
			referee.setAddress(object.getAddress());
			referee.setEmail(object.getEmail());
			referee.setMiddleName(object.getMiddleName());
			referee.setName(object.getName());
			referee.setPhone(object.getPhone());
			referee.setPhoto(object.getPhoto());
			referee.setSurname(object.getSurname());
			referee.setUserAccount(object.getUserAccount());
			this.serviceUtils.checkActor(referee);
			this.serviceUtils.checkAuthority(Authority.REFEREE);
		}
		final Referee res = this.repository.save(object);
		return res;
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
			for (Complaint c : this.complaintService.findAllComplaintsByReferee(referee)) {
				res = this.actorService.containsSpam(c.getDescription()) ||
						this.actorService.containsSpam(this.reportService.findByComplaint(c).getDescription());
				if(!res) {
					for (Note n : this.noteService.findAllByReport(this.reportService.findByComplaint(c))) {
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
