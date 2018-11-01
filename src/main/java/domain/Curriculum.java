
package domain;

import java.util.Collection;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


public class Curriculum extends Ticketable {

	//------------Atributos----------

	

	//-----------Relaciones------------

	private HandyWorker							handyWorker;
	private PersonalRecord						personalRecord;
	private Collection<EducationRecord>			educationRecords;
	private Collection<ProfessionalRecord>		profesionalRecords;
	private Collection<EndorserRecord>			endorserRecords;
	private Collection<MiscellaneousRecord>		miscellaneousRecords;


	//----------Getters y Setters-------



	// Relationships

	@NotNull
	@Valid
	public HandyWorker getHandyWorker() {
		return this.handyWorker;
	}
	public void setHandyWorker(final HandyWorker handyWorker) {
		this.handyWorker = handyWorker;
	}

	@NotNull
	@Valid
	public PersonalRecord getPersonalRecord() {
		return this.personalRecord;
	}
	public void setPersonalRecord(final PersonalRecord personalRecord) {
		this.personalRecord = personalRecord;
	}

	@NotNull
	@Valid
	public Collection<EducationRecord> getEducationRecords() {
		return this.educationRecords;
	}

	public void setEducationRecords(final Collection<EducationRecord> educationRecords) {
		this.educationRecords = educationRecords;
	}

	@NotNull
	@Valid
	public Collection<ProfessionalRecord> getProfesionalRecords() {
		return this.profesionalRecords;
	}

	public void setProfesionalRecords(final Collection<ProfessionalRecord> profesionalRecords) {
		this.profesionalRecords = profesionalRecords;
	}

	@NotNull
	@Valid
	public Collection<EndorserRecord> getEndorserRecords() {
		return this.endorserRecords;
	}

	public void setEndorserRecords(final Collection<EndorserRecord> endorserRecords) {
		this.endorserRecords = endorserRecords;
	}

	@NotNull
	@Valid
	public Collection<MiscellaneousRecord> getMiscellaneousRecords() {
		return this.miscellaneousRecords;
	}

	public void setMiscellaneousRecords(final Collection<MiscellaneousRecord> miscellaneousRecords) {
		this.miscellaneousRecords = miscellaneousRecords;
	}

}
