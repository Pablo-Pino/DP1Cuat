
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
import domain.EducationRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class EducationRecordServiceTest extends AbstractTest {

	//Service under test ------------------------------------------

	@Autowired
	private EducationRecordService	educationRecordService;


	//test-------------------------------------------------------------------

	@Test
	public void createTestCorrecto() {
		final EducationRecord er = this.educationRecordService.create();
		Assert.notNull(er);
	}

	@Test
	public void saveTestCorrecto() {
		EducationRecord er, saved;
		final int erId = this.getEntityId("educationRecord1");
		er = this.educationRecordService.findOne(erId);
		Assert.notNull(er);

		er.setComments("nuevo comentario");
		saved = this.educationRecordService.save(er);
		Assert.isTrue(saved.getComments().equals("nuevo comentario"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void saveTestIncorrecto() {
		EducationRecord er;
		EducationRecord saved;
		final int erId = this.getEntityId("educationRecord1");
		er = this.educationRecordService.findOne(erId);
		Assert.notNull(er);

		er.setCurriculum(null);
		saved = this.educationRecordService.save(er);
		Assert.isNull(saved);
	}

	@Test
	public void testFindAllCorrecto() {
		Collection<EducationRecord> educationRecords;

		educationRecords = this.educationRecordService.findAll();
		Assert.notNull(educationRecords);
		Assert.notEmpty(educationRecords); //porque sabemos que hemos creado algunos con el populate
	}

	@Test
	public void testFindOneCorrecto() {
		EducationRecord res;
		final int resId = this.getEntityId("educationRecord1");
		res = this.educationRecordService.findOne(resId);
		Assert.notNull(res);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindOneIncorrecto() {
		EducationRecord res;
		final int resId = this.getEntityId("fallo");
		res = this.educationRecordService.findOne(resId);
		Assert.notNull(res);

	}

	@Test
	public void deleteTestCorrecto() {
		EducationRecord er;
		final int erId = this.getEntityId("educationRecord2");
		er = this.educationRecordService.findOne(erId);
		Assert.notNull(er);

		this.educationRecordService.delete(er);
		Assert.isNull(er = this.educationRecordService.findOne(erId));
	}

	@Test(expected = IllegalArgumentException.class)
	public void deleteTestIncorrecto() {
		EducationRecord er;
		final int erId = this.getEntityId("vaAFallar");
		er = this.educationRecordService.findOne(erId);
		Assert.notNull(er);

		this.educationRecordService.delete(er);
		Assert.isNull(er = this.educationRecordService.findOne(erId));
	}

}
