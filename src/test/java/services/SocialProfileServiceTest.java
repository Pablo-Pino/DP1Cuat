
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Actor;
import domain.SocialProfile;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SocialProfileServiceTest extends AbstractTest {

	// Services

	@Autowired
	private SocialProfileService	socialProfileService;
	@Autowired
	private ActorService			actorService;


	// Tests

	public void findOneSocialProfile(final Integer socialProfileId, final Class<?> expected) {
		Class<?> caught = null;
		try {
			this.socialProfileService.findOne(socialProfileId);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	public void findAllSocialProfile(final Collection<Integer> socialProfileIds, final Class<?> expected) {
		Class<?> caught = null;
		try {
			this.socialProfileService.findAll(socialProfileIds);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	public void findAllSocialProfile(final Class<?> expected) {
		Class<?> caught = null;
		try {
			this.socialProfileService.findAll();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	public void createSocialProfile(final String username, final Integer actorId, final Class<?> expected) {
		Class<?> caught = null;
		try {
			this.authenticate(username);
			final Actor actor = this.actorService.findOne(actorId);
			final SocialProfile socialProfile = this.socialProfileService.create(actor);
			Assert.notNull(socialProfile);
			Assert.notNull(socialProfile.getActor());
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	public void saveSocialProfile(final String username, final String networkName, final String nick, final String profile, final Integer actorId, final Integer socialProfileId, final Class<?> expected) {
		Class<?> caught = null;
		try {
			this.authenticate(username);
			SocialProfile socialProfile = null;
			SocialProfile oldSocialProfile = null;
			final Actor actor = this.actorService.findOne(actorId);
			if (socialProfileId == null)
				socialProfile = this.socialProfileService.create(actor);
			else
				socialProfile = this.socialProfileService.findOne(socialProfileId);
			oldSocialProfile = socialProfile;
			socialProfile.setActor(actor);
			socialProfile.setNetworkName(networkName);
			socialProfile.setNick(nick);
			socialProfile.setProfile(profile);
			final SocialProfile savedSocialProfile = this.socialProfileService.save(socialProfile);
			this.socialProfileService.flush();
			Assert.isTrue(savedSocialProfile.getNetworkName().equals(networkName));
			Assert.isTrue(savedSocialProfile.getNick().equals(nick));
			Assert.isTrue(savedSocialProfile.getProfile().equals(profile));
			if (socialProfileId == null)
				Assert.isTrue(savedSocialProfile.getActor() == actor);
			else
				Assert.isTrue(savedSocialProfile.getActor() == oldSocialProfile.getActor());
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	public void deleteSocialProfile(final String username, final Integer socialProfileId, final Class<?> expected) {
		Class<?> caught = null;
		try {
			this.authenticate(username);
			final SocialProfile socialProfile = this.socialProfileService.findOne(socialProfileId);
			this.socialProfileService.delete(socialProfile);
			this.socialProfileService.flush();
			Assert.isNull(this.socialProfileService.findOne(socialProfileId));
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
