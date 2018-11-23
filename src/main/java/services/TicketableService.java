
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TicketableRepository;
import domain.Ticketable;

@Service
@Transactional
public class TicketableService {

	//Managed Repository

	@Autowired
	private TicketableRepository	ticketableRepository;


	// Supporting Service

	// Simple CRUD methods

	public Collection<Ticketable> findAll() {
		return this.ticketableRepository.findAll();
	}

	public Ticketable findOne(final int ticketableId) {
		return this.ticketableRepository.findOne(ticketableId);
	}

	public Ticketable save(final Ticketable s) {
		Assert.notNull(s);
		return this.ticketableRepository.save(s);
	}

	public void delete(final Ticketable s) {
		Assert.notNull(s);
		this.ticketableRepository.delete(s);
	}
}
