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
public class FixupTaskServiceTest extends AbstractTest{ 
	//Service under test ----------------------------------------

	@Autowired
	private FixupTaskService	fixupTaskService;


	//------------------------------------------------------------

	@Test
	public void testCreate() {
		final FixupTask f = this.fixupTaskService.create();
		Assert.notNull(f);
	}

	@Test
	public void testFindOneCorrecto() {
		FixupTask f;
		final int idBusqueda = super.getEntityId("fixupTask1");
		f = this.fixupTaskService.findOne(idBusqueda);
		Assert.notNull(f);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindOneIncorrecto() {
		FixupTask f;

		final int idBusqueda = super.getEntityId("fixap");
		f = this.fixupTaskService.findOne(idBusqueda);
		Assert.isNull(f);
	}

	@Test
	public void testFindAll() {
		Collection<FixupTask> fixupTasks;

		fixupTasks = this.fixupTaskService.findAll();
		Assert.notNull(fixupTasks);
		Assert.notEmpty(fixupTasks);
	}

	@Test
	public void testSaveFixupTaskCorrecto() {
		FixupTask f;
		f = this.fixupTaskService.findOne(this.getEntityId("fixupTask1"));
		Assert.notNull(f);
		f = this.fixupTaskService.save(f);
		Assert.isTrue(f.getTicker()== "81103-RTIB01");

	}

	@Test(expected = IllegalArgumentException.class)
	public void testSaveFixupTaskIncorrecto() {
		FixupTask f;
		f = this.fixupTaskService.findOne(this.getEntityId("fixupTask1"));
		Assert.notNull(f);
		f = this.fixupTaskService.save(f);
		Assert.isTrue(f.getTicker()== "81103-B01");

	}
	
	@Test
	public void testDelete() {
		FixupTask f;

		f = this.fixupTaskService.findOne(super.getEntityId("fixupTask1"));
		this.fixupTaskService.delete(f);
		Assert.isNull(this.fixupTaskService.findOne(f.getId()));
	}
	
	@Test
	public void testAppStats() {
		this.authenticate("admin1");
		final Map<String, Double> result = this.fixupTaskService.appsStats();
		System.out.println("Las caracteristicas de las fiuptask son:" + result);

	}
	
//	@Test
//	public void testRatioFixupTasksWithComplaints() {
//		this.authenticate("admin1");
//		final Map<String, Double> result = this.fixupTaskService.getRatioFixupTasksWithComplaints();
//		System.out.println("El ratio de las fiuptask con mas complaints:" + result);
//
//	}
	
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
