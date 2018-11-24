
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RefereeRepository;
import domain.Referee;

@Service
@Transactional
public class RefereeService {

	//Managed Repository

	@Autowired
	private RefereeRepository	refereeRepository;

	// Supporting Service
	private ComplaintService	complaintService;
	private ComplaintRepository	complaintRepository;


	// Simple CRUD methods

	public Referee create() {
		final Referee s = new Referee();
		return s;
	}

	public Collection<Referee> findAll() {
		return this.refereeRepository.findAll();
	}

	public Referee findOne(final int refereeId) {
		return this.refereeRepository.findOne(refereeId);
	}

	public Referee save(final Referee s) {
		Assert.notNull(s);
		return this.refereeRepository.save(s);
	}

	public void delete(final Referee s) {
		Assert.notNull(s);
		this.refereeRepository.delete(s);
	}
}
