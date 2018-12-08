
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SponsorshipRepository;
import security.LoginService;
import domain.Sponsor;
import domain.Sponsorship;
import domain.Tutorial;

@Service
@Transactional
public class SponsorshipService {

	//Managed Repository

	@Autowired
	private SponsorshipRepository	sponsorshipRepository;

	// Supporting Service
	@Autowired
	private SponsorService			sponsorService;
	@Autowired
	private TutorialService			tutorialService;
	@Autowired
	private ServiceUtils			serviceUtils;


	// Simple CRUD methods

	public Sponsorship create() {
		Sponsorship s;
		s = new Sponsorship();
		s.setTutorials(new ArrayList<Tutorial>());
		return s;
	}

	public Collection<Sponsorship> findAll() {
		return this.sponsorshipRepository.findAll();
	}

	public Sponsorship findOne(final int sponsorshipId) {
		return this.sponsorshipRepository.findOne(sponsorshipId);
	}

	public Sponsorship save(final Sponsorship s) {
		Sponsor sp;
		if (s.getId() == 0) {
			sp = this.sponsorService.findSponsorById(LoginService.getPrincipal().getId());
			s.setSponsor(sp);
			sp.getSponsorships().add(s);
		}
		Assert.notNull(s);

		return this.sponsorshipRepository.save(s);
	}
	public void delete(final Sponsorship s) {
		Assert.notNull(s);
		this.serviceUtils.checkAuthority("SPONSOR");
		final Sponsor sp = s.getSponsor();
		sp.getSponsorships().remove(s);
		for (final Tutorial t : s.getTutorials()) {
			t.getSponsorships().remove(s);
			this.tutorialService.save(t);
		}
		this.sponsorshipRepository.delete(s);
	}
	
	public Collection<Sponsorship> findBySponsor(Sponsor sponsor) {
		Assert.notNull(sponsor);
		Assert.isTrue(sponsor.getId() > 0);
		Assert.notNull(this.sponsorshipRepository.findBySponsor(sponsor.getId()));
		return this.sponsorshipRepository.findBySponsor(sponsor.getId());
	}
	
	public Collection<Sponsorship> findByTutorial(Tutorial tutorial) {
		Assert.notNull(tutorial);
		Assert.isTrue(tutorial.getId() > 0);
		Assert.notNull(this.sponsorshipRepository.findByTutorial(tutorial.getId()));
		return this.sponsorshipRepository.findByTutorial(tutorial.getId());
	}
	
}
