
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import security.LoginService;
import domain.Application;
import domain.Customer;
import domain.FixupTask;
import domain.Folder;
import domain.HandyWorker;
import domain.Message;

@Service
@Transactional
public class ApplicationService {

	//Managed Repository

	@Autowired
	private ApplicationRepository	applicationRepository;

	// Supporting Service

	@Autowired
	private FolderService			folderService;
	@Autowired
	private MessageService			messageService;
	@Autowired
	private HandyWorkerService		handyWorkerService;
	@Autowired
	private FixupTaskService		fixupTaskService;

	@Autowired
	private ActorService			actorService;


	// Constructors
	public ApplicationService() {
		super();
	}

	// Simple CRUD methods

	public Application create() {
		final Application a = new Application();
		HandyWorker hw;
		hw = (HandyWorker) this.actorService.findOneByUserAccount(LoginService.getPrincipal());
		a.setHandyWorker(hw);
		a.setStatus("PENDING");
		a.setMoment(new Date(System.currentTimeMillis() - 1000));
		return a;
	}

	public Collection<Application> findAll() {
		return this.applicationRepository.findAll();
	}

	public Application findOne(final int applicationId) {
		return this.applicationRepository.findOne(applicationId);
	}

	public Application save(final Application app) {
		System.out.println("Entra en el save");
		//Assert.notNull(app);
		System.out.println("Sale del not null");

		Application res;
		//compruebo si esa fixuptask tiene una app accepted si no la tiene lo guardo
		final FixupTask f = app.getFixupTask();
		if (this.getTieneYaACCEPTED(f)) {
			System.out.println("Entra en el 1º if");

			throw new IllegalArgumentException("Only one application among all the applications for a fixup task can be accepted");
			//
		}
		if (app.getStatus().equals("ACCEPTED")) {
			System.out.println("Entra en el 2º if");

			//cuando cambia a accepted una creditcard valida se debe dar
			//si se ha cambiado a accepted manda un mensaje al handyworker y al customer avisandoles
			Assert.notNull(app.getHandyWorker());
			Assert.notNull(app.getFixupTask().getCustomer());
			this.NotificationMessage(app.getHandyWorker(), app.getFixupTask().getCustomer());

		}
		System.out.println("Se dispone a guardar ");
		res = this.applicationRepository.save(app);
		System.out.println("sale del save");

		return res;
	}
	private boolean getTieneYaACCEPTED(final FixupTask f) {
		Boolean res = false;
		for (final Application a : this.findAll(f))
			if (a.getStatus().equals("ACCEPTED"))
				res = true;
		return res;
	}

	//-------- Una application no se debe borrar solo cambiar su estado----------------

	//-----------------Other Methods----------------------------------

	public Map<String, Double> applicationPriceStats() {
		final Double[] statistics = this.applicationRepository.applicationPriceStats();
		final Map<String, Double> res = new HashMap<>();

		res.put("MIN", statistics[0]);
		res.put("MAX", statistics[1]);
		res.put("AVG", statistics[2]);
		res.put("STD", statistics[3]);

		return res;
	}

	public Map<String, Double> pendingRatio() {
		final Double ratio = this.applicationRepository.pendingRatio();
		final Map<String, Double> res = new HashMap<>();

		res.put("Ratio", ratio);

		return res;
	}

	public Map<String, Double> acceptedRatio() {
		final Double ratio = this.applicationRepository.acceptedRatio();
		final Map<String, Double> res = new HashMap<>();

		res.put("Ratio", ratio);

		return res;
	}

	public Map<String, Double> appsRejectedRatio() {
		final Double ratio = this.applicationRepository.acceptedRatio();
		final Map<String, Double> res = new HashMap<>();

		res.put("Ratio", ratio);

		return res;
	}

	public Double lateApplicationsRatio() {
		final Double ratio = this.applicationRepository.lateApplicationsRatio();

		return ratio;
	}

	public Application changeStatus(final Application a, final String status) {
		Assert.notNull(a);
		Assert.notNull(status);
		if (a.getStatus().equals("PENDING") && (status.equals("ACCEPTED") || status.equals("REJECTED"))) {
			a.setStatus(status);
			this.save(a);
		} else
			System.out.println("No se puede cambiar a ese status");

		this.save(a);
		return a;
	}

	public void NotificationMessage(final HandyWorker hw, final Customer c) {
		Folder res = null;
		for (final Folder f : this.folderService.findAllByActor(hw))
			if (f.getName().equals("inBox"))
				res = f;
		final Message m1 = this.messageService.create(res);
		m1.setReceiver(hw);

		m1.setSender(c);
		m1.setBody("Este es un mensaje de notificación");
		m1.setSubject("Cambio en Application");
		m1.setPriority("HIGH");

		this.messageService.save(m1);
		//res.getMessages().add(m1);
		//hw.getReceivedMessages().add(m1);
		//c.getSendedMessages().add(m1);
		//this.folderService.save(res);
		for (final Folder f : this.folderService.findAllByActor(c))
			if (f.getName().equals("inBox"))
				res = f;
		final Message m2 = this.messageService.create(res);

		m2.setReceiver(c);

		m2.setSender(hw);
		m2.setBody("Este es un mensaje de notificación");
		m2.setSubject("Cambio en Application");
		m2.setPriority("HIGH");
		this.messageService.save(m2);
		//res.getMessages().add(m2);
		//c.getReceivedMessages().add(m2);
		//hw.getSendedMessages().add(m2);
		//	this.handyWorkerService.save(hw);
		//	this.customerService.save(c);
		//this.folderService.save(res);
	}

	public void flush() {
		this.applicationRepository.flush();
	}

	public Collection<Application> findApplicationsByHandyWorker(final HandyWorker h) {
		Assert.notNull(h);
		Assert.isTrue(h.getId() > 0);
		Assert.notNull(this.handyWorkerService.findOne(h.getId()));
		return this.applicationRepository.findApplicationsByHandyWorker(h.getId());
	}

	public Collection<Application> findAll(final FixupTask f) {
		Assert.notNull(f);
		Assert.isTrue(f.getId() > 0);
		Assert.notNull(this.fixupTaskService.findOne(f.getId()));
		return this.applicationRepository.findApplicationsByHandyWorker(f.getId());
	}

	public Application create(final FixupTask dependency) {
		final Application res = this.create();
		res.setFixupTask(dependency);
		return res;
	}

}
