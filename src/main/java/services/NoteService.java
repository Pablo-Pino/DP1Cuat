package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.NoteRepository;

import domain.Note;
import domain.Report;

@Service
@Transactional
public class NoteService {
	
	//Managed Repository

	@Autowired
	private NoteRepository noteRepository;

	// Supporting Service
	private ReportService reportService;
	
	//Contrusctor
	
	public NoteService() {
		super();
	}
	// Simple CRUD methods
	
	public Note create() {
		final Note n = new Note();
		n.setComments(new ArrayList<String>());
		n.setMoment(new Date(System.currentTimeMillis() - 1000));
		return n;
	}
	
	public Collection<Note> findAll() {
		Collection<Note> n;

		Assert.notNull(this.noteRepository);
		n = this.noteRepository.findAll();
		Assert.notNull(n);

		return n;
	}

	public Note findOne(final int noteId) {
		Assert.notNull(this);
		return this.noteRepository.findOne(noteId);
	}
	
	public Note save(final Note n) {
		
		Note guardado;
		Report report;
		Date moment;
		long millis;
		millis = System.currentTimeMillis() -1000;
		moment = new Date(millis);
		
		Assert.notNull(n);
		n.setMoment(moment);
		
		guardado= noteRepository.save(n);
		
		report = guardado.getReport();
		report.getNotes().add(guardado);
		reportService.save(report);
		
		Assert.isTrue(guardado.getMoment().before(moment));
		return guardado;
	}

	public void delete(final Note n) {
		Assert.notNull(n);
		Assert.isTrue(n.getId()!=0);
		noteRepository.delete(n);
	}

}
