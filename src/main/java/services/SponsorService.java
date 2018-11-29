
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SponsorRepository;
import domain.Folder;
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
	@Autowired
	private FolderService		folderService;
	@Autowired
	private UserAccountService	uAService;
	@Autowired
	private ServiceUtils		serviceUtils;


	// Simple CRUD methods

	public Sponsor create() {
		Sponsor e;
		e = new Sponsor();
		e.setSuspicious(false);
		e.setBanned(false);
		e.setSocialProfiles(new ArrayList<SocialProfile>());
		e.setSendedMessages(new ArrayList<Message>());
		e.setReceivedMessages(new ArrayList<Message>());
		e.setFolders(new ArrayList<Folder>());
		e.setSponsorships(new ArrayList<Sponsorship>());
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
		if (e.getId() == 0) {
			e.setSocialProfiles(new ArrayList<SocialProfile>());
			e.setSendedMessages(new ArrayList<Message>());
			e.setReceivedMessages(new ArrayList<Message>());
			e.setFolders(this.folderService.createSystemFolders(e));
			e.setSuspicious(false);
			e.setSponsorships(new ArrayList<Sponsorship>());
			e.setUserAccount(this.uAService.create("SPONSOR"));

		}
		return this.sponsorRepository.save(e);
	}

	public void delete(final Sponsor s) {
		Assert.notNull(s);
		s.setBanned(true);
	}

	public void banActor(final Sponsor s) {
		Assert.notNull(s);
		this.serviceUtils.checkAuthority("ADMIN");
		s.setBanned(true);
		this.save(s);
	}

	public void unbanActor(final Sponsor s) {
		Assert.notNull(s);
		this.serviceUtils.checkAuthority("ADMIN");
		s.setBanned(false);
		this.save(s);
	}

	public Sponsor findSponsorById(final int id) {
		return this.sponsorRepository.findSponsorbyId(id);
	}

}
