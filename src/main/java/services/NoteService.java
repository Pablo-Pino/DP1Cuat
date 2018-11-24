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

@Service
@Transactional
public class NoteService {
	
	//Managed Repository

	@Autowired
	private NoteRepository noteRepository;

	// Supporting Service
	
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
		return this.noteRepository.findOne(noteId);
	}
	
	public Note save(final Note n) {
		Assert.notNull(n);
		Date moment;
		moment = new Date();
		Assert.isTrue(n.getMoment().after(moment));
		return this.noteRepository.save(n);
	}

	public void delete(final Note n) {
		Assert.notNull(n);
		Assert.isTrue(n.getId()!=0);
		noteRepository.delete(n);
	}

}
