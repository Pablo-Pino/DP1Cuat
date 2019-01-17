
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
import domain.Curriculum;
import domain.ProfessionalRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ProfessionalRecordServiceTest extends AbstractTest {

	// Services

	@Autowired
	private ProfessionalRecordService	professionalRecordService;
	@Autowired
	private CurriculumService			curriculumService;


	// Tests

	public void findOneProfessionalRecord(final Integer profesionalRecordId, final Class<?> expected) {
		Class<?> caught = null;
		try {
			this.professionalRecordService.findOne(profesionalRecordId);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	public void findAllProfessionalRecord(final Collection<Integer> professionalRecordIds, final Class<?> expected) {
		Class<?> caught = null;
		try {
			this.professionalRecordService.findAll(professionalRecordIds);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	public void findAllProfessionalRecord(final Class<?> expected) {
		Class<?> caught = null;
		try {
			this.professionalRecordService.findAll();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	public void findByCurriculumProfessionalRecord(final Curriculum curriculum, final Class<?> expected) {
		Class<?> caught = null;
		try {
			final Collection<ProfessionalRecord> professionalRecords = this.professionalRecordService.findAll(curriculum);
			Assert.isTrue(curriculum.getProfessionalRecords().size() == professionalRecords.size());
			for (final ProfessionalRecord p : professionalRecords)
				Assert.isTrue(curriculum.getProfessionalRecords().contains(p));
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	public void createProfessionalRecord(final String username, final Integer curriculumId, final Class<?> expected) {
		Class<?> caught = null;
		try {
			this.authenticate(username);
			final Curriculum curriculum = this.curriculumService.findOne(curriculumId);
			final ProfessionalRecord professionalRecord = this.professionalRecordService.create(curriculum);
			Assert.notNull(professionalRecord);
			Assert.isTrue(professionalRecord.getCurriculum().equals(curriculum));
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	public void saveProfessionalRecord(final String username, final String attachment, final String comments, final String company, final String role, final Date start, final Date end, final Integer professionalRecordId, final Integer curriculumId,
		final Class<?> expected) {
		Class<?> caught = null;
		try {
			this.authenticate(username);
			final Curriculum curriculum = this.curriculumService.findOne(curriculumId);
			Curriculum oldCurriculum = null;
			ProfessionalRecord professionalRecord = null;
			if (professionalRecordId == null)
				professionalRecord = this.professionalRecordService.create(curriculum);
			else {
				professionalRecord = this.professionalRecordService.findOne(professionalRecordId);
				oldCurriculum = professionalRecord.getCurriculum();
			}
			professionalRecord.setAttachment(attachment);
			professionalRecord.setComments(comments);
			professionalRecord.setCompany(company);
			professionalRecord.setEnd(end);
			professionalRecord.setRole(role);
			professionalRecord.setStart(start);
			professionalRecord.setCurriculum(curriculum);
			final ProfessionalRecord savedProfessionalRecord = this.professionalRecordService.save(professionalRecord);
			this.professionalRecordService.flush();
			Assert.isTrue(savedProfessionalRecord.getAttachment().equals(attachment));
			Assert.isTrue(savedProfessionalRecord.getComments().equals(comments));
			Assert.isTrue(savedProfessionalRecord.getCompany().equals(company));
			Assert.isTrue(savedProfessionalRecord.getEnd().equals(end));
			Assert.isTrue(savedProfessionalRecord.getRole().equals(role));
			Assert.isTrue(savedProfessionalRecord.getStart().equals(start));
			if (professionalRecordId == null)
				Assert.isTrue(savedProfessionalRecord.getCurriculum().equals(curriculum));
			else
				Assert.isTrue(savedProfessionalRecord.getCurriculum().equals(oldCurriculum));
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	public void deleteProfessionalRecord(final String username, final Integer professionalRecordId, final Class<?> expected) {
		Class<?> caught = null;
		try {
			this.authenticate(username);
			final ProfessionalRecord professionalRecord = this.professionalRecordService.findOne(professionalRecordId);
			this.professionalRecordService.delete(professionalRecord);
			this.professionalRecordService.flush();
			Assert.isNull(this.professionalRecordService.findOne(professionalRecordId));
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void testFindOneProfessionalRecord() {
		this.findOneProfessionalRecord(super.getEntityId("professionalRecord1"), null);
	}

	@Test
	public void testFindOneProfessionalRecordNullId() {
		this.findOneProfessionalRecord(null, IllegalArgumentException.class);
	}

	@Test
	public void testFindAllByIdsProfessionalRecord() {
		this.findAllProfessionalRecord(Arrays.asList(new Integer[] {
			super.getEntityId("professionalRecord1")
		}), null);
	}

	@Test
	public void testFindAllByIdsProfessionalRecordNullIds() {
		this.findAllProfessionalRecord(null, IllegalArgumentException.class);
	}

	@Test
	public void testFindAllProfessionalRecord() {
		this.findAllProfessionalRecord(null);
	}

	@Test
	public void testFindAllByCurriculumProfessionalRecord() {
		final Curriculum curriculum = this.curriculumService.findOne(super.getEntityId("curriculum1"));
		this.findByCurriculumProfessionalRecord(curriculum, null);
	}

	// En este caso se genera un NullPointerException debido a que al buscar el Curriculum, se llama al
	// metodo getId() de Curriculum, y al ser este nulo se provoca un NullPointerException
	@Test
	public void testFindAllByCurriculumProfessionalRecordNullCurriculum() {
		this.findByCurriculumProfessionalRecord(null, NullPointerException.class);
	}

	@Test
	public void testSaveProfessionalRecord() {
		final Integer curriculumId = super.getEntityId("curriculum1");
		this.saveProfessionalRecord("handyWorker1", "http://coso", "muy bueno", "la del anillo", "hobbit", new Date(System.currentTimeMillis() - 1000), new Date(System.currentTimeMillis() + 1000), null, curriculumId, null);
	}

	@Test
	public void testSaveProfessionalRecordUnauthenticated() {
		final Integer curriculumId = super.getEntityId("curriculum1");
		this.saveProfessionalRecord(null, "http://coso", "muy bueno", "la del anillo", "hobbit", new Date(System.currentTimeMillis() - 1000), new Date(System.currentTimeMillis() + 1000), null, curriculumId, IllegalArgumentException.class);
	}

	@Test
	public void testUpdateProfessionalRecord() {
		final Integer curriculumId = super.getEntityId("curriculum1");
		this.saveProfessionalRecord("handyWorker1", "http://coso", "muy bueno", "la del anillo", "hobbit", new Date(System.currentTimeMillis() - 1000), new Date(System.currentTimeMillis() + 1000), super.getEntityId("professionalRecord1"), curriculumId,
			null);
	}

	@Test
	public void testUpdateProfessionalRecordUnauthenticated() {
		final Integer curriculumId = super.getEntityId("curriculum1");
		this.saveProfessionalRecord(null, "http://coso", "muy bueno", "la del anillo", "hobbit", new Date(System.currentTimeMillis() - 1000), new Date(System.currentTimeMillis() + 1000), super.getEntityId("professionalRecord1"), curriculumId,
			IllegalArgumentException.class);
	}

	@Test
	public void testDeleteProfessionalRecord() {
		this.deleteProfessionalRecord("handyWorker1", super.getEntityId("professionalRecord1"), null);
	}

	@Test
	public void testDeleteProfessionalRecordUnauthenticated() {
		this.deleteProfessionalRecord(null, super.getEntityId("professionalRecord1"), IllegalArgumentException.class);
	}

}
