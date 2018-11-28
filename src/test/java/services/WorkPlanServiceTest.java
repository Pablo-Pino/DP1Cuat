
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
import domain.WorkPlan;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class WorkPlanServiceTest extends AbstractTest {

	//Service under test ------------------------------------------

	@Autowired
	private WorkPlanService	workPlanService;


	@Test
	public void testCreate() {
		WorkPlan workPlan;

		workPlan = this.workPlanService.create();
		Assert.notNull(workPlan);
	}

	@Test
	public void testFindOne() {
		WorkPlan workPlan;

		workPlan = this.workPlanService.findOne(super.getEntityId("workPlan1"));
		Assert.notNull(workPlan);
	}

	@Test
	public void testFindAll() {

		final Collection<WorkPlan> workPlans = this.workPlanService.findAll();
		Assert.notNull(workPlans);
	}

	@Test
	public void testSave() {
		WorkPlan workPlan;

		workPlan = this.workPlanService.findOne(super.getEntityId("workPlan1"));
		Assert.notNull(workPlan);
	}

	@Test
	public void testDelete() {
		WorkPlan workPlan;
		workPlan = this.workPlanService.findOne(super.getEntityId("workPlan1"));
		this.workPlanService.delete(workPlan);
		Assert.isNull(this.workPlanService.findOne(workPlan.getId()));
	}
}
