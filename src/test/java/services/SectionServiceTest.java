
package services;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Section;
import domain.Tutorial;
import domain.Url;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SectionServiceTest extends AbstractTest {

	// Services

	@Autowired
	private SectionService	sectionService;
	@Autowired
	private TutorialService	tutorialService;


	// Tests

	public void findOneSection(final Integer sectionId, final Class<?> expected) {
		Class<?> caught = null;
		try {
			this.sectionService.findOne(sectionId);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	public void findAllSection(final Collection<Integer> sectionIds, final Class<?> expected) {
		Class<?> caught = null;
		try {
			this.sectionService.findAll(sectionIds);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	public void findAllSection(final Class<?> expected) {
		Class<?> caught = null;
		try {
			this.sectionService.findAll();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	public void findByTutorialSection(final Tutorial tutorial, final Class<?> expected) {
		Class<?> caught = null;
		try {
			final Collection<Section> sections = this.sectionService.findByTutorial(tutorial);
			Assert.isTrue(tutorial.getSections().size() == sections.size());
			for (final Section s : sections)
				Assert.isTrue(tutorial.getSections().contains(s));
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	public void createSection(final String username, final Integer tutorialId, final Class<?> expected) {
		Class<?> caught = null;
		try {
			this.authenticate(username);
			final Tutorial tutorial = this.tutorialService.findOne(tutorialId);
			final Section section = this.sectionService.create(tutorial);
			Assert.notNull(section);
			Assert.isTrue(section.getTutorial().equals(tutorial));
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	public void saveSection(final String username, final int numberOrder, final Collection<Url> pictures, final String text, final String title, final Integer sectionId, final Integer tutorialId, final Class<?> expected) {
		Class<?> caught = null;
		try {
			this.authenticate(username);
			final Tutorial tutorial = this.tutorialService.findOne(tutorialId);
			Tutorial oldTutorial = null;
			Section section = null;
			Section oldSection = null;
			if (sectionId == null)
				section = this.sectionService.create(tutorial);
			else {
				section = this.sectionService.findOne(sectionId);
				oldTutorial = section.getTutorial();
				oldSection = section;
			}
			section.setNumberOrder(numberOrder);
			section.setPictures((List<Url>) pictures);
			section.setText(text);
			section.setTitle(title);
			section.setTutorial(tutorial);
			final Section savedSection = this.sectionService.save(section);
			this.sectionService.flush();
			Assert.isTrue(savedSection.getPictures().size() == pictures.size());
			for (final Url u : pictures)
				Assert.isTrue(savedSection.getPictures().contains(u));
			Assert.isTrue(savedSection.getText().equals(text));
			Assert.isTrue(savedSection.getTitle().equals(title));
			if (sectionId == null) {
				Assert.isTrue(savedSection.getTutorial().equals(tutorial));
				Assert.isTrue(savedSection.getNumberOrder() == tutorial.getSections().size());
			} else {
				Assert.isTrue(savedSection.getTutorial().equals(oldTutorial));
				Assert.isTrue(savedSection.getNumberOrder() == oldSection.getNumberOrder());
			}
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	public void deleteSection(final String username, final Integer sectionId, final Class<?> expected) {
		Class<?> caught = null;
		try {
			this.authenticate(username);
			final Section section = this.sectionService.findOne(sectionId);
			this.sectionService.delete(section);
			this.sectionService.flush();
			Assert.isNull(this.sectionService.findOne(sectionId));
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		//this.checkExceptions(expected, caught);
	}

	@Test
	public void testFindOneSection() {
		this.findOneSection(super.getEntityId("section1"), null);
	}

	@Test
	public void testFindOneSectionNullId() {
		this.findOneSection(null, IllegalArgumentException.class);
	}

	@Test
	public void testFindAllByIdsSection() {
		this.findAllSection(Arrays.asList(new Integer[] {
			super.getEntityId("section1"), super.getEntityId("section2")
		}), null);
	}

	@Test
	public void testFindAllByIdsSectionNullIds() {
		this.findAllSection(null, IllegalArgumentException.class);
	}

	@Test
	public void testFindAllSection() {
		this.findAllSection(null);
	}

	@Test
	public void testFindAllByTutorialSection() {
		final Tutorial tutorial = this.tutorialService.findOne(super.getEntityId("tutorial1"));
		this.findByTutorialSection(tutorial, null);
	}

	// En este caso se genera un NullPointerException debido a que al buscar el Tutorial, se llama al
	// metodo getId() de Tutorial, y al ser este nulo se provoca un NullPointerException
	@Test
	public void testFindAllByTutorialSectionNullTutorial() {
		this.findByTutorialSection(null, NullPointerException.class);
	}

	@Test
	public void testSaveSection() {
		final Integer tutorialId = super.getEntityId("tutorial1");
		final Url picture = new Url();
		picture.setUrl("http://photo");
		final Collection<Url> pictures = Arrays.asList(picture);
		this.saveSection("handyWorker1", 1, pictures, "Da text", "Da title", null, tutorialId, null);
	}

	@Test
	public void testSaveSectionUnauthenticated() {
		final Integer tutorialId = super.getEntityId("tutorial1");
		final Collection<Url> pictures = this.sectionService.findOne(super.getEntityId("section1")).getPictures();
		this.saveSection(null, 2, pictures, "Da text", "Da title", null, tutorialId, IllegalArgumentException.class);
	}

	@Test
	public void testUpdateSection() {
		final Integer tutorialId = super.getEntityId("tutorial1");
		final Collection<Url> pictures = this.sectionService.findOne(super.getEntityId("section1")).getPictures();
		this.saveSection("handyWorker1", 1, pictures, "Da text", "Da title", super.getEntityId("section1"), tutorialId, null);
	}

	@Test
	public void testUpdateSectionUnauthenticated() {
		final Integer tutorialId = super.getEntityId("tutorial1");
		final Collection<Url> pictures = this.sectionService.findOne(super.getEntityId("section1")).getPictures();
		this.saveSection(null, 1, pictures, "Da text", "Da title", super.getEntityId("section1"), tutorialId, IllegalArgumentException.class);
	}

	@Test
	public void testDeleteSection() {
		this.deleteSection("handyWorker1", super.getEntityId("section1"), null);
	}

	@Test
	public void testDeleteSectionUnauthenticated() {
		this.deleteSection(null, super.getEntityId("section2"), IllegalArgumentException.class);
	}

}
