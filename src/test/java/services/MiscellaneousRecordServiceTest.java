package services;

import java.util.Collection;


import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.MiscellaneousRecord;

import utilities.AbstractTest;



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

		final int idBusqueda = super.getEntityId("miselaneu");
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
	public void testSaveMiscellaneousRecordCorrecto() {
		MiscellaneousRecord mr;
		mr = this.miscellaneousRecordService.findOne(this.getEntityId("miscellaneousRecord1"));
		Assert.notNull(mr);
		mr = this.miscellaneousRecordService.save(mr);
		Assert.isTrue(mr.getTitle()== "Benhill County GaArchives Historical Records");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSaveMiscellaneousRecordIncorrecto() {
		MiscellaneousRecord mr;
		mr = this.miscellaneousRecordService.findOne(this.getEntityId("miscellaneousRecord1"));
		Assert.notNull(mr);
		mr = this.miscellaneousRecordService.save(mr);
		Assert.isTrue(mr.getTitle()== "uwu");
	}
}