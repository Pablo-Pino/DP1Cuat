
package services;

import java.util.Collection;
import java.util.Map;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.FixupTask;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class FixupTaskServiceTest extends AbstractTest {

	//Service under test ----------------------------------------

	@Autowired
	private FixupTaskService	fixupTaskService;


	//------------------------------------------------------------

	@Test
	public void testCreate() {
		this.authenticate("customer1");
		final FixupTask ft = this.fixupTaskService.create();
		Assert.notNull(ft);
	}

	@Test
	public void testFindOneCorrecto() {
		FixupTask ft;
		final int idBusqueda = super.getEntityId("fixupTask1");
		ft = this.fixupTaskService.findOne(idBusqueda);
		Assert.notNull(ft);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindOneIncorrecto() {
		FixupTask ft;
		final int idBusqueda = super.getEntityId("fixu");
		ft = this.fixupTaskService.findOne(idBusqueda);
		Assert.isNull(ft);
	}

	@Test
	public void testFindAll() {
		Collection<FixupTask> fixupTasks;

		fixupTasks = this.fixupTaskService.findAll();
		Assert.notNull(fixupTasks);
		Assert.notEmpty(fixupTasks);
	}

	@Test
	public void saveTestCorrecto() {
		FixupTask ft, saved;
		final int ftId = this.getEntityId("fixupTask1");
		ft = this.fixupTaskService.findOne(ftId);
		Assert.notNull(ft);

		ft.setAddress("seviia");
		saved = this.fixupTaskService.save(ft);
		Assert.isTrue(saved.getAddress().equals("seviia"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void saveTestIncorrecto() {
		FixupTask ft;
		FixupTask saved;
		final int ftId = this.getEntityId("fixupTask1");
		ft = this.fixupTaskService.findOne(ftId);
		Assert.notNull(ft);

		ft.setMoment(null);
		saved = this.fixupTaskService.save(ft);
		Assert.isNull(saved);
	}

	@Test
	public void deleteTestCorrecto() {
		FixupTask ft;
		final int ftId = this.getEntityId("fixupTask1");
		ft = this.fixupTaskService.findOne(ftId);
		Assert.notNull(ft);

		this.fixupTaskService.delete(ft);
		Assert.isNull(ft = this.fixupTaskService.findOne(ftId));
	}

	@Test(expected = IllegalArgumentException.class)
	public void deleteTestIncorrecto() {
		FixupTask ft;
		final int ftId = this.getEntityId("Error intencionado");
		ft = this.fixupTaskService.findOne(ftId);
		Assert.notNull(ft);

		this.fixupTaskService.delete(ft);
		Assert.isNull(ft = this.fixupTaskService.findOne(ftId));
	}

	//Other methods

	@Test
	public void testAppStats() {
		this.authenticate("admin1");
		final Map<String, Double> result = this.fixupTaskService.appsStats();
		System.out.println("Las caracteristicas de las fiuptask son:" + result);

	}

	@Test
	public void testRatioFixupTasksWithComplaints() {
		this.authenticate("admin1");
		final Map<String, Double> result = this.fixupTaskService.getRatioFixupTasksWithComplaints();
		System.out.println("El ratio de las fiuptask con mas complaints:" + result);

	}

	@Test
	public void testmaxFixupStaskStats() {
		this.authenticate("admin1");
		final Map<String, Double> result = this.fixupTaskService.maxFixupStaskStats();
		System.out.println("Caracteristicas fixup max:" + result);

	}

	@Test
	public void testfixupComplaintsStats() {
		this.authenticate("admin1");
		final Map<String, Double> result = this.fixupTaskService.fixupComplaintsStats();
		System.out.println("Caracteristicas fixup complaints:" + result);

	}

}
