
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
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Administrator;


@Service
@Transactional
public class ActorService {

	//--------------------Repositories--------------------------

	@Autowired
	private ActorRepository	actorRepository;
	


	//--------------------Services------------------------------
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private ServiceUtils utilService;
	
	@Autowired
	private AdministratorService adminService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private SponsorService sponsorService;
	
	@Autowired
	private HandyWorkerService handyworkerService;
	
	@Autowired
	private RefereeService refereeService;


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
		Collection<Actor> res = new ArrayList<Actor>();
		for (final Actor a : this.findAll())
			if (a.getSuspicious())
				res.add(a);

		return res;
	}

	//Ban actor
		public Boolean banActor(final Actor a) {
			Boolean banned = false;
			Assert.notNull(a);
			Boolean esAdmin = utilService.checkAuthorityBoolean("ADMIN");
			Boolean esCustomer = utilService.checkAuthorityBoolean("CUSTOMER");
			Boolean esSponsor = utilService.checkAuthorityBoolean("SPONSOR");
			Boolean esHandyWorker = utilService.checkAuthorityBoolean("HANDYWORKER");
			Boolean esReferee = utilService.checkAuthorityBoolean("REFEREE");
			//if (checkBan(a)){
			a.setBanned(true);
			if(esAdmin){
				this.adminService.save(null);
			}
			if(esCustomer){
				this.customerService.save(null);
			}
			if(esSponsor){
				this.sponsorService.save(null);
			}
			if(esHandyWorker){
				this.handyWorkerService.save(null);
			}
			if(esAdmin){
				this.refereeService.save(null);
			}
			
			return banned;
			//TODO ver como guardar el actor cuando ha sido baneado
		}
		
		//unban actor
		public Boolean unbanActor(final Actor a) {
			Boolean banned = true;
			Assert.notNull(a);
			Assert.isTrue(a.getBanned());
			a.setBanned(false);
			//this.save(a);
			return banned;
		}
		

}
