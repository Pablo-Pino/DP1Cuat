
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Complaint;
import domain.Note;
import domain.Report;
import domain.Url;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ReportServiceTest extends AbstractTest {

	// Services

	@Autowired
	private ReportService		reportService;
	@Autowired
	private ComplaintService	complaintService;
	@Autowired
	private NoteService			noteService;


	// Tests

	public void findOneReport(final Integer reportId, final Class<?> expected) {
		Class<?> caught = null;
		try {
			this.reportService.findOne(reportId);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	public void findAllReport(final Collection<Integer> reportIds, final Class<?> expected) {
		Class<?> caught = null;
		try {
			this.reportService.findAll(reportIds);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	public void findAllReport(final Class<?> expected) {
		Class<?> caught = null;
		try {
			this.reportService.findAll();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	public void findByComplaintReport(final Complaint complaint, final Class<?> expected) {
		Class<?> caught = null;
		try {
			final Collection<Report> reports = this.reportService.findAll(complaint);
			Assert.isTrue(1 == reports.size());
			Assert.isTrue(reports.contains(this.reportService.findByComplaint(complaint)));
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}
	public void createReport(final String username, final Integer complaintId, final Class<?> expected) {
		Class<?> caught = null;
		try {
			this.authenticate(username);
			final Complaint complaint = this.complaintService.findOne(complaintId);
			final Report report = this.reportService.create(complaint);
			Assert.notNull(report);
			Assert.isTrue(report.getComplaint().equals(complaint));
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	public void saveReport(final String username, final Collection<Url> attachments, final String description, final boolean draft, final Date moment, final Collection<Note> notes, final Integer reportId, final Integer complaintId, final Class<?> expected) {
		Class<?> caught = null;
		try {
			this.authenticate(username);
			final Complaint complaint = this.complaintService.findOne(complaintId);
			Complaint oldComplaint = null;
			Report report = null;
			Date oldMoment = null;
			Collection<Note> oldNotes = null;
			if (reportId == null)
				report = this.reportService.create(complaint);
			else {
				report = this.reportService.findOne(reportId);
				oldComplaint = report.getComplaint();
				oldMoment = report.getMoment();
				oldNotes = this.noteService.findByReport(report);
			}
			report.setDescription(description);
			report.setAttachments((List<Url>) attachments);
			report.setComplaint(oldComplaint);
			report.setDescription(description);
			report.setDraft(draft);
			report.setMoment(moment);
			report.setComplaint(complaint);
			final Report savedReport = this.reportService.save(report);
			this.reportService.flush();
			Assert.isTrue(savedReport.getDescription().equals(description));
			Assert.isTrue(savedReport.getAttachments().size() == attachments.size());
			for (final Url a : savedReport.getAttachments())
				Assert.isTrue(attachments.contains(a));
			Assert.isTrue(savedReport.getDraft() == draft);
			Assert.isTrue(savedReport.getComplaint().equals(complaint));
			if (reportId == null) {
				Assert.isTrue(savedReport.getComplaint().equals(complaint));
				Assert.isTrue(this.noteService.findByReport(savedReport).isEmpty());
			} else {
				Assert.isTrue(savedReport.getComplaint().equals(oldComplaint));
				Assert.isTrue(savedReport.getMoment().equals(oldMoment));
				Assert.isTrue(this.noteService.findByReport(savedReport).equals(oldNotes));
			}
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	public void deleteReport(final String username, final Integer reportId, final Class<?> expected) {
		Class<?> caught = null;
		try {
			this.authenticate(username);
			final Report report = this.reportService.findOne(reportId);
			this.reportService.delete(report);
			this.reportService.flush();
			Assert.isNull(this.reportService.findOne(reportId));
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			//	this.checkExceptions(expected, caught);
		}

	}

	public void refeeReportStats(final String username, final Class<?> expected) {
		Class<?> caught = null;
		try {
			this.authenticate(username);
			this.reportService.refeeReportStats();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void testFindOneReport() {
		this.findOneReport(super.getEntityId("report1"), null);
	}

	@Test
	public void testFindOneReportNullId() {
		this.findOneReport(null, IllegalArgumentException.class);
	}

	@Test
	public void testFindAllByIdsReport() {
		this.findAllReport(Arrays.asList(new Integer[] {
			super.getEntityId("report1"), super.getEntityId("report2")
		}), null);
	}

	@Test
	public void testFindAllByIdsReportNullIds() {
		this.findAllReport(null, IllegalArgumentException.class);
	}

	@Test
	public void testFindAllReport() {
		this.findAllReport(null);
	}

	@Test
	public void testFindAllByComplaintReport() {
		final Complaint complaint = this.complaintService.findOne(super.getEntityId("complaint1"));
		this.findByComplaintReport(complaint, null);
	}

	// En este caso se genera un NullPointerException debido a que al buscar el Complaint, se llama al
	// metodo getId() de Complaint, y al ser este nulo se provoca un NullPointerException
	@Test
	public void testFindAllByComplaintReportNullComplaint() {
		this.findByComplaintReport(null, NullPointerException.class);
	}

	@Test
	public void testSaveReport() {
		final Url attachment = new Url();
		attachment.setUrl("http://attach");
		final Collection<Url> attachments = Arrays.asList(attachment);
		this.saveReport("referee1", attachments, "desc", true, new Date(System.currentTimeMillis()), null, null, this.getEntityId("complaint3"), null);
	}

	@Test
	public void testSaveReportUnauthenticated() {
		final Report report = this.reportService.findOne(this.getEntityId("report1"));
		this.saveReport(null, report.getAttachments(), "desc", true, new Date(System.currentTimeMillis()), null, null, this.getEntityId("complaint2"), IllegalArgumentException.class);
	}

	@Test
	public void testUpdateReport() {
		final Report report = this.reportService.findOne(this.getEntityId("report1"));
		final Url attachment = new Url();
		attachment.setUrl("http://attach");
		final Collection<Url> attachments = new ArrayList<Url>();
		attachments.add(attachment);
		final Collection<Note> notes = this.noteService.findByReport(report);
		this.saveReport("referee1", attachments, "desc", true, report.getMoment(), notes, this.getEntityId("report1"), this.getEntityId("complaint1"), null);
	}

	@Test
	public void testUpdateReportUnauthenticated() {
		final Report report = this.reportService.findOne(this.getEntityId("report1"));
		this.saveReport(null, report.getAttachments(), "desc", true, new Date(System.currentTimeMillis()), null, this.getEntityId("report1"), this.getEntityId("complaint2"), IllegalArgumentException.class);
	}

	@Test
	public void testDeleteReport() {
		this.deleteReport("referee1", super.getEntityId("report1"), null);
	}

	@Test
	public void testDeleteReportUnauthenticated() {
		this.deleteReport(null, super.getEntityId("report1"), IllegalArgumentException.class);
	}

	@Test
	public void testRefeeReportStats() {
		this.refeeReportStats("admin1", null);
	}

	@Test
	public void testRefeeReportStatsUnauthenticated() {
		this.refeeReportStats(null, IllegalArgumentException.class);
	}

}
