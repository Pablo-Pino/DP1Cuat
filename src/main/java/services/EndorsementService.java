
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.EndorsementRepository;
import security.Authority;
import domain.Endorsable;
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
		final Endorsable sender = this.endorsableService.findPrincipal();
		Assert.notNull(sender);
		res.setSender(sender);
		return res;
	}
	public Endorsement findOne(final int endorsementId) {
		this.serviceUtils.checkId(endorsementId);
		Endorsement res;
		res = this.endorsementRepository.findOne(endorsementId);
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

		if (endorsement.getId() == 0) {
			final Boolean res = (this.serviceUtils.checkAuthorityBoolean(Authority.CUSTOMER) || this.serviceUtils.checkAuthorityBoolean(Authority.HANDYWORKER));
			Assert.isTrue(res);
		} else {
			endorsement.setComments(endorsementBD.getComments());
			endorsement.setMoment(endorsementBD.getMoment());
			endorsement.setSender(endorsementBD.getSender());
			final Boolean res = (this.serviceUtils.checkAuthorityBoolean(Authority.CUSTOMER) || this.serviceUtils.checkAuthorityBoolean(Authority.HANDYWORKER));
			Assert.isTrue(res);
		}
		Endorsement res;
		res = this.endorsementRepository.save(endorsement);
		return res;
	}
	public void delete(final Endorsement endorsement) {
		Assert.notNull(endorsement);
		this.endorsementRepository.delete(endorsement);
	}

}
