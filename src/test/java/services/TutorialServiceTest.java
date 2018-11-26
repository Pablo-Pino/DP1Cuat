
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
import domain.Tutorial;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class TutorialServiceTest extends AbstractTest {

	//Service under test ------------------------------------------

	@Autowired
	private TutorialService	tutorialService;


	@Test
	public void testCreate() {
		Tutorial tutorial;

		tutorial = this.tutorialService.create();
		Assert.notNull(tutorial);
	}

	@Test
	public void testFindOne() {
		Tutorial tutorial;

		tutorial = this.tutorialService.findOne(super.getEntityId("tutorial1"));
		Assert.notNull(tutorial);
	}

	@Test
	public void testFindAll() {

		final Collection<Tutorial> tutorials = this.tutorialService.findAll();
		Assert.notNull(tutorials);
	}

	@Test
	public void testSave() {
		Tutorial tutorial;

		tutorial = this.tutorialService.findOne(super.getEntityId("tutorial1"));
		Assert.notNull(tutorial);
	}

	@Test
	public void testDelete() {
		Tutorial tutorial;

		tutorial = this.tutorialService.findOne(super.getEntityId("tutorial1"));
		this.tutorialService.delete(tutorial);
		Assert.isNull(this.tutorialService.findOne(tutorial.getId()));
	}
}
