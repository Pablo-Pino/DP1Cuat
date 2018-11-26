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
import domain.HandyWorker;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional

public class HandyWorkerServiceTest extends AbstractTest{
	
	//Service under test ----------------------------------------

	@Autowired
	private HandyWorkerService	handyworkerService;


	//------------------------------------------------------------

	@Test
	public void testCreate() {
		final HandyWorker hw = this.handyworkerService.create();
		Assert.notNull(hw);
	}

	@Test
	public void testFindOneCorrecto() {
		HandyWorker hw;
		final int idBusqueda = super.getEntityId("handyWorker1");
		hw = this.handyworkerService.findOne(idBusqueda);
		Assert.notNull(hw);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindOneIncorrecto() {
		HandyWorker hw;

		final int idBusqueda = super.getEntityId("jandiwoke");
		hw = this.handyworkerService.findOne(idBusqueda);
		Assert.isNull(hw);
	}

	@Test
	public void testFindAll() {
		Collection<HandyWorker> handyworkers;

		handyworkers = this.handyworkerService.findAll();
		Assert.notNull(handyworkers);
		Assert.notEmpty(handyworkers);
	}

	@Test
	public void testSaveHandyworkerCorrecto() {
		HandyWorker hw;
		hw = this.handyworkerService.findOne(this.getEntityId("handyWorker1"));
		Assert.notNull(hw);
		hw = this.handyworkerService.save(hw);
		Assert.isTrue(hw.getName()== "Antonio");

	}

	@Test(expected = IllegalArgumentException.class)
	public void testSaveHandyworkerIncorrecto() {
		HandyWorker hw;
		hw = this.handyworkerService.findOne(this.getEntityId("handyWorker1"));
		Assert.notNull(hw);
		hw = this.handyworkerService.save(hw);
		Assert.isTrue(hw.getName()== "Luis");
	}
	
	@Test
	public void testDelete() {
		HandyWorker h;

		h = this.handyworkerService.findOne(super.getEntityId("handyWorker1"));
		this.handyworkerService.delete(h);
		Assert.isNull(this.handyworkerService.findOne(h.getId()));
	}

	@Test
	public void testHandyWorkerFixupStats() {
		//this.authenticate("handyWorker2");
		final Collection<HandyWorker> result = this.handyworkerService.getTop3HandyWorkerWithMoreComplaints();
		System.out.println("El top 3 de handyworker con mas complaints:" + result);

	}
	
	@Test
	public void testHandyWorkerfixupTasksTop3() {
		//this.authenticate("handyWorker2");
		final Map<String, Collection<HandyWorker>> result = this.handyworkerService.fixupTasksTop3();
		System.out.println("El top 3 de handyworker con mas fixuptask:" + result);

	}

}
