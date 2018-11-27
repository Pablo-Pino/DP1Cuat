
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

	public void findByTutorialSection(final Integer tutorialId, final Class<?> expected) {
		Class<?> caught = null;
		try {
			final Tutorial tutorial = this.tutorialService.findOne(tutorialId);
			final Collection<Section> sections = this.sectionService.findAll(tutorial);
			Assert.isTrue(tutorial.getSections().equals(sections));
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

	public void saveSection(final String username, final int numberOrder, final Collection<Url> pictures, final String text, final String title, final String description, final Date start, final Date end, final Integer sectionId, final Integer tutorialId,
		final Class<?> expected) {
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
			section.setPictures(pictures);
			section.setText(text);
			section.setTitle(title);
			section.setTutorial(tutorial);
			final Section savedSection = this.sectionService.save(section);
			this.sectionService.flush();
			Assert.isTrue(savedSection.getPictures().equals(pictures));
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
		this.checkExceptions(expected, caught);
	}

}
