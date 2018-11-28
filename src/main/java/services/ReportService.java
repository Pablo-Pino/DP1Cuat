
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ReportRepository;
import security.Authority;
import domain.Complaint;
import domain.Note;
import domain.Report;
import domain.Url;

@Service
@Transactional
public class ReportService extends GenericService<Report, ReportRepository> implements ServiceObjectDependantI<Report, Complaint> {

	@Autowired
	private ReportRepository	repository;

	@Autowired
	private ComplaintService	complaintService;
	@Autowired
	private ServiceUtils		serviceUtils;


	@Override
	public Collection<Report> findAll(final Complaint dependency) {
		this.serviceUtils.checkId(dependency);
		Assert.notNull(this.complaintService.findOne(dependency.getId()));
		return this.repository.findByComplaint(dependency.getId());
	}

	@Override
	public Report create(final Complaint dependency) {
		final Report res = new Report();
		res.setComplaint(dependency);
		res.setAttachments(new ArrayList<Url>());
		res.setDraft(true);
		res.setMoment(new Date(System.currentTimeMillis() - 1000));
		res.setNotes(new ArrayList<Note>());
		return res;
	}

	@Override
	public Report save(final Report object) {
		final Report report = super.checkObjectSave(object);
		if (object.getId() == 0) {
			this.serviceUtils.checkId(object.getComplaint());
			Assert.notNull(this.complaintService.findOne(object.getComplaint().getId()));
			object.setMoment(new Date(System.currentTimeMillis() - 1000));
			object.setNotes(new ArrayList<Note>());
		} else {
			Assert.isTrue(report.getDraft());
			object.setComplaint(report.getComplaint());
			object.setMoment(report.getMoment());
			object.setNotes(report.getNotes());
		}
		this.serviceUtils.checkActor(report.getComplaint().getReferee());
		this.serviceUtils.checkAuthority(Authority.REFEREE);
		final Report res = this.repository.save(object);
		return res;
	}

	@Override
	public void delete(final Report object) {
		final Report report = super.checkObject(object);
		Assert.isTrue(report.getDraft());
		this.serviceUtils.checkActor(report.getComplaint().getReferee());
		this.serviceUtils.checkAuthority(Authority.REFEREE);
		report.getComplaint().setReport(null);
		this.complaintService.save(report.getComplaint());
		this.repository.delete(report);
	}
	
	public void flush() {
		this.repository.flush();
	}
	
	public Map<String, Double> refeeReportStats() {
		final Double[] statistics = this.repository.refeeReportStats();
		final Map<String, Double> res = new HashMap<>();
		res.put("MIN", statistics[0]);
		res.put("MAX", statistics[1]);
		res.put("AVG", statistics[2]);
		res.put("STD", statistics[3]);

		return res;

	}

}
