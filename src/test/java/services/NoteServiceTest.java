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
import domain.Note;
import domain.Report;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional

public class NoteServiceTest extends AbstractTest{
	
	//Service under test ----------------------------------------

	@Autowired
	private NoteService	noteService;
	
	//Supporting Services
	@Autowired
	private ReportService reportService;


	//------------------------------------------------------------
	@Test
	public void testCreate() {
		final Note n = this.noteService.create();
		Assert.notNull(n);
	}

	@Test
	public void testFindOneCorrecto() {
		Note n;
		final int idBusqueda = super.getEntityId("note1");
		n = this.noteService.findOne(idBusqueda);
		Assert.notNull(n);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindOneIncorrecto() {
		Note n;
		final int idBusqueda = super.getEntityId("notah");
		n = this.noteService.findOne(idBusqueda);
		Assert.isNull(n);
	}

	@Test
	public void testFindAll() {
		Collection<Note> notes;

		notes = this.noteService.findAll();
		Assert.notNull(notes);
		Assert.notEmpty(notes);
	}

	@Test
	public void saveTestCorrecto() {
		Note n, saved;
		final int nId = this.getEntityId("note1");
		n = this.noteService.findOne(nId);
		Assert.notNull(n);
		int reportId =this.getEntityId("report1");
		final Report report = this.reportService.findOne(reportId);

		n.setReport(report);
		saved = this.noteService.save(n);
		Assert.isTrue(saved.getReport().equals(report));
	}

	@Test(expected = IllegalArgumentException.class)
	public void saveTestIncorrecto() {
		Note n;
		Note saved;
		final int nId = this.getEntityId("note1");
		n = this.noteService.findOne(nId);
		Assert.notNull(n);

		n.setMoment(null);
		saved = this.noteService.save(n);
		Assert.isNull(saved);
	}
	
	@Test
	public void deleteTestCorrecto() {
		Note n;
		final int nId = this.getEntityId("note2");
		n = this.noteService.findOne(nId);
		Assert.notNull(n);

		this.noteService.delete(n);
		Assert.isNull(n = this.noteService.findOne(nId));
	}

	@Test(expected = IllegalArgumentException.class)
	public void deleteTestIncorrecto() {
		Note n;
		final int nId = this.getEntityId("Error intencionado");
		n = this.noteService.findOne(nId);
		Assert.notNull(n);

		this.noteService.delete(n);
		Assert.isNull(n = this.noteService.findOne(nId));
	}


}