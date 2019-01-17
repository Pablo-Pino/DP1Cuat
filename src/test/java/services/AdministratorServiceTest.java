
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
import domain.Administrator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class AdministratorServiceTest extends AbstractTest {

	//Service under test ------------------------------------------

	@Autowired
	private AdministratorService	administratorService;


	@Test
	public void testCreate() {
		final Administrator a = this.administratorService.create();
		Assert.notNull(a);
	}
	@Test
	public void testFindOneCorrecto() {
		Administrator admin;

		final int idBusqueda = super.getEntityId("administrator1");
		admin = this.administratorService.findOne(idBusqueda);
		Assert.notNull(admin);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindOneIncorrecto() {
		Administrator admin;

		final int idBusqueda = super.getEntityId("abelardo");
		admin = this.administratorService.findOne(idBusqueda);
		Assert.isNull(admin);
	}

	@Test
	public void testFindAll() {
		Collection<Administrator> admins;

		admins = this.administratorService.findAll();
		Assert.notNull(admins);
		Assert.notEmpty(admins); //porque sabemos que hemos creado algunos con el populate
	}

	@Test
	public void testSaveCorrecto() {

		Administrator administrator, saved;
		int adminId;
		adminId = this.getEntityId("administrator1");
		administrator = this.administratorService.findOne(adminId);
		Assert.notNull(administrator);

		this.authenticate("admin1");

		administrator.setSurname("Pablo");
		saved = this.administratorService.save(administrator);
		Assert.isTrue(saved.getSurname().equals("Pablo"));

		this.unauthenticate();
	}
	

}
