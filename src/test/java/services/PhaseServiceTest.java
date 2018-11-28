
package services;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.FixupTask;
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
	private PhaseService		phaseService;
	@Autowired
	private WorkPlanService		workPlanService;
	@Autowired
	private FixupTaskService	fixupTaskService;


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

	public void findByWorkPlanPhase(final WorkPlan workPlan, final Class<?> expected) {
		Class<?> caught = null;
		try {
			final Collection<Phase> phases = this.phaseService.findAll(workPlan);
			Assert.isTrue(workPlan.getPhases().size() == phases.size());
			for (final Phase p : phases)
				Assert.isTrue(workPlan.getPhases().contains(p));
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

	@Test
	public void testFindOnePhase() {
		this.findOnePhase(super.getEntityId("phase1"), null);
	}

	@Test
	public void testFindOnePhaseNullId() {
		this.findOnePhase(null, IllegalArgumentException.class);
	}

	@Test
	public void testFindAllByIdsPhase() {
		this.findAllPhase(Arrays.asList(new Integer[] {
			super.getEntityId("phase1"), super.getEntityId("phase2")
		}), null);
	}

	@Test
	public void testFindAllByIdsPhaseNullIds() {
		this.findAllPhase(null, IllegalArgumentException.class);
	}

	@Test
	public void testFindAllPhase() {
		this.findAllPhase(null);
	}

	@Test
	public void testFindAllByWorkPlanPhase() {
		final WorkPlan workPlan = this.workPlanService.findOne(super.getEntityId("workPlan1"));
		this.findByWorkPlanPhase(workPlan, null);
	}

	// En este caso se genera un NullPointerException debido a que al buscar el WorkPlan, se llama al
	// metodo getId() de WorkPlan, y al ser este nulo se provoca un NullPointerException
	@Test
	public void testFindAllByWorkPlanPhaseNullWorkPlan() {
		this.findByWorkPlanPhase(null, NullPointerException.class);
	}

	@Test
	public void testSavePhase() {
		final Integer workPlanId = super.getEntityId("workPlan1");
		final FixupTask fixupTask = this.fixupTaskService.findOne(super.getEntityId("fixupTask1"));
		this.savePhase("handywoker1", "A phase", "A nice phase", new Date(fixupTask.getStart().getTime() + 1000), new Date(fixupTask.getEnd().getTime() - 1000), null, workPlanId, null);
	}

	@Test
	public void testSavePhaseUnauthenticated() {
		final Integer workPlanId = super.getEntityId("workPlan1");
		final FixupTask fixupTask = this.fixupTaskService.findOne(super.getEntityId("fixupTask1"));
		this.savePhase(null, "A phase", "A nice phase", new Date(fixupTask.getStart().getTime() + 1000), new Date(fixupTask.getEnd().getTime() - 1000), null, workPlanId, IllegalArgumentException.class);
	}

	@Test
	public void testUpdatePhase() {
		final Integer workPlanId = super.getEntityId("workPlan1");
		final FixupTask fixupTask = this.fixupTaskService.findOne(super.getEntityId("fixupTask1"));
		this.savePhase("handywoker1", "A phase", "A nice phase", new Date(fixupTask.getStart().getTime() + 1000), new Date(fixupTask.getEnd().getTime() - 1000), super.getEntityId("phase1"), workPlanId, null);
	}

	@Test
	public void testUpdatePhaseUnauthenticated() {
		final Integer workPlanId = super.getEntityId("workPlan1");
		final FixupTask fixupTask = this.fixupTaskService.findOne(super.getEntityId("fixupTask1"));
		this.savePhase(null, "A phase", "A nice phase", new Date(fixupTask.getStart().getTime() + 1000), new Date(fixupTask.getEnd().getTime() - 1000), super.getEntityId("phase1"), workPlanId, IllegalArgumentException.class);
	}

	@Test
	public void testDeletePhase() {
		this.deletePhase("handywoker1", super.getEntityId("phase1"), null);
	}

	@Test
	public void testDeletePhaseUnauthenticated() {
		this.deletePhase(null, super.getEntityId("phase2"), IllegalArgumentException.class);
	}

}
