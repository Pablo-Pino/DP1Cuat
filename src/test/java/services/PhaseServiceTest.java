
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Phase;
import domain.WorkPlan;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class PhaseServiceTest extends AbstractTest {

	// Services

	@Autowired
	private PhaseService	phaseService;
	@Autowired
	private WorkPlanService	workPlanService;


	// Tests

	public void findOnePhase(final Integer phaseId, final Class<?> expected) {
		Class<?> caught = null;
		try {
			this.phaseService.findOne(phaseId);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	public void findAllPhase(final Collection<Integer> phaseIds, final Class<?> expected) {
		Class<?> caught = null;
		try {
			this.phaseService.findAll(phaseIds);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	public void findAllPhase(final Class<?> expected) {
		Class<?> caught = null;
		try {
			this.phaseService.findAll();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	public void findByWorkPlanPhase(final Integer workPlanId, final Class<?> expected) {
		Class<?> caught = null;
		try {
			final WorkPlan workPlan = this.workPlanService.findOne(workPlanId);
			final Collection<Phase> phases = this.phaseService.findAll(workPlan);
			Assert.isTrue(workPlan.getPhases().equals(phases));
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	public void createPhase(final String username, final Integer workPlanId, final Class<?> expected) {
		Class<?> caught = null;
		try {
			this.authenticate(username);
			final WorkPlan workPlan = this.workPlanService.findOne(workPlanId);
			final Phase phase = this.phaseService.create(workPlan);
			Assert.notNull(phase);
			Assert.isTrue(phase.getWorkPlan().equals(workPlan));
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	public void savePhase(final String username, final String title, final String description, final Date start, final Date end, final Integer phaseId, final Integer workPlanId, final Class<?> expected) {
		Class<?> caught = null;
		try {
			this.authenticate(username);
			final WorkPlan workPlan = this.workPlanService.findOne(workPlanId);
			WorkPlan oldWorkPlan = null;
			Phase phase = null;
			if (phaseId == null)
				phase = this.phaseService.create(workPlan);
			else {
				phase = this.phaseService.findOne(phaseId);
				oldWorkPlan = phase.getWorkPlan();
			}
			phase.setDescription(description);
			phase.setEnd(end);
			phase.setStart(start);
			phase.setTitle(title);
			phase.setWorkPlan(workPlan);
			final Phase savedPhase = this.phaseService.save(phase);
			this.phaseService.flush();
			Assert.isTrue(savedPhase.getDescription().equals(description));
			Assert.isTrue(savedPhase.getEnd().equals(end));
			Assert.isTrue(savedPhase.getStart().equals(start));
			Assert.isTrue(savedPhase.getTitle().equals(title));
			if (phaseId == null)
				Assert.isTrue(savedPhase.getWorkPlan().equals(workPlan));
			else
				Assert.isTrue(savedPhase.getWorkPlan().equals(oldWorkPlan));
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	public void deletePhase(final String username, final Integer phaseId, final Class<?> expected) {
		Class<?> caught = null;
		try {
			this.authenticate(username);
			final Phase phase = this.phaseService.findOne(phaseId);
			this.phaseService.delete(phase);
			this.phaseService.flush();
			Assert.isNull(this.phaseService.findOne(phaseId));
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
