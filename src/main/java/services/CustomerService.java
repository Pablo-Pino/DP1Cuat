
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CustomerRepository;
import security.LoginService;
import security.UserAccount;
import domain.Customer;
import domain.FixupTask;
import domain.Folder;
import domain.Message;
import domain.SocialProfile;

@Service
@Transactional
public class CustomerService {

	// Managed repository --------------------------------------

	@Autowired
	private CustomerRepository	customerRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private FolderService		folderService;

	@Autowired
	private UserAccountService	userAccountService;

	@Autowired
	private ServiceUtils		serviceUtils;


	// Constructors -----------------------------------------------------------

	public CustomerService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Customer create() {
		Customer result;
		result = new Customer();
		//los atributos que no pueden estar vacíos
		result.setBanned(false);
		result.setSuspicious(false);
		result.setFolders(new ArrayList<Folder>());
		result.setReceivedMessages(new ArrayList<Message>());
		result.setSendedMessages(new ArrayList<Message>());
		result.setSocialProfiles(new ArrayList<SocialProfile>());
		//establezco ya su tipo de userAccount porque no va a cambiar
		result.setUserAccount(this.userAccountService.create("CUSTOMER"));
		return result;
	}

	public Customer findOne(final int customerId) {
		Customer res;

		res = this.customerRepository.findOne(customerId);
		Assert.notNull(res);

		return res;

	}

	public Collection<Customer> findAll() {
		Collection<Customer> res;

		res = this.customerRepository.findAll();
		Assert.notNull(res);

		return res;
	}

	public Customer save(final Customer customer) {
		//comprobamos que el customer que nos pasan no sea nulo
		Assert.notNull(customer);

		//comprobamos que su id no sea negativa por motivos de seguridad
		this.serviceUtils.checkIdSave(customer);

		//este customer será el que está en la base de datos para usarlo si estamos ante un customer que ya existe
		Customer customerBD;
		Assert.isTrue(customer.getId() > 0);

		//cogemos el customer de la base de datos
		customerBD = this.customerRepository.findOne(customer.getId());

		//Si el customer que estamos guardando es nuevo (no está en la base de datos) le ponemos todos sus atributos vacíos
		if (customer.getId() == 0) {
			customer.setBanned(false);
			customer.setFixupTasks(new ArrayList<FixupTask>());
			customer.setFolders(this.folderService.createSystemFolders(customer));
			customer.setReceivedMessages(new ArrayList<Message>());
			customer.setSendedMessages(new ArrayList<Message>());
			customer.setSocialProfiles(new ArrayList<SocialProfile>());
			customer.setSuspicious(false);

			//comprobamos que ningún actor resté autenticado (ya que ningun actor puede crear los customers)
			this.serviceUtils.checkNoActor();

		} else {
			customer.setBanned(customerBD.getBanned());
			customer.setFixupTasks(customerBD.getFixupTasks());
			customer.setFolders(customerBD.getFolders());
			customer.setReceivedMessages(customerBD.getReceivedMessages());
			customer.setSendedMessages(customerBD.getSendedMessages());
			customer.setSocialProfiles(customerBD.getSocialProfiles());
			customer.setSuspicious(customerBD.getSuspicious());
			customer.setUserAccount(customerBD.getUserAccount());

			//Comprobamos que el actor sea un Customer
			this.serviceUtils.checkAuthority("CUSTOMER");
			//esto es para ver si el actor que está logueado es el mismo que se está editando
			this.serviceUtils.checkActor(customer);

		}
		Customer res;
		//le meto al resultado final el customer que he ido modificando anteriormente
		res = this.customerRepository.save(customer);
		return res;
	}

	//no realizamos el delete porque no se va a borrar nunca un customer

	// Other business methods -------------------------------------------------

	public Customer findByPrincipal() {
		Customer result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}

	public Customer findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Customer result;

		result = this.customerRepository.findByUserAccountId(userAccount.getId());

		return result;
	}

	public Collection<Customer> getTop3CustomerWithMoreComplaints() {
		final Collection<Customer> ratio = this.customerRepository.getTop3CustomerWithMoreComplaints();
		return ratio;
	}
	
	public Collection<Customer> listCustomer10() {
		final Collection<Customer> list = this.customerRepository.listCustomer10();
		return list;
	}
	
	
}
