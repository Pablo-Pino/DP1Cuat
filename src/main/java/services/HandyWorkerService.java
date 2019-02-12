
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.HandyWorkerRepository;
import security.Authority;
import security.UserAccount;
import domain.Application;
import domain.Complaint;
import domain.Customer;
import domain.FixupTask;
import domain.HandyWorker;
import domain.Note;
import domain.Phase;
import domain.Report;
import domain.Settings;
import domain.Url;
import domain.WorkPlan;

@Service
@Transactional
public class HandyWorkerService {

	//Managed Repository

	@Autowired
	private HandyWorkerRepository	handyWorkerRepository;

	// Supporting Service

	@Autowired
	private FixupTaskService		fixupTaskService;

	@Autowired
	private ServiceUtils			serviceUtils;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ApplicationService		applicationService;

	@Autowired
	private ReportService			reportService;

	@Autowired
	private WorkPlanService			workPlanService;

	@Autowired
	FolderService					folderService;

	@Autowired
	private SettingsService			settingsService;


	//constructor

	public HandyWorkerService() {
		super();
	}

	// Simple CRUD methods

	public HandyWorker create() {
		HandyWorker result;
		result = new HandyWorker();
		//establezco ya su tipo de userAccount porque no va a cambiar
		result.setUserAccount(new UserAccount());
		final Authority authority = new Authority();
		authority.setAuthority(Authority.HANDYWORKER);
		result.getUserAccount().addAuthority(authority);
		//los atributos que no pueden estar vacíos
		final String make = "initialMake";
		result.setMake(make);
		result.getUserAccount().setBanned(false);
		result.setSuspicious(false);
		return result;
	}

	public Collection<HandyWorker> findAll() {
		return this.handyWorkerRepository.findAll();
	}

	public Collection<HandyWorker> findAll(final int handyWorkerId) {
		Collection<HandyWorker> hw;

		hw = this.handyWorkerRepository.findAll();
		Assert.notNull(hw);

		return hw;
	}

	public HandyWorker findOne(final int handyWorkerId) {
		Assert.notNull(this);
		return this.handyWorkerRepository.findOne(handyWorkerId);
	}

	public HandyWorker save(final HandyWorker hw) {
		//comprobamos que el handy que nos pasan no sea nulo
		Assert.notNull(hw);

		Assert.isTrue(!(hw.getEmail().endsWith("@") || hw.getEmail().endsWith("@>")));

		Boolean isCreating = null;

		//si es nuevo
		if (hw.getId() == 0) {
			isCreating = true;
			hw.setSuspicious(false);
		} else {
			isCreating = false;
			//compruebo que esté guardado
			this.serviceUtils.checkIdSave(hw);
			//este es el de la base de datos
			final HandyWorker handyWorkerBD;
			Assert.isTrue(hw.getId() > 0);

			handyWorkerBD = this.handyWorkerRepository.findOne(hw.getId());

			hw.setSuspicious(handyWorkerBD.getSuspicious());
			hw.setUserAccount(handyWorkerBD.getUserAccount());

			this.serviceUtils.checkAuthority("HANDYWORKER");
			this.serviceUtils.checkActor(hw);
		}
		if ((!hw.getPhone().startsWith("+")) && StringUtils.isNumeric(hw.getPhone()) && hw.getPhone().length() > 3) {
			final Settings settings = this.settingsService.findSettings();
			hw.setPhone(settings.getCountryCode() + hw.getPhone());
		}
		HandyWorker res;
		res = this.handyWorkerRepository.save(hw);
		this.flush();
		if (isCreating)
			this.folderService.createSystemFolders(res);
		return res;
	}

	public void delete(final HandyWorker hw) {
		Assert.notNull(hw);

		Assert.isTrue(hw.getId() != 0);
		this.handyWorkerRepository.delete(hw);
	}

	//Other methods

	public Map<String, Collection<HandyWorker>> fixupTasksTop3() {
		final Collection<HandyWorker> collection = this.handyWorkerRepository.findTop3HandyWorkerWithMoreComplaints();
		final Map<String, Collection<HandyWorker>> res = new HashMap<>();

		res.put("Collection", collection);

		return res;

	}

	public List<HandyWorker> getTop3HandyWorkerWithMoreComplaints() {
		List<HandyWorker> ratio = new ArrayList<>();
		ratio = this.handyWorkerRepository.findTop3HandyWorkerWithMoreComplaints();
		if (ratio.size() > 2)
			ratio = this.handyWorkerRepository.findTop3HandyWorkerWithMoreComplaints().subList(0, 3);
		return ratio;
	}

	public Collection<HandyWorker> listHandyWorkerApplication() {
		final Collection<HandyWorker> list = this.handyWorkerRepository.listHandyWorkerApplication();
		return list;
	}

	//un handy worker lista todos los fixupTasks
	public Collection<FixupTask> getAllFixupTasks() {
		Collection<FixupTask> res;
		res = this.fixupTaskService.findAll();
		return res;
	}

	public Collection<FixupTask> getFixupTaskFromCustomer(final Customer customer) {
		Collection<FixupTask> res;
		res = this.fixupTaskService.findByCustomer(customer);
		return res;
	}

	public void banActor(final HandyWorker hw) {
		Assert.notNull(hw);
		this.serviceUtils.checkAuthority("ADMIN");
		hw.getUserAccount().setBanned(true);
		this.handyWorkerRepository.save(hw);
	}

	public void unbanActor(final HandyWorker hw) {
		Assert.notNull(hw);
		this.serviceUtils.checkAuthority("ADMIN");
		hw.getUserAccount().setBanned(false);
		this.handyWorkerRepository.save(hw);
	}

	public HandyWorker findByAssignedFixupTask(final FixupTask f) {
		final FixupTask fixupTask = (FixupTask) this.serviceUtils.checkObject(f);
		return this.handyWorkerRepository.findByAssignedFixupTask(fixupTask.getId());
	}

	public boolean isSuspicious(final HandyWorker h) {
		final HandyWorker handyWorker = (HandyWorker) this.serviceUtils.checkObject(h);
		Boolean res = false;
		for (final Application a : this.applicationService.findApplicationsByHandyWorker(handyWorker))
			if (this.actorService.containsSpam(a.getWorkerComments())) {
				res = true;
				break;
			}
		for (final FixupTask f : this.fixupTaskService.findAcceptedFixupTasksByHandyWorker(handyWorker))
			for (final Complaint com : f.getComplaints()) {
				for (final Url u : com.getAttachments())
					if (this.actorService.containsSpam(u.getUrl())) {
						res = true;
						break;
					}
				if (this.actorService.containsSpam(com.getDescription())) {
					res = true;
					break;
				}
				final Report report = this.reportService.findByComplaint(com);
				for (final Note n : report.getNotes())
					for (final String comment : n.getComments())
						if (this.actorService.containsSpam(comment)) {
							res = true;
							break;
						}
			}
		for (final WorkPlan w : this.workPlanService.findWorkPlanByHandyWorker(handyWorker))
			for (final Phase p : w.getPhases())
				if (this.actorService.containsSpam(p.getDescription()) || this.actorService.containsSpam(p.getTitle())) {
					res = true;
					break;
				}
		return res;
	}

	public void flush() {
		this.handyWorkerRepository.flush();
	}

}
