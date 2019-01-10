
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
import domain.Sponsor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SponsorServiceTest extends AbstractTest {

	//Service under test ------------------------------------------

	@Autowired
	private SponsorService	sponsorService;


	@Test
	public void testCreate() {
		Sponsor sponsor;

		sponsor = this.sponsorService.create();
		Assert.notNull(sponsor);
	}

	@Test
	public void testFindOne() {
		Sponsor sponsor;

		sponsor = this.sponsorService.findOne(super.getEntityId("sponsor1"));
		Assert.notNull(sponsor);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindOneError() {
		Sponsor sponsor;

		sponsor = this.sponsorService.findOne(super.getEntityId("ssponsor1"));
		Assert.notNull(sponsor);
	}

	@Test
	public void testFindAll() {

		final Collection<Sponsor> sponsors = this.sponsorService.findAll();
		Assert.notNull(sponsors);
	}

	@Test
	public void testSave() {
		Sponsor sponsor;

		sponsor = this.sponsorService.findOne(super.getEntityId("sponsor1"));
		Assert.notNull(sponsor);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSaveError() {
		Sponsor sponsor;

		sponsor = this.sponsorService.findOne(super.getEntityId("ssponsor1"));
		Assert.notNull(sponsor);
	}

	@Test
	public void testDelete() {
		Sponsor sponsor;
		sponsor = this.sponsorService.findOne(super.getEntityId("sponsor1"));
		//final int id = sponsor.getId();
		this.sponsorService.delete(sponsor);
		Assert.isTrue(!(sponsor.getUserAccount().isEnabled()));
	}
}
