
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CustomerRepository;
import domain.Customer;
import domain.FixupTask;

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

	@Autowired
	private FixupTaskService	fixupTaskService;


	// Constructors -----------------------------------------------------------

	public CustomerService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Customer create() {
		Customer result;
		result = new Customer();
		//establezco ya su tipo de userAccount porque no va a cambiar
		result.setUserAccount(this.userAccountService.create("CUSTOMER"));
		//los atributos que no pueden estar vacíos
		result.getUserAccount().setBanned(false);
		result.setSuspicious(false);
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
			customer.getUserAccount().setBanned(false);
			this.folderService.createSystemFolders(customer);
			customer.setSuspicious(false);

			//comprobamos que ningún actor resté autenticado (ya que ningun actor puede crear los customers)
			this.serviceUtils.checkNoActor();

		} else {
			customer.getUserAccount().setBanned(customerBD.getUserAccount().getBanned());
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

	public Collection<Customer> getTop3CustomerWithMoreComplaints() {
		final Collection<Customer> ratio = new ArrayList<>();

		final Collection<Customer> customersCompl = this.customerRepository.getTop3CustomerWithMoreComplaints();
		int i = 0;
		for (final Customer c : customersCompl)
			if (i < 3) {
				ratio.add(c);
				i++;

			}
		return ratio;
	}

	//The listing of customers who have published at least 10% more fix-up tasks than the average, ordered by number of applications
	public Collection<Customer> listCustomer10() {
		final Collection<Customer> list = this.customerRepository.listCustomer10();
		return list;
	}

	//Un customer puede listar todas las fixuptask
	public Collection<FixupTask> getAllFixupTasks() {
		Collection<FixupTask> res;
		res = this.fixupTaskService.findAll();
		return res;
	}

	//Un customer puede listar sus fixuptask
	public Collection<FixupTask> getFixupTaskByCustomer(final Customer c) {
		final Collection<FixupTask> res = this.fixupTaskService.findByCustomer(c);
		return res;
	}

	public void banActor(final Customer a) {
		Assert.notNull(a);
		this.serviceUtils.checkAuthority("ADMIN");
		a.getUserAccount().setBanned(true);
		this.customerRepository.save(a);

	}

	public void unBanActor(final Customer a) {
		Assert.notNull(a);
		this.serviceUtils.checkAuthority("ADMIN");
		a.getUserAccount().setBanned(false);
		this.customerRepository.save(a);

	}

}
