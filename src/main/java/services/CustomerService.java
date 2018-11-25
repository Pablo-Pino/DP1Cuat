
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
	private ActorService		actorService;

	@Autowired
	private FolderService		folderService;

	@Autowired
	private UserAccountService	userAccountService;


	// Constructors -----------------------------------------------------------

	public CustomerService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Customer create() {
		Customer result;
		result = new Customer();
		result.setBanned(false);
		result.setSuspicious(false);
		result.setFolders(new ArrayList<Folder>());
		result.setReceivedMessages(new ArrayList<Message>());
		result.setSendedMessages(new ArrayList<Message>());
		result.setSocialProfiles(new ArrayList<SocialProfile>());
		result.setUserAccount(this.userAccountService.create("CUSTOMER"));
		return result;
	}
	//	public Customer create() {
	//		UserAccount userAccount;
	//		Authority authority;
	//		final Collection<Authority> authorities;
	//		Collection<Folder> folders;
	//		Customer result;
	//
	//		folders = new ArrayList<Folder>();
	//
	//		authority = new Authority();
	//		authority.setAuthority(Authority.CUSTOMER);
	//
	//		authorities = new ArrayList<Authority>();
	//		authorities.add(authority);
	//
	//		userAccount = new UserAccount();
	//		userAccount.setAuthorities(authorities);
	//
	//		result = new Customer();
	//		result.setUserAccount(userAccount);
	//		Collection<Fix>
	//		result.setFixupTasks(fixupTasks)
	//
	//		folders = this.folderService.createSystemFolders(result);
	//		result.setFolders(folders);
	//
	//		return result;
	//	}
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

		Assert.notNull(customer);
		Customer res;
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
}
