
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

import domain.Actor;
import domain.Administrator;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ActorServiceTest extends AbstractTest {

	//Service under test ------------------------------------------

	@Autowired
	private ActorService	actorService;


	@Test
	public void testUserFixupTaskStats() {
		this.authenticate("customer1");
		final Map<String, Double> result = this.actorService.fixupTasksStats();
		System.out.println("Las variables de FixupTask para este usuario son: " + result);

	}
	
	@Test
	public void testgetSuspicious() {
		this.authenticate("administrator1");
		int actorId = this.getEntityId("administrator1");
		Actor actor;
		actor = this.actorService.findOne(actorId);
		Collection<Actor> suspicious = new ArrayList<Actor>(this.actorService.suspiciousActors());
		Assert.isTrue(suspicious.contains(actor));
		System.out.println("Lista de actores sospechosos: " + suspicious);
		//administrator1 es sospechoso porque lo he cambiado yo queriendo
	
	}
	
	@Test
	public void testBanUnban() {
		//this.authenticate("super");
		int actorId = this.getEntityId("administrator2");
		Actor actor;
		actor = this.actorService.findOne(actorId);
		this.actorService.banActor(actor);
		Assert.notNull(actor);
		//Assert.isTrue(actor.getBanned());
		this.actorService.unbanActor(actor);
		Assert.notNull(actor);
		Assert.isTrue(!(actor.getBanned()));

	}


}
