
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SponsorRepository;
import security.UserAccount;
import domain.Sponsor;

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
		e.setUserAccount(new UserAccount());
		e.setSuspicious(false);
		e.getUserAccount().setBanned(false);
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
			this.folderService.createSystemFolders(e);
			e.setSuspicious(false);
			e.setUserAccount(this.uAService.create("SPONSOR"));

		}
		return this.sponsorRepository.save(e);
	}

	public void delete(final Sponsor s) {
		Assert.notNull(s);
		s.getUserAccount().setBanned(true);
	}

	public void banActor(final Sponsor s) {
		Assert.notNull(s);
		this.serviceUtils.checkAuthority("ADMIN");
		s.getUserAccount().setBanned(true);
		this.sponsorRepository.save(s);
	}

	public void unbanActor(final Sponsor s) {
		Assert.notNull(s);
		this.serviceUtils.checkAuthority("ADMIN");
		s.getUserAccount().setBanned(false);
		this.sponsorRepository.save(s);
	}

	public Sponsor findSponsorById(final int id) {
		return this.sponsorRepository.findSponsorById(id);
	}

}
