
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SponsorRepository;
import domain.Sponsor;

@Service
@Transactional
public class SponsorService {

	//Managed Repository

	@Autowired
	private SponsorRepository	sponsorRepository;


	// Supporting Service

	// Simple CRUD methods

	public Sponsor create() {
		final Sponsor s = new Sponsor();
		return s;
	}

	public Collection<Sponsor> findAll() {
		return this.sponsorRepository.findAll();
	}

	public Sponsor findOne(final int sponsorId) {
		return this.sponsorRepository.findOne(sponsorId);
	}

	public Sponsor save(final Sponsor s) {
		Assert.notNull(s);
		return this.sponsorRepository.save(s);
	}

	public void delete(final Sponsor s) {
		Assert.notNull(s);
		this.sponsorRepository.delete(s);
	}
}
