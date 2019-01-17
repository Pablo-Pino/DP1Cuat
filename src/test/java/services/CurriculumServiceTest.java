
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Curriculum;
import domain.EducationRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class CurriculumServiceTest extends AbstractTest {

	//Service under test ------------------------------------------

	@Autowired
	private CurriculumService	curriculumService;


	//test-------------------------------------------------------------------

	@Test
	public void createCorrecto() {
		Curriculum curriculum;
		curriculum = this.curriculumService.create();
		Assert.notNull(curriculum);
	}

	@Test
	public void findOneCorrecto() {
		Curriculum res;
		final int curriculumId = super.getEntityId("curriculum1");
		res = this.curriculumService.findOne(curriculumId);
		Assert.notNull(res);
	}

	@Test(expected = IllegalArgumentException.class)
	public void findOneIncorrecto() {
		Curriculum res;
		final int curriculumId = super.getEntityId("curriculem1");
		res = this.curriculumService.findOne(curriculumId);
		Assert.notNull(res);
	}

	@Test
	public void findAll() {
		Collection<Curriculum> curriculums;
		curriculums = this.curriculumService.findAll();
		Assert.notNull(curriculums);
		Assert.notEmpty(curriculums);
	}

	@Test
	public void save() {
		Curriculum curriculum, saved;
		final int curriculumId = this.getEntityId("curriculum1");
		curriculum = this.curriculumService.findOne(curriculumId);
		Assert.notNull(curriculum);
		curriculum.setEducationRecords(new ArrayList<EducationRecord>());
		saved = this.curriculumService.save(curriculum);
		Assert.isTrue(saved.getEducationRecords().equals(curriculum.getEducationRecords()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void saveIncorrecto() {
		Curriculum curriculum, saved;
		final int curriculumId = this.getEntityId("curriculum1");
		curriculum = this.curriculumService.findOne(curriculumId);
		Assert.notNull(curriculum);
		curriculum.setHandyWorker(null);
		saved = this.curriculumService.save(curriculum);
		Assert.notNull(saved);
	}

	@Test
	public void deleteCorrecto() {
		Curriculum curriculum;
		final int curriculumId = this.getEntityId("curriculum1");
		curriculum = this.curriculumService.findOne(curriculumId);
		Assert.notNull(curriculum);
		this.curriculumService.delete(curriculum);
		Assert.isNull(this.curriculumService.findOne(curriculumId));
	}

	@Test(expected = IllegalArgumentException.class)
	public void deleteIncorrecto() {
		Curriculum curriculum;
		final int curriculumId = this.getEntityId("cursslum1");
		curriculum = this.curriculumService.findOne(curriculumId);
		Assert.notNull(curriculum);
		this.curriculumService.delete(curriculum);
		Assert.isNull(this.curriculumService.findOne(curriculumId));
	}

}
