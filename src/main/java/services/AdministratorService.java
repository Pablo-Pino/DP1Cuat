
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.UserAccount;
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

	//Actor service no le haria falta?

	@Autowired
	private FolderService			folderService;

	@Autowired
	private ServiceUtils			serviceUtils;


	// --------------------------Constructor-----------------------

	public AdministratorService() {
		super();
	}

	// --------------------CRUD methods----------------------------

	public Administrator create() {
		final Administrator result = new Administrator();
		result.setBanned(false);
		result.setSuspicious(false);
		
		//----- Las listas que tiene que tener se las pongo vacias-------------
		result.setSocialProfiles(new ArrayList<SocialProfile>());
		result.setFolders(new ArrayList<Folder>());
		result.setReceivedMessages(new ArrayList<Message>());
		result.setSendedMessages(new ArrayList<Message>());
		result.setUserAccount(new UserAccount());
		return result;

	}

	public Administrator save(final Administrator object) {
		final Administrator admin = this.checkObjectSave(object);
		if (object.getId() == 0) {
			object.setBanned(false);
			object.setFolders(this.folderService.createSystemFolders(object));
			object.setReceivedMessages(new ArrayList<Message>());
			object.setSendedMessages(new ArrayList<Message>());
			object.setSocialProfiles(new ArrayList<SocialProfile>());
			object.setSuspicious(false);
			this.checkPermisionActor(null, new String[] {
				Authority.ADMIN
			});
		} else {
			object.setBanned(admin.getBanned());
			object.setFolders(admin.getFolders());
			object.setReceivedMessages(admin.getReceivedMessages());
			object.setSendedMessages(admin.getSendedMessages());
			object.setSocialProfiles(admin.getSocialProfiles());
			object.setSuspicious(admin.getSuspicious());
			object.setUserAccount(admin.getUserAccount());
			this.checkPermisionActor(admin, new String[] {
				Authority.REFEREE
			});
		}
		final Administrator res = this.administratorRepository.save(object);
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

}
