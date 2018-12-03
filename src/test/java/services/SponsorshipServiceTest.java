
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Sponsorship;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SponsorshipServiceTest extends AbstractTest {

	//Service under test ------------------------------------------

	@Autowired
	private SponsorshipService	sponsorshipService;


	@Test
	public void testCreate() {
		Sponsorship sponsorship;

		sponsorship = this.sponsorshipService.create();
		Assert.notNull(sponsorship);
	}

	@Test
	public void testFindOne() {
		Sponsorship sponsorship;

		sponsorship = this.sponsorshipService.findOne(super.getEntityId("sponsorship1"));
		Assert.notNull(sponsorship);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindOneError() {
		Sponsorship sponsorship;

		sponsorship = this.sponsorshipService.findOne(super.getEntityId("ssponsorship1"));
		Assert.notNull(sponsorship);
	}

	@Test
	public void testFindAll() {

		final Collection<Sponsorship> sponsorships = this.sponsorshipService.findAll();
		Assert.notNull(sponsorships);
	}

	@Test
	public void testSave() {
		Sponsorship sponsorship;

		sponsorship = this.sponsorshipService.findOne(super.getEntityId("sponsorship1"));
		Assert.notNull(sponsorship);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSaveError() {
		Sponsorship sponsorship;

		sponsorship = this.sponsorshipService.findOne(super.getEntityId("ssponsorship1"));
		Assert.notNull(sponsorship);
	}

	@Test
	public void testDelete() {
		Sponsorship sponsorship;
		this.authenticate("sponsor1");
		sponsorship = this.sponsorshipService.findOne(super.getEntityId("sponsorship1"));
		this.sponsorshipService.delete(sponsorship);
		Assert.isNull(this.sponsorshipService.findOne(sponsorship.getId()));
	}
}
