
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
import domain.Complaint;
import domain.Referee;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ComplaintServiceTest extends AbstractTest {

	//Service under test ----------------------------------------

	@Autowired
	private ComplaintService	complaintService;

	@Autowired
	private RefereeService		refereeService;


	//-------------Test----------------------------------

	@Test
	public void testCreate() {
		final Complaint a = this.complaintService.create();
		Assert.notNull(a);
	}

	@Test
	public void testFindOneCorrecto() {
		Complaint app;
		final int idBusqueda = super.getEntityId("complaint1");
		app = this.complaintService.findOne(idBusqueda);
		Assert.notNull(app);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindOneIncorrecto() {
		Complaint app;

		final int idBusqueda = super.getEntityId("COMPLAINT QUE NO EXISTE");
		app = this.complaintService.findOne(idBusqueda);
		Assert.isNull(app);
	}

	@Test
	public void testFindAll() {
		Collection<Complaint> complaints;

		complaints = this.complaintService.findAll();
		Assert.notNull(complaints);
		Assert.notEmpty(complaints); //porque sabemos que hemos creado algunos con el populate
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSaveComplaintIncorrecto() {
		Complaint complaint, saved;
		complaint = this.complaintService.findOne(this.getEntityId("coaint1"));
		Assert.notNull(complaint);
		complaint.setDescription("Esto es una descripcion normal");
		saved = this.complaintService.save(complaint);
		Assert.isTrue(saved.getDescription().equals("Esto es una descripcion normal"));

	}

	@Test
	public void testSaveComplaintCorrecto() {
		Complaint complaint, saved;
		complaint = this.complaintService.create();
		Assert.notNull(complaint);
		complaint.setDescription("Esto es una descripcion normal");
		saved = this.complaintService.save(complaint);
		Assert.isTrue(saved.getDescription().equals("Esto es una descripcion normal"));

	}

	@Test
	public void testFindAllComplaintsByReferee() {
		Collection<Complaint> complaints;
		final Referee r = this.refereeService.findOne(this.getEntityId("referee1"));
		complaints = this.complaintService.findAllComplaintsByReferee(r);
		Assert.notNull(complaints);
		Assert.notEmpty(complaints); //porque sabemos que hemos creado algunos con el populate
	}

	@Test
	public void testFindAllComplaintsWithoutReferee() {
		Collection<Complaint> complaints;
		complaints = this.complaintService.findAllComplaintsWithoutReferee();
		Assert.notNull(complaints);
		Assert.notEmpty(complaints); //porque sabemos que hemos creado algunos con el populate
	}
}
