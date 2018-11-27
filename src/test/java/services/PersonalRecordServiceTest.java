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
import domain.PersonalRecord;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional

public class PersonalRecordServiceTest extends AbstractTest{
	
	//Service under test ----------------------------------------

	@Autowired
	private PersonalRecordService	personalRecordService;


	//------------------------------------------------------------

	@Test
	public void testCreate() {
		final PersonalRecord pr = this.personalRecordService.create();
		Assert.notNull(pr);
	}

	@Test
	public void testFindOneCorrecto() {
		PersonalRecord pr;
		final int idBusqueda = super.getEntityId("personalRecord1");
		pr = this.personalRecordService.findOne(idBusqueda);
		Assert.notNull(pr);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindOneIncorrecto() {
		PersonalRecord pr;

		final int idBusqueda = super.getEntityId("personareco");
		pr = this.personalRecordService.findOne(idBusqueda);
		Assert.isNull(pr);
	}

	@Test
	public void testFindAll() {
		Collection<PersonalRecord> personalRecords;

		personalRecords = this.personalRecordService.findAll();
		Assert.notNull(personalRecords);
		Assert.notEmpty(personalRecords);
	}

	@Test
	public void testSavePersonalRecordCorrecto() {
		PersonalRecord pr;
		pr = this.personalRecordService.findOne(this.getEntityId("personalRecord1"));
		Assert.notNull(pr);
		pr = this.personalRecordService.save(pr);
		Assert.isTrue(pr.getFullName()== "Antonio Grinch Papp");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSaveHandyworkerIncorrecto() {
		PersonalRecord pr;
		pr = this.personalRecordService.findOne(this.getEntityId("personalRecord1"));
		Assert.notNull(pr);
		pr = this.personalRecordService.save(pr);
		Assert.isTrue(pr.getFullName()== "uwu");

	}
	
	@Test
	public void testDelete() {
		PersonalRecord pr;

		pr = this.personalRecordService.findOne(super.getEntityId("personalRecord1"));
		this.personalRecordService.delete(pr);
		Assert.isNull(this.personalRecordService.findOne(pr.getId()));
	}


}