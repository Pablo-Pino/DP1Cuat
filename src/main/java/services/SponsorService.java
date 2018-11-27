
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SponsorRepository;
import domain.Message;
import domain.SocialProfile;
import domain.Sponsor;
import domain.Sponsorship;

@Service
@Transactional
public class SponsorService {

	//Managed Repository

	@Autowired
	private SponsorRepository	sponsorRepository;

	// Supporting Service
	private FolderService		folderService;
	private UserAccountService	uAService;


	// Simple CRUD methods

	public Sponsor create() {
		final Sponsor e = new Sponsor();
		return e;
	}

	public Collection<Sponsor> findAll() {
		return this.sponsorRepository.findAll();
	}

	public Sponsor findOne(final int sponsorId) {
		return this.sponsorRepository.findOne(sponsorId);
	}

	public Sponsor save(final Sponsor e) {
		Assert.notNull(e);
		e.setSocialProfiles(new ArrayList<SocialProfile>());
		e.setSendedMessages(new ArrayList<Message>());
		e.setReceivedMessages(new ArrayList<Message>());
		this.folderService.createSystemFolders(e);
		e.setSponsorships(new ArrayList<Sponsorship>());
		e.setUserAccount(this.uAService.create("SPONSOR"));
		return this.sponsorRepository.save(e);
	}

	public void delete(final Sponsor s) {
		Assert.notNull(s);
		s.setBanned(true);
		this.sponsorRepository.delete(s);
	}
}
