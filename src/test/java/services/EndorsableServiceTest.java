
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
import domain.Endorsable;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class EndorsableServiceTest extends AbstractTest {

	//Service under test ------------------------------------------

	@Autowired
	private EndorsableService	endorsableService;


	//test-------------------------------------------------------------------

	@Test
	//(expected = IllegalArgumentException.class)
	public void findOneTestCorrecto() {
		Endorsable endorsable;
		final int idBusqueda = super.getEntityId("customer1");
		endorsable = this.endorsableService.findOne(idBusqueda);
		System.out.println(endorsable);
		Assert.notNull(endorsable);
	}
	@Test(expected = IllegalArgumentException.class)
	public void findOneTestIncorrecto() {
		Endorsable endorsable;
		final int idBusqueda = super.getEntityId("cusewmer1");
		endorsable = this.endorsableService.findOne(idBusqueda);
		System.out.println(endorsable);
		Assert.notNull(endorsable);
	}
	@Test
	public void findAll() {
		Collection<Endorsable> res;
		res = this.endorsableService.findAll();
		Assert.notNull(res);
		Assert.notEmpty(res);
	}

}
