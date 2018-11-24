
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MiscellaneousRecordRepository;
import domain.MiscellaneousRecord;

@Service
@Transactional
public class MiscellaneousRecordService {

	//Managed Repository

	@Autowired
	private MiscellaneousRecordRepository	miscellaneousRecordRepository;


	// Supporting Service

	public MiscellaneousRecordService() {
		super();
	}

	// Simple CRUD methods
	public MiscellaneousRecord create() {
		final MiscellaneousRecord mr = new MiscellaneousRecord();
		return mr;
	}

	public Collection<MiscellaneousRecord> findAll() {
		return this.miscellaneousRecordRepository.findAll();
	}

	public Collection<MiscellaneousRecord> findAll(final int miscellaneousRecordId) {
		Collection<MiscellaneousRecord> mr;

		mr = this.miscellaneousRecordRepository.findAll();
		Assert.notNull(mr);

		return mr;
	}

	public MiscellaneousRecord findOne(final int miscellaneousRecordId) {
		Assert.notNull(this);
		return this.miscellaneousRecordRepository.findOne(miscellaneousRecordId);

	}
	public MiscellaneousRecord save(final MiscellaneousRecord mr) {
		Assert.notNull(mr);
		return this.miscellaneousRecordRepository.save(mr);
	}

	public void delete(final MiscellaneousRecord mr) {
		Assert.notNull(mr);
		Assert.isTrue(mr.getId() != 0);
		this.miscellaneousRecordRepository.delete(mr);
	}
}
