
package services;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
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
	private HandyWorkerService		handyWorkerService;

	@Autowired
	private ServiceUtils			serviceUtils;

	@Autowired
	private MessageService			messageService;
	@Autowired
	private FolderService			folderService;
	@Autowired
	private CustomerService			customerService;


	// Constructors
	public ApplicationService() {
		super();
	}

	// Simple CRUD methods

	public Application create() {
		final Application a = new Application();
		return a;
	}

	public Collection<Application> findAll() {
		return this.applicationRepository.findAll();
	}

	public Application findOne(final int applicationId) {
		return this.applicationRepository.findOne(applicationId);
	}

	public Application save(final Application app) {
		Assert.notNull(app);
		Application res;
		//compruebo si esa fixuptask tiene una app accepted si no la tiene lo guardo
		final FixupTask f = app.getFixupTask();
		if (this.getTieneYaACCEPTED(f) == false)
			throw new IllegalArgumentException("Only one application among all the applications for a fixup task can be accepted");
		//
		if (app.getStatus().equals("ACCEPTED")) {
			//cuando cambia a accepted una creditcard valida se debe dar
			//si se ha cambiado a accepted manda un mensaje al handyworker y al customer avisandoles
			Assert.notNull(app.getHandyWorker());
			Assert.notNull(app.getFixupTask().getCustomer());
			this.NotificationMessage(app.getHandyWorker(), app.getFixupTask().getCustomer());

		}

		res = this.applicationRepository.save(app);
		return res;
	}
	private boolean getTieneYaACCEPTED(final FixupTask f) {
		Boolean res = false;
		for (final Application a : f.getApplications())
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

	public Map<String, Double> lateApplicationsRatio() {
		final Double ratio = this.applicationRepository.lateApplicationsRatio();
		final Map<String, Double> res = new HashMap<>();

		res.put("Ratio", ratio);

		return res;
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
		for (final Folder f : hw.getFolders()) {
			if (f.getName().equals("inbox"))
				res = f;
			final Message m1 = this.messageService.create(f);
			m1.setReceiver(hw);

			m1.setSender(c);

			this.messageService.save(m1);
			res.getMessages().add(m1);
			hw.getReceivedMessages().add(m1);
			c.getSendedMessages().add(m1);
			this.folderService.save(res);
		}
		for (final Folder f : c.getFolders()) {
			if (f.getName().equals("inbox"))
				res = f;
			final Message m2 = this.messageService.create(f);

			m2.setReceiver(c);

			m2.setSender(hw);

			this.messageService.save(m2);
			res.getMessages().add(m2);
			c.getReceivedMessages().add(m2);
			hw.getSendedMessages().add(m2);
			this.handyWorkerService.save(hw);
			this.customerService.save(c);
			this.folderService.save(res);
		}

	}

}
