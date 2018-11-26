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
	public void testSaveNoteCorrecto() {
		Note n;
		Report report;
		Note guardado;
		Collection<Note> notes;
		n = noteService.create();
		report = reportService.findOne(89322);
		
		n.setReport(report);
		
		guardado = noteService.save(n);
		
		notes = noteService.findAll();
		Assert.isTrue(notes.contains(guardado));
		
//		n = this.noteService.findOne(this.getEntityId("note1"));
//		Assert.notNull(n);
//		n = this.noteService.save(n);
//		Assert.isTrue(n.getComments().contains("Es un comentario de la nota 1"));

	}

	@Test(expected = IllegalArgumentException.class)
	public void testSaveHandyworkerIncorrecto() {
		Note n;
		n = this.noteService.findOne(this.getEntityId("handyWorker1"));
		Assert.notNull(n);
		n = this.noteService.save(n);
		Assert.isTrue(n.getComments().contains("Es un comentario de la nota 2"));

	}
	
	@Test
	public void testDelete() {
		Note n;

		n = this.noteService.findOne(super.getEntityId("note1"));
		this.noteService.delete(n);
		Assert.isNull(this.noteService.findOne(n.getId()));
	}


}