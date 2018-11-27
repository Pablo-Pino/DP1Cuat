
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
import domain.EndorserRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class EndorserRecordServiceTest extends AbstractTest {

	//Service under test ------------------------------------------

	@Autowired
	private EndorserRecordService	endorserRecordService;


	//test-------------------------------------------------------------------

	@Test
	public void createTestCorrecto() {
		final EndorserRecord er = this.endorserRecordService.create();
		Assert.notNull(er);
	}

	@Test
	public void saveTestCorrecto() {
		EndorserRecord er, saved;
		final int erId = this.getEntityId("endorserRecord1");
		er = this.endorserRecordService.findOne(erId);
		Assert.notNull(er);

		er.setFullName("Nombre cambiado para test");
		saved = this.endorserRecordService.save(er);
		Assert.isTrue(saved.getFullName().equals("Nombre cambiado para test"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void saveTestIncorrecto() {
		EndorserRecord er;
		EndorserRecord saved;
		final int erId = this.getEntityId("endorserRecord1");
		er = this.endorserRecordService.findOne(erId);
		Assert.notNull(er);

		er.setCurriculum(null);
		saved = this.endorserRecordService.save(er);
		Assert.isNull(saved);
	}

	@Test
	public void testFindAllCorrecto() {
		Collection<EndorserRecord> endorserRecords;

		endorserRecords = this.endorserRecordService.findAll();
		Assert.notNull(endorserRecords);
		Assert.notEmpty(endorserRecords); //porque sabemos que hemos creado algunos con el populate
	}

	@Test
	public void testFindOneCorrecto() {
		EndorserRecord res;
		final int resId = this.getEntityId("endorserRecord1");
		res = this.endorserRecordService.findOne(resId);
		Assert.notNull(res);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindOneIncorrecto() {
		EndorserRecord res;
		final int resId = this.getEntityId("fallo");
		res = this.endorserRecordService.findOne(resId);
		Assert.notNull(res);

	}

	@Test
	public void deleteTestCorrecto() {
		EndorserRecord er;
		final int erId = this.getEntityId("endorserRecord2");
		er = this.endorserRecordService.findOne(erId);
		Assert.notNull(er);

		this.endorserRecordService.delete(er);
		Assert.isNull(er = this.endorserRecordService.findOne(erId));
	}

	@Test(expected = IllegalArgumentException.class)
	public void deleteTestIncorrecto() {
		EndorserRecord er;
		final int erId = this.getEntityId("vaAFallar");
		er = this.endorserRecordService.findOne(erId);
		Assert.notNull(er);

		this.endorserRecordService.delete(er);
		Assert.isNull(er = this.endorserRecordService.findOne(erId));
	}

}
