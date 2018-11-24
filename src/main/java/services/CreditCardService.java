package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CreditCardRepository;
import domain.CreditCard;

@Service
@Transactional
public class CreditCardService {
	
	//Managed Repository

	@Autowired
	private CreditCardRepository creditCardRepository;

	// Supporting Service
	
	
	// Simple CRUD methods
	
	public CreditCard create() {
		final CreditCard cc = new CreditCard();
		return cc;
	}
	
	public Collection<CreditCard> findAll() {
		return this.creditCardRepository.findAll();
	}

	public CreditCard findOne(final int creditCardId) {
		return this.creditCardRepository.findOne(creditCardId);
	}
	
	public CreditCard save(final CreditCard cc) {
		Assert.notNull(cc);
		return this.creditCardRepository.save(cc);
	}

	public void delete(final CreditCard cc) {
		Assert.notNull(cc);
		this.creditCardRepository.delete(cc);
	}
}