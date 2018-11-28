
package services;

import java.util.Map;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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

}
