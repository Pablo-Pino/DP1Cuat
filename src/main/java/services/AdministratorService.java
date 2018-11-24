
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.UserAccount;
import domain.Administrator;

@Service
@Transactional
public class AdministratorService {

	// Supporting Services

	// ------------------------------------------------------------
	@Autowired
	private AdministratorRepository	administratorRepository;


	// --------------------------Constructor-----------------------

	public AdministratorService() {
		super();
	}

	// --------------------CRUD methods----------------------------

	public Administrator create() {
		final Administrator f = new Administrator();
		f.setUserAccount(new UserAccount());
		return f;
	}

	public Administrator save(final Administrator administrator) {
		Assert.notNull(administrator);
		return this.administratorRepository.save(administrator);
	}

	public Administrator findOne(final int administratorId) {
		return this.administratorRepository.findOne(administratorId);
	}

	public Collection<Administrator> findAll() {
		Collection<Administrator> a;

		a = this.administratorRepository.findAll();
		Assert.notNull(a);

		return a;
	}

}
