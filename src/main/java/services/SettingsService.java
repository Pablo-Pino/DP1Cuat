
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.SettingsRepository;
import security.Authority;
import domain.Settings;

@Service
@Transactional
public class SettingsService extends GenericService<Settings, SettingsRepository> implements ServiceI<Settings> {

	@Autowired
	private SettingsRepository	repository;
	@Autowired
	private ServiceUtils serviceUtils;


	@Override
	public Settings create() {
		throw new IllegalArgumentException("Unallowed method");
	}

	@Override
	public Settings save(final Settings object) {
		super.checkObject(object);
		this.serviceUtils.checkAuthority(Authority.ADMIN);
		final Settings res = this.repository.save(object);
		return res;
	}

	@Override
	public void delete(final Settings object) {
		throw new IllegalArgumentException("Unallowed method");
	}

	public Settings getSettings() {
		return this.repository.getSettings();
	}

	public void flush() {
		this.repository.flush();
	}

}
