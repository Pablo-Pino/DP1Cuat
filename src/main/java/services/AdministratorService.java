
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import domain.Administrator;
import domain.Folder;
import domain.Message;
import domain.SocialProfile;

@Service
@Transactional
public class AdministratorService {

	//--------------Managed repository---------------------------

	@Autowired
	private AdministratorRepository	administratorRepository;

	//-------------- Supporting Services-----------------------

	@Autowired
	private FolderService			folderService;

	@Autowired
	private ServiceUtils			serviceUtils;

	@Autowired
	private UserAccountService		userAccountService;


	// --------------------------Constructor-----------------------

	public AdministratorService() {
		super();
	}

	// --------------------CRUD methods----------------------------

	public Administrator create() {
		Administrator result;
		result = new Administrator();
		result.setBanned(false);
		result.setSuspicious(false);
		result.setFolders(new ArrayList<Folder>());
		result.setReceivedMessages(new ArrayList<Message>());
		result.setSendedMessages(new ArrayList<Message>());
		result.setSocialProfiles(new ArrayList<SocialProfile>());
		//establezco ya su tipo de userAccount porque no va a cambiar
		result.setUserAccount(this.userAccountService.create("ADMIN"));

		return result;

	}

	public Administrator save(final Administrator administrator) {
		//comprobamos que el customer que nos pasan no sea nulo
		Assert.notNull(administrator);

		//comprobamos que su id no sea negativa por motivos de seguridad
		this.serviceUtils.checkIdSave(administrator);

		//este admin será el que está en la base de datos para usarlo si estamos ante un admin que ya existe
		Administrator adminDB;
		Assert.isTrue(administrator.getId() > 0);

		//cogemos el admin de la base de datos
		adminDB = this.administratorRepository.findOne(administrator.getId());

		//Si el admin que estamos guardando es nuevo (no está en la base de datos) le ponemos todos sus atributos vacíos
		if (administrator.getId() == 0) {
			administrator.setBanned(false);
			administrator.setFolders(this.folderService.createSystemFolders(administrator));
			administrator.setReceivedMessages(new ArrayList<Message>());
			administrator.setSendedMessages(new ArrayList<Message>());
			administrator.setSocialProfiles(new ArrayList<SocialProfile>());
			administrator.setSuspicious(false);

			//comprobamos que ningún actor resté autenticado (ya que ningun actor puede crear los customers)
			//this.serviceUtils.checkNoActor();

		} else {
			administrator.setBanned(adminDB.getBanned());
			administrator.setFolders(adminDB.getFolders());
			administrator.setReceivedMessages(adminDB.getReceivedMessages());
			administrator.setSendedMessages(adminDB.getSendedMessages());
			administrator.setSocialProfiles(adminDB.getSocialProfiles());
			administrator.setSuspicious(adminDB.getSuspicious());
			administrator.setUserAccount(adminDB.getUserAccount());

			//Comprobamos que el actor sea un admin
			this.serviceUtils.checkAuthority("ADMIN");
			//esto es para ver si el actor que está logueado es el mismo que se está editando
			this.serviceUtils.checkActor(administrator);

		}
		Administrator res;
		//le meto al resultado final el admin que he ido modificando anteriormente
		res = this.administratorRepository.save(administrator);
		return res;
	}

	//----------------------------

	public Administrator findOne(final int administratorId) {
		return this.administratorRepository.findOne(administratorId);
	}

	public Collection<Administrator> findAll() {
		Collection<Administrator> res;
		res = this.administratorRepository.findAll();
		Assert.notNull(res);

		return res;
	}

	// -------------------------Other business methods ------------------------------

	public void banActor(final Administrator a) {
		Assert.notNull(a);
		this.serviceUtils.checkAuthority("ADMIN");
		a.setBanned(true);
		this.save(a);

	}

	public void unBanActor(final Administrator a) {
		Assert.notNull(a);
		this.serviceUtils.checkAuthority("ADMIN");
		a.setBanned(false);
		this.save(a);

	}
	//	//Ban actor
	//	public Boolean banActor(final Administrator a) {
	//		Boolean banned = false;
	//		Assert.notNull(a);
	//		//if (checkBan(a)){
	//		a.setBanned(true);
	//		//}
	//		this.save(a);
	//		return banned;
	//		//TODO ver como guardar el actor cuando ha sido baneado
	//	}
	//	
	//	//unban actor
	//	public Boolean unbanActor(final Administrator a) {
	//		Boolean banned = true;
	//		Assert.notNull(a);
	//		Assert.isTrue(a.getBanned());
	//		a.setBanned(false);
	//		this.save(a);
	//		return banned;
	//	}
	//	
	//	//comprueba que tenga spam, 
	//	
	////	public Boolean checkBan(final Actor a){
	////		Boolean res= false;
	////		
	////	}
	//	
	//	public 
}
