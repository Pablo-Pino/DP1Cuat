
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.EndorserRecordRepository;
import domain.EndorserRecord;

@Transactional
@Service
public class EndorserRecordService {

	//---------------Managed Repository---------------------------------------

	@Autowired
	private EndorserRecordRepository	endorserRecordRepository;


	//---------------Supporting Services----------------------------------------

	//------------------Simple CRUD methods----------------------------------
	public EndorserRecord create() {
		EndorserRecord res;
		res = new EndorserRecord();
		return res;
	}

	public Collection<EndorserRecord> findAll() {
		Collection<EndorserRecord> res;
		res = this.endorserRecordRepository.findAll();
		return res;
	}
	public EndorserRecord findOne(final int endorserRecordId) {
		EndorserRecord res;
		res = this.endorserRecordRepository.findOne(endorserRecordId);
		return res;
	}

	public EndorserRecord save(final EndorserRecord endorserRecord) {
		Assert.notNull(endorserRecord);
		EndorserRecord res;
		res = this.endorserRecordRepository.save(endorserRecord);
		return res;
	}

	public void delete(final EndorserRecord endorserRecord) {
		Assert.notNull(endorserRecord);
		this.endorserRecordRepository.delete(endorserRecord);
	}
}
