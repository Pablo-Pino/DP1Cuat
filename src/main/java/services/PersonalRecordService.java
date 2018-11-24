package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PersonalRecordRepository;
import domain.PersonalRecord;

@Service
@Transactional
public class PersonalRecordService {
	
	//Managed Repository

	@Autowired
	private PersonalRecordRepository personalRecordRepository;

	// Supporting Service
	
	
	// Simple CRUD methods
	
	public PersonalRecord create() {
		final PersonalRecord ft = new PersonalRecord();
		return ft;
	}
	
	public Collection<PersonalRecord> findAll() {
		return this.personalRecordRepository.findAll();
	}

	public PersonalRecord findOne(final int personalRecordId) {
		return this.personalRecordRepository.findOne(personalRecordId);
	}
	
	public PersonalRecord save(final PersonalRecord p) {
		Assert.notNull(p);
		return this.personalRecordRepository.save(p);
	}

	public void delete(final PersonalRecord p) {
		Assert.notNull(p);
		this.personalRecordRepository.delete(p);
	}
}