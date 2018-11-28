
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.ComplaintRepository;
import domain.Complaint;
import domain.Referee;

@Service
@Transactional
public class ComplaintService {

	//Managed Repository

	@Autowired
	private ComplaintRepository	complaintRepository;
	//Supporting Service
	public TicketableService	ticketableService;

	@Autowired
	private ServiceUtils		serviceUtils;


	// Simple CRUD methods
	//un complaint tiene un ticker
	public Complaint create() {
		final Complaint res = new Complaint();
		//res.setTicker(this.ticketableService.createTicker());
		res.setMoment(new Date(System.currentTimeMillis() - 1000));

		return res;
	}

	public Collection<Complaint> findAll() {
		return this.complaintRepository.findAll();
	}

	public Complaint findOne(final int complaintId) {
		return this.complaintRepository.findOne(complaintId);
	}

	//	public Complaint save(final Complaint c) {
	//		Assert.notNull(c);
	//		return this.complaintRepository.save(c);
	//	}

	//	public Complaint save1(final Complaint object) {
	//		final Phase phase = super.checkObjectSave(object);
	//		Assert.isTrue(object.getEnd().before(object.getWorkPlan().getFixupTask().getEnd()));
	//		if (object.getId() > 0)
	//			object.setWorkPlan(phase.getWorkPlan());
	//		super.checkPermisionActor(object.getWorkPlan().getHandyWorker(), new String[] {
	//			Authority.HANDYWORKER
	//		});
	//		final Phase res = this.repository.save(object);
	//		return res;
	//	}

	//----------------- Other business methods----------------------------------
	public Collection<Complaint> findAllComplaintsByReferee(final Referee r) {
		return this.complaintRepository.SearchComplaintByReferee(r.getId());
	}

	public Collection<Complaint> findAllComplaintsWithoutReferee() {
		return this.complaintRepository.SearchComplaintWithoutReferee();
	}
}
