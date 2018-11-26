
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
import domain.Customer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class CustomerServiceTest extends AbstractTest {

	//Service under test ------------------------------------------

	@Autowired
	private CustomerService	customerService;


	//test-------------------------------------------------------------------

	@Test
	public void createCorrecto() {
		final Customer customer = this.customerService.create();
		Assert.notNull(customer);
	}

	@Test
	public void testSaveCorrecto() {

		Customer customer, saved;
		int customerId;
		customerId = this.getEntityId("customer1");
		customer = this.customerService.findOne(customerId);
		Assert.notNull(customer);

		this.authenticate("customer1");

		customer.setSurname("otro");
		saved = this.customerService.save(customer);
		Assert.isTrue(saved.getSurname().equals("otro"));
	}

	@Test
	public void testFindOneCorrecto() {
		Customer customer;

		final int idBusqueda = super.getEntityId("customer1");
		customer = this.customerService.findOne(idBusqueda);
		Assert.notNull(customer);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindOneIncorrecto() {
		Customer customer;

		final int idBusqueda = super.getEntityId("custemer");
		customer = this.customerService.findOne(idBusqueda);
		Assert.isNull(customer);
	}

	@Test
	public void testFindAll() {
		Collection<Customer> customers;

		customers = this.customerService.findAll();
		Assert.notNull(customers);
		Assert.notEmpty(customers); //porque sabemos que hemos creado algunos con el populate
	}

}
