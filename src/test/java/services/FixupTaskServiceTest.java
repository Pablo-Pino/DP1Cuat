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

}
