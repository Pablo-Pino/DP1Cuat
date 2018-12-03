
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import security.Authority;
import security.LoginService;
import domain.Actor;
import domain.DomainEntity;

@Service
@Transactional
public class ServiceUtils {

	@Autowired
	private ActorService	actorService;


	public void checkAuthority(final String auth) {
		if (auth != null) {
			Boolean res = false;
			Assert.notNull(LoginService.getPrincipal());
			Assert.notNull(auth);
			for (final Authority a : LoginService.getPrincipal().getAuthorities())
				if (a.getAuthority().equals(auth)) {
					res = true;
					break;
				}
			Assert.isTrue(res);
		}
	}

	public Boolean checkAuthorityBoolean(final String auth) {
		Boolean res = true;
		if (auth != null) {
			res = false;
			Assert.notNull(LoginService.getPrincipal());
			Assert.notNull(auth);
			for (final Authority a : LoginService.getPrincipal().getAuthorities())
				if (a.getAuthority().equals(auth)) {
					res = true;
					break;
				}
		}
		return res;
	}

	public void checkAnyAuthority(final String[] auths) {
		if (auths != null) {
			Boolean res = false;
			Assert.notNull(auths);
			Assert.notNull(LoginService.getPrincipal());
			for (final Authority a : LoginService.getPrincipal().getAuthorities())
				for (final String s : auths) {
					if (s.equals(a)) {
						res = true;
						break;
					}
					if (res)
						break;
				}
			Assert.isTrue(res);
		}
	}

	public void checkId(final Integer id) {
		Assert.notNull(id);
		Assert.isTrue(id > 0);
	}

	public void checkIds(final Collection<Integer> ids) {
		Assert.notNull(ids);
		for (final Integer id : ids)
			this.checkId(id);
	}

	public void checkId(final DomainEntity de) {
		Assert.notNull(de.getId());
		Assert.isTrue(de.getId() > 0);
	}

	public void checkIdSave(final DomainEntity de) {
		Assert.notNull(de.getId());
		Assert.isTrue(de.getId() >= 0);
	}

	public void checkNoActor() {
		final Actor principal = this.actorService.findOneByUserAccount(LoginService.getPrincipal());
		Assert.isNull(principal);
	}

	public void checkActor(final Actor a) {
		if (a != null) {
			// Comprueba el id del Actor
			this.checkId(a);
			// Comprueba que el Actor est� en la base de datos
			Assert.notNull(this.actorService.findOne(a.getId()));
			// Comprueba que el actor de entrada y el actor logueado son el mismo
			final Actor principal = this.actorService.findOneByUserAccount(LoginService.getPrincipal());
			Assert.isTrue(principal.equals(a));
		}
	}

	public void checkAnyActor(final Actor[] as) {
		if (as != null) {
			Boolean res = false;
			for (final Actor a : as) {
				try {
					this.checkActor(a);
				} catch (final Throwable t) {
					continue;
				}
				res = true;
				break;
			}
			Assert.isTrue(res);
		}
	}

}
