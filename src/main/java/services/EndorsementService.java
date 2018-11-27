
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


	// Constructors -----------------------------------------------------------
	public EndorsementService() {
		super();

	}

	// Simple CRUD methods ----------------------------------------------------

	public Endorsement create() {
		Endorsement res;
		res = new Endorsement();
		res.setMoment(new Date(System.currentTimeMillis() - 1000));
		//	res.setSender(this.)
		return res;
	}
	public Endorsement findOne(final int endorsableId) {
		this.serviceUtils.checkId(endorsableId);
		Endorsement res;
		res = this.endorsementRepository.findOne(endorsableId);
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

}
