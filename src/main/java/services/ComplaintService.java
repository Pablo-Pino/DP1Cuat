
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ComplaintRepository;
import domain.Complaint;
import domain.Referee;

@Service
@Transactional
public class ComplaintService {

	//Managed Repository

	@Autowired
	private ComplaintRepository	complaintRepository;
	//Supporting Service
	@Autowired
	public TicketableService	ticketableService;

	@Autowired
	private ServiceUtils		serviceUtils;


	// Simple CRUD methods
	//un complaint tiene un ticker
	public Complaint create() {
		Complaint res;
		res = new Complaint();
		res.setTicker(this.ticketableService.createTicker());
		res.setMoment(new Date(System.currentTimeMillis() - 1000));

		return res;
	}

	public Collection<Complaint> findAll() {
		return this.complaintRepository.findAll();
	}

	public Complaint findOne(final int complaintId) {
		return this.complaintRepository.findOne(complaintId);
	}

	public Complaint save(final Complaint complaint) {
		Assert.notNull(complaint);
		//comprobamos que su id no sea negativa por motivos de seguridad
		this.serviceUtils.checkIdSave(complaint);
		//Si el admin que estamos guardando es nuevo (no está en la base de datos) le ponemos todos sus atributos vacíos
		if (complaint.getId() == 0) {
			Complaint res;
			//le meto al resultado final el admin que he ido modificando anteriormente
			res = this.complaintRepository.save(complaint);
			return res;

		} else
			throw new IllegalArgumentException("Una vez añadido a la base de datos no se puede modificar la queja ");

	}

	//Una vez guardados en la base de datos ,una complaint no se puede ni actualizar  ni eliminar 

	//----------------- Other business methods----------------------------------
	public Collection<Complaint> findAllComplaintsByReferee(final Referee r) {
		return this.complaintRepository.SearchComplaintByReferee(r.getId());
	}

	public Collection<Complaint> findAllComplaintsWithoutReferee() {
		return this.complaintRepository.SearchComplaintWithoutReferee();
	}
}
