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
import domain.MiscellaneousRecord;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional

public class MiscellaneousRecordServiceTest extends AbstractTest{
	
	//Service under test ----------------------------------------

	@Autowired
	private MiscellaneousRecordService	miscellaneousRecordService;


	//------------------------------------------------------------

	@Test
	public void testCreate() {
		final MiscellaneousRecord mr = this.miscellaneousRecordService.create();
		Assert.notNull(mr);
	}

	@Test
	public void testFindOneCorrecto() {
		MiscellaneousRecord mr;
		final int idBusqueda = super.getEntityId("miscellaneousRecord1");
		mr = this.miscellaneousRecordService.findOne(idBusqueda);
		Assert.notNull(mr);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindOneIncorrecto() {
		MiscellaneousRecord mr;
		final int idBusqueda = super.getEntityId("miselaniu");
		mr = this.miscellaneousRecordService.findOne(idBusqueda);
		Assert.isNull(mr);
	}

	@Test
	public void testFindAll() {
		Collection<MiscellaneousRecord> miscellaneousRecords;

		miscellaneousRecords = this.miscellaneousRecordService.findAll();
		Assert.notNull(miscellaneousRecords);
		Assert.notEmpty(miscellaneousRecords);
	}

	@Test
	public void saveTestCorrecto() {
		MiscellaneousRecord mr, saved;
		final int mrId = this.getEntityId("miscellaneousRecord1");
		mr = this.miscellaneousRecordService.findOne(mrId);
		Assert.notNull(mr);

		mr.setComments("prueba fallo");
		saved = this.miscellaneousRecordService.save(mr);
		Assert.isTrue(saved.getComments().contains("prueba fallo"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void saveTestIncorrecto() {
		MiscellaneousRecord pr;
		MiscellaneousRecord saved;
		final int mrId = this.getEntityId("miscellaneousRecord1");
		pr = this.miscellaneousRecordService.findOne(mrId);
		Assert.notNull(pr);

		pr.setCurriculum(null);
		saved = this.miscellaneousRecordService.save(pr);
		Assert.isNull(saved);
	}
	
	@Test
	public void deleteTestCorrecto() {
		MiscellaneousRecord mr;
		final int mrId = this.getEntityId("miscellaneousRecord2");
		mr = this.miscellaneousRecordService.findOne(mrId);
		Assert.notNull(mr);

		this.miscellaneousRecordService.delete(mr);
		Assert.isNull(mr = this.miscellaneousRecordService.findOne(mrId));
	}

	@Test(expected = IllegalArgumentException.class)
	public void deleteTestIncorrecto() {
		MiscellaneousRecord mr;
		final int mrId = this.getEntityId("Error intencionado");
		mr = this.miscellaneousRecordService.findOne(mrId);
		Assert.notNull(mr);

		this.miscellaneousRecordService.delete(mr);
		Assert.isNull(mr = this.miscellaneousRecordService.findOne(mrId));
	}



}