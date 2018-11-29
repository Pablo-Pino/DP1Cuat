
package services;

import java.util.ArrayList;
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
import domain.Actor;
import domain.Administrator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ActorServiceTest extends AbstractTest {

	//Service under test ------------------------------------------

	@Autowired
	private ActorService			actorService;
	@Autowired
	private AdministratorService	administratorService;


	@Test
	public void testUserFixupTaskStats() {
		this.authenticate("customer1");
		final Map<String, Double> result = this.actorService.fixupTasksStats();
		System.out.println("Las variables de FixupTask para este usuario son: " + result);

	}

	@Test
	public void testgetSuspicious() {
		this.authenticate("admin2");
		final int actorId = this.getEntityId("administrator1");
		Actor actor;
		actor = this.actorService.findOne(actorId);
		final Collection<Actor> suspicious = new ArrayList<Actor>(this.actorService.suspiciousActors());
		Assert.isTrue(suspicious.contains(actor));
		System.out.println("Lista de actores sospechosos: " + suspicious);
		//administrator1 es sospechoso porque lo he cambiado yo queriendo

	}

	//	@Test
	//	public void testBanUnban() {
	//		//this.authenticate("super");
	//		final int actorId = this.getEntityId("administrator1");
	//		Actor actor;
	//		actor = this.actorService.findOne(actorId);
	//		this.actorService.banActor(actor);
	//		Assert.notNull(actor);
	//		//Assert.isTrue(actor.getBanned());
	//		this.actorService.unbanActor(actor);
	//		Assert.notNull(actor);
	//		Assert.isTrue(!(actor.getBanned()));
	//
	//	}

	@Test
	public void banActorCorrecto() {
		this.authenticate("admin1");
		final int actId = this.getEntityId("administrator1");
		final Administrator a = this.administratorService.findOne(actId);
		Assert.notNull(a);
		Assert.isTrue(this.actorService.banActor(a));
	}

	@Test
	public void banActorOtro() {
		this.authenticate("admin2");
		final int actId = this.getEntityId("administrator1");
		final Administrator a = this.administratorService.findOne(actId);
		Assert.notNull(a);
		Assert.isTrue(this.actorService.banActor(a));
	}

	@Test
	public void unBanActor() {
		this.authenticate("admin2");
		final int actId = this.getEntityId("administrator1");
		final Administrator a = this.administratorService.findOne(actId);
		Assert.notNull(a);
		Assert.isTrue(this.actorService.banActor(a));

		Assert.isTrue((a.getBanned()));
		Assert.isTrue(this.actorService.unBanActor(a));

		Assert.isTrue(!(a.getBanned()));

	}

}
