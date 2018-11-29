
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Administrator;
import domain.Customer;
import domain.HandyWorker;
import domain.Message;
import domain.Referee;
import domain.Sponsor;

@Service
@Transactional
public class ActorService {

	//--------------------Repositories--------------------------

	@Autowired
	private ActorRepository			actorRepository;

	//--------------------Services------------------------------

	@Autowired
	private MessageService			messageService;

	@Autowired
	private ServiceUtils			utilService;

	@Autowired
	private AdministratorService	adminService;

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private SponsorService			sponsorService;

	@Autowired
	private HandyWorkerService		handyWorkerService;

	@Autowired
	private RefereeService			refereeService;


	// ------------------CRUD methods-----------------------------

	public Actor findOne(final int actorId) {
		Assert.isTrue(actorId != 0);
		Actor result;
		result = this.actorRepository.findOne(actorId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Actor> findAll() {
		Collection<Actor> result;
		result = this.actorRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	// -------------------------Other methods-------------------------

	public Actor findOneByUserAccount(final UserAccount userAccount) {
		return this.actorRepository.findOneByUserAccount(userAccount.getId());
	}

	public Actor findPrincipal() {
		final UserAccount userAccount = LoginService.getPrincipal();
		return this.actorRepository.findOneByUserAccount(userAccount.getId());
	}

	public Map<String, Double> fixupTasksStats() {
		final Double[] statistics = this.actorRepository.fixupTasksStats();
		final Map<String, Double> res = new HashMap<>();

		res.put("MIN", statistics[0]);
		res.put("MAX", statistics[1]);
		res.put("AVG", statistics[2]);
		res.put("STD", statistics[3]);

		return res;
	}

	//List of suspicious actors
	//Flata hacer lo del spam. se hace aqui o en el domain?

	public Collection<Actor> suspiciousActors() {
		final Collection<Actor> res = new ArrayList<Actor>();
		for (final Actor a : this.findAll())
			if (this.checkSpam(a))
				res.add(a);

		return res;
	}

	//Ban actor
	public Boolean banActor(final Actor a) {
		final Boolean banned = false;
		Assert.notNull(a);
		final Boolean esAdmin = this.utilService.checkAuthorityBoolean("ADMIN");
		final Boolean esCustomer = this.utilService.checkAuthorityBoolean("CUSTOMER");
		final Boolean esSponsor = this.utilService.checkAuthorityBoolean("SPONSOR");
		final Boolean esHandyWorker = this.utilService.checkAuthorityBoolean("HANDYWORKER");
		final Boolean esReferee = this.utilService.checkAuthorityBoolean("REFEREE");
		//if (checkBan(a)){
		a.setBanned(true);
		if (esAdmin)
			this.adminService.save((Administrator) a);
		if (esCustomer)
			this.customerService.save((Customer) a);
		if (esSponsor)
			this.sponsorService.save((Sponsor) a);
		if (esHandyWorker)
			this.handyWorkerService.save((HandyWorker) a);
		if (esReferee)
			this.refereeService.save((Referee) a);

		return banned;
		//TODO ver como guardar el actor cuando ha sido baneado
	}

	//unban actor
	public Boolean unbanActor(final Actor a) {
		final Boolean banned = true;
		Assert.notNull(a);
		Assert.isTrue(a.getBanned());
		a.setBanned(false);
		//this.save(a);
		return banned;
	}

	public Boolean checkSpam(final Actor a) {
		Boolean res = false;
		for (final Message m : a.getSendedMessages())
			if (this.messageService.containsSpam(m))
				res = true;
		return res;
	}
	//--------------------método alternativo
	public Boolean banActorJuan(final Actor a) {
		Boolean res = false;
		if (a.getSuspicious() && !(a.getBanned()))
			for (final Authority au : a.getUserAccount().getAuthorities()) {
				if (au.getAuthority().equals(Authority.ADMIN)) {
					a.setBanned(true);

					final Collection<Administrator> admins = this.adminService.findAll();
					for (final Administrator admin : admins)
						if (admin.getUserAccount().equals(a.getUserAccount())) {
							admin.setBanned(true);
							this.adminService.save(admin);
							res = true;
							break;
						}
					break;
				} else if (au.getAuthority().equals(Authority.CUSTOMER)) {
					a.setBanned(true);
					final Collection<Customer> customers = this.customerService.findAll();
					for (final Customer customer : customers)
						if (customer.getUserAccount().equals(a.getUserAccount())) {
							customer.setBanned(true);
							this.customerService.save(customer);
							res = true;
							break;
						}
					break;
				} else if (au.getAuthority().equals(Authority.HANDYWORKER)) {
					a.setBanned(true);
					final Collection<HandyWorker> handyWorkers = this.handyWorkerService.findAll();
					for (final HandyWorker handyWorker : handyWorkers)
						if (handyWorker.getUserAccount().equals(a.getUserAccount())) {
							handyWorker.setBanned(true);
							this.handyWorkerService.save(handyWorker);
							res = true;
							break;
						}
					break;
				} else if (au.getAuthority().equals(Authority.REFEREE)) {
					a.setBanned(true);
					final Collection<Referee> referees = this.refereeService.findAll();
					for (final Referee referee : referees)
						if (referee.getUserAccount().equals(a.getUserAccount())) {
							referee.setBanned(true);
							this.refereeService.save(referee);
							res = true;
							break;
						}
					break;
				} else if (au.getAuthority().equals(Authority.SPONSOR)) {
					a.setBanned(true);
					final Collection<Sponsor> sponsors = this.sponsorService.findAll();
					for (final Sponsor sponsor : sponsors)
						if (sponsor.getUserAccount().equals(a.getUserAccount())) {
							sponsor.setBanned(true);
							this.sponsorService.save(sponsor);
							res = true;
							break;
						}

				}
				break;
			}
		//System.out.println(au.getAuthority().equals(Authority.ADMIN));

		return res;
	}

}
