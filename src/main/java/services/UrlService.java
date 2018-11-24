
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.UrlRepository;
import domain.Url;

@Service
@Transactional
public class UrlService {

	//Managed Repository

	@Autowired
	private UrlRepository	urlRepository;


	// Supporting Service

	// Simple CRUD methods

	public Url create() {
		final Url s = new Url();
		return s;
	}

	public Collection<Url> findAll() {
		return this.urlRepository.findAll();
	}

	public Url findOne(final int UrlId) {
		return this.urlRepository.findOne(UrlId);
	}

	public Url save(final Url s) {
		Assert.notNull(s);
		return this.urlRepository.save(s);
	}

	public void delete(final Url s) {
		Assert.notNull(s);
		this.urlRepository.delete(s);
	}
}
