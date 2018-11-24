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
	
	public PersonalRecordService() {
		super();
	}
	// Simple CRUD methods
	
	public PersonalRecord create() {
		final PersonalRecord ft = new PersonalRecord();
		return ft;
	}
	
	public Collection<PersonalRecord> findAll() {
		
		Collection<PersonalRecord> pr;

		Assert.notNull(this.personalRecordRepository);
		pr = this.personalRecordRepository.findAll();
		Assert.notNull(pr);

		return pr;
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
		Assert.isTrue(p.getId()!=0);
		this.personalRecordRepository.delete(p);
	}
}