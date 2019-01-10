
package services;

import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Customer;
import domain.FixupTask;
import domain.HandyWorker;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class HandyWorkerServiceTest extends AbstractTest {

	//Service under test ----------------------------------------

	@Autowired
	private HandyWorkerService	handyworkerService;

	@Autowired
	private CustomerService		customerService;


	//------------------------------------------------------------

	@Test
	public void testCreate() {
		final HandyWorker hw = this.handyworkerService.create();
		Assert.notNull(hw);
	}

	@Test
	public void testFindOneCorrecto() {
		HandyWorker mr;
		final int idBusqueda = super.getEntityId("handyWorker1");
		mr = this.handyworkerService.findOne(idBusqueda);
		Assert.notNull(mr);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindOneIncorrecto() {
		HandyWorker hw;
		final int idBusqueda = super.getEntityId("handiwoke");
		hw = this.handyworkerService.findOne(idBusqueda);
		Assert.isNull(hw);
	}

	@Test
	public void testFindAll() {
		Collection<HandyWorker> handyWorkers;

		handyWorkers = this.handyworkerService.findAll();
		Assert.notNull(handyWorkers);
		Assert.notEmpty(handyWorkers);
	}

	@Test
	public void saveTestCorrecto() {
		HandyWorker hw, saved;
		this.authenticate("handyWorker1");
		final int mrId = this.getEntityId("handyWorker1");
		hw = this.handyworkerService.findOne(mrId);
		Assert.notNull(hw);

		hw.setName("Antonio");
		saved = this.handyworkerService.save(hw);
		Assert.isTrue(saved.getName().equals("Antonio"));
	}

	@Test(expected = ConstraintViolationException.class)
	public void saveTestIncorrecto() {
		HandyWorker hw;
		HandyWorker saved;
		final int mrId = this.getEntityId("handyWorker1");
		hw = this.handyworkerService.findOne(mrId);
		Assert.notNull(hw);

		hw.setMake(null);
		saved = this.handyworkerService.save(hw);
		Assert.isNull(saved);
	}

	@Test
	public void deleteTestCorrecto() {
		HandyWorker hw;
		final int hwId = this.getEntityId("handyWorker1");
		hw = this.handyworkerService.findOne(hwId);
		Assert.notNull(hw);

		this.handyworkerService.delete(hw);
		Assert.isNull(hw = this.handyworkerService.findOne(hwId));
	}

	@Test(expected = IllegalArgumentException.class)
	public void deleteTestIncorrecto() {
		HandyWorker hw;
		final int hwId = this.getEntityId("Error intencionado");
		hw = this.handyworkerService.findOne(hwId);
		Assert.notNull(hw);

		this.handyworkerService.delete(hw);
		Assert.isNull(hw = this.handyworkerService.findOne(hwId));
	}

	@Test
	public void getAllFixupTasks() {
		Collection<FixupTask> res;
		res = this.handyworkerService.getAllFixupTasks();
		Assert.notNull(res);
		Assert.notEmpty(res);

	}

	@Test
	public void getFixupTasksFromCustomer() {
		Collection<FixupTask> res;

		Customer c;
		final int cId = this.getEntityId("customer1");
		c = this.customerService.findOne(cId);
		Assert.notNull(c);

		res = this.handyworkerService.getFixupTaskFromCustomer(c);
		Assert.notNull(res);
		Assert.notEmpty(res);

	}

}
