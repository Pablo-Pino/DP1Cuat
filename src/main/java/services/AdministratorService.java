package services;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.UserAccount;
import domain.Administrator;
import domain.Note;

@Service
@Transactional
public class AdministratorService {

	// Supporting Services

	@Autowired
	private UserAccountService userAccountService;

	// ------------------------------------------------------------
	@Autowired
	private AdministratorRepository administratorRepository;

	// --------------------------Constructor-----------------------

	public AdministratorService() {
		super();
	}

	// --------------------CRUD methods----------------------------

	public Administrator create() {
		final Administrator f = new Administrator();
		final UserAccount ua = this.userAccountService.create("ADMIN");
		f.setUserAccount(ua);
		return f;
	}

	public Administrator save(final Administrator administrator) {
		Assert.notNull(administrator);
		administrator.setUserAccount(this.userAccountService.save(administrator
				.getUserAccount()));
		return this.administratorRepository.save(administrator);
	}

	public Administrator findOne(final int administratorId) {
		return this.administratorRepository.findOne(administratorId);
	}
	

	public Collection<Administrator> findAll() {
		Collection<Administrator> a;

		Assert.notNull(this.administratorRepository);
		a = this.administratorRepository.findAll();
		Assert.notNull(a);

		return a;
	}



}

