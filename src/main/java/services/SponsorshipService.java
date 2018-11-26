
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SponsorshipRepository;
import domain.Sponsorship;

@Service
@Transactional
public class SponsorshipService {

	//Managed Repository

	@Autowired
	private SponsorshipRepository	sponsorshipRepository;

	// Supporting Service
	private SponsorService			sponsorService;


	// Simple CRUD methods

	public Sponsorship create() {
		final Sponsorship s = new Sponsorship();
		return s;
	}

	public Collection<Sponsorship> findAll() {
		return this.sponsorshipRepository.findAll();
	}

	public Sponsorship findOne(final int sponsorshipId) {
		return this.sponsorshipRepository.findOne(sponsorshipId);
	}

	public Sponsorship save(final Sponsorship s) {
		Assert.notNull(s);
		return this.sponsorshipRepository.save(s);
	}

	public void delete(final Sponsorship s) {
		Assert.notNull(s);
		this.sponsorshipRepository.delete(s);
	}
}
