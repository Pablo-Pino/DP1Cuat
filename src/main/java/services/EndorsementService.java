
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.EndorsementRepository;
import domain.Endorsement;

@Service
@Transactional
public class EndorsementService {

	// Managed repository --------------------------------------
	@Autowired
	private EndorsementRepository	endorsementRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ServiceUtils			serviceUtils;

	@Autowired
	private EndorsableService		endorsableService;


	// Constructors -----------------------------------------------------------
	public EndorsementService() {
		super();

	}

	// Simple CRUD methods ----------------------------------------------------

	public Endorsement create() {
		Endorsement res;
		res = new Endorsement();
		res.setMoment(new Date(System.currentTimeMillis() - 1000));
		res.setSender(this.endorsableService.findPrincipal());
		return res;
	}
	public Endorsement findOne(final int endorsementId) {
		this.serviceUtils.checkId(endorsementId);
		Endorsement res;
		res = this.endorsementRepository.findOne(endorsementId);
		Assert.notNull(res);
		return res;
	}

	public Collection<Endorsement> findAll() {
		Collection<Endorsement> res;
		res = this.endorsementRepository.findAll();
		Assert.notNull(res);
		Assert.notEmpty(res);
		return res;
	}

	public Endorsement save(final Endorsement endorsement) {
		Assert.notNull(endorsement);

		this.serviceUtils.checkIdSave(endorsement);

		Endorsement endorsementBD;
		endorsementBD = this.endorsementRepository.findOne(endorsement.getId());

		if (endorsement.getId() == 0)
			this.serviceUtils.checkAnyAuthority("CUSTOMER", "HANDYWORKER");
		else {
			endorsement.setComments(endorsementBD.getComments());
			endorsement.setMoment(endorsementBD.getMoment());
			endorsement.setSender(endorsementBD.getSender());
			this.serviceUtils.checkAnyAuthority("CUSTOMER", "HANDYWORKER");

		}
		Endorsement res;
		res = this.endorsementRepository.save(endorsement);
		return res;
	}

}
