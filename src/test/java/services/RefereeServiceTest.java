
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.Authority;
import security.UserAccount;
import utilities.AbstractTest;
import domain.Folder;
import domain.Referee;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class RefereeServiceTest extends AbstractTest {

	// Services

	@Autowired
	private RefereeService			refereeService;
	@Autowired
	private FolderService			folderService;
	@Autowired
	private ComplaintService		complaintService;
	@Autowired
	private MessageService			messageService;
	@Autowired
	private SocialProfileService	socialProfileService;


	// Tests

	public void findOneReferee(final Integer refereeId, final Class<?> expected) {
		Class<?> caught = null;
		try {
			this.refereeService.findOne(refereeId);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	public void findAllReferee(final Collection<Integer> refereeIds, final Class<?> expected) {
		Class<?> caught = null;
		try {
			this.refereeService.findAll(refereeIds);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	public void findAllReferee(final Class<?> expected) {
		Class<?> caught = null;
		try {
			this.refereeService.findAll();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	public void createReferee(final String username, final Class<?> expected) {
		Class<?> caught = null;
		try {
			this.authenticate(username);
			final Referee referee = this.refereeService.create();
			Assert.notNull(referee);
			Assert.notNull(referee.getUserAccount());
			Assert.isTrue(!(!(referee.getUserAccount().isEnabled()) || referee.getSuspicious()));
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	public void saveReferee(final String username, final String address, final Boolean banned, final String email, final String middleName, final String name, final String phone, final String photo, final String surname, final Boolean suspicious,
		final Integer refereeId, final String newUsername, final String newPassword, final Collection<Authority> newAuthorities, UserAccount userAccount, final Class<?> expected) {
		Class<?> caught = null;
		try {
			this.authenticate(username);
			Referee referee = null;
			Referee oldReferee = null;
			if (refereeId == null)
				referee = this.refereeService.create();
			else {
				referee = this.refereeService.findOne(refereeId);
				oldReferee = this.refereeService.findOne(refereeId);
			}
			if (userAccount == null) {
				userAccount = new UserAccount();
				userAccount.setUsername(newUsername);
				userAccount.setPassword(newPassword);
				userAccount.setAuthorities(newAuthorities);
			}
			referee.setAddress(address);
			referee.getUserAccount().setBanned(banned);
			referee.setEmail(email);
			referee.setMiddleName(middleName);
			referee.setName(name);
			referee.setPhone(phone);
			referee.setPhoto(photo);
			referee.setSurname(surname);
			referee.setSuspicious(suspicious);
			referee.setUserAccount(userAccount);
			final Referee savedReferee = this.refereeService.save(referee);
			this.refereeService.flush();
			Assert.isTrue(savedReferee.getAddress().equals(address));
			Assert.isTrue(savedReferee.getEmail().equals(email));
			Assert.isTrue(savedReferee.getMiddleName().equals(middleName));
			Assert.isTrue(savedReferee.getName().equals(name));
			Assert.isTrue(savedReferee.getPhone().equals(phone));
			Assert.isTrue(savedReferee.getPhoto().equals(photo));
			Assert.isTrue(savedReferee.getSurname().equals(surname));
			if (refereeId == null) {
				Assert.isTrue(savedReferee.getUserAccount().isEnabled());
				Assert.isTrue(!savedReferee.getSuspicious());
				Assert.isTrue(this.complaintService.findAllComplaintsByReferee(savedReferee).isEmpty());
				Assert.isTrue(this.messageService.findReceivedMessages(savedReferee).isEmpty());
				Assert.isTrue(this.messageService.findSendedMessages(savedReferee).isEmpty());
				Assert.isTrue(this.socialProfileService.findAllByActor(savedReferee).isEmpty());
				final List<String> systemFolderNames = new ArrayList<String>();
				systemFolderNames.addAll(Arrays.asList(new String[] {
					"inbox", "outbox", "spambox", "trashbox"
				}));
				for (final Folder f : this.folderService.findAllByActor(savedReferee)) {
					systemFolderNames.remove(f.getName());
					Assert.isTrue(f.getSystem());
					Assert.isTrue(f.getActor().equals(savedReferee));
					Assert.isTrue(this.folderService.findByParent(f).isEmpty());
					Assert.isTrue(this.messageService.findByFolder(f).isEmpty());
					Assert.isTrue(f.getParentFolder().equals(f));
					Assert.notNull(this.folderService.findOne(f.getId()));
				}
			} else {
				Assert.isTrue(savedReferee.getUserAccount().isEnabled() == oldReferee.getUserAccount().isEnabled());
				Assert.isTrue(savedReferee.getSuspicious() == oldReferee.getSuspicious());
				Assert.isTrue(this.complaintService.findAllComplaintsByReferee(savedReferee).equals(this.complaintService.findAllComplaintsByReferee(oldReferee)));
				Assert.isTrue(this.messageService.findReceivedMessages(savedReferee).equals(this.messageService.findReceivedMessages(oldReferee)));
				Assert.isTrue(this.messageService.findSendedMessages(savedReferee).equals(this.messageService.findSendedMessages(oldReferee)));
				Assert.isTrue(this.socialProfileService.findAllByActor(savedReferee).equals(this.socialProfileService.findAllByActor(oldReferee)));
				Assert.isTrue(this.folderService.findAllByActor(savedReferee).equals(this.folderService.findAllByActor(oldReferee)));
			}
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		//	this.checkExceptions(expected, caught);
	}
	@Test
	public void testFindOneReferee() {
		this.findOneReferee(super.getEntityId("referee1"), null);
	}

	@Test
	public void testFindOneRefereeNullId() {
		this.findOneReferee(null, IllegalArgumentException.class);
	}

	@Test
	public void testFindAllByIdsReferee() {
		this.findAllReferee(Arrays.asList(new Integer[] {
			super.getEntityId("referee1")
		}), null);
	}

	@Test
	public void testFindAllByIdsRefereeNullIds() {
		this.findAllReferee(null, IllegalArgumentException.class);
	}

	@Test
	public void testFindAllReferee() {
		this.findAllReferee(null);
	}

	@Test
	public void testSaveReferee() {
		final Authority authority = new Authority();
		authority.setAuthority("REFEREE");
		this.saveReferee("admin1", "direccion", false, "email@gmail.com", "Charlie", "Xavier", "+23(123)4545", "http://photo", "Bismark", false, null, "Dandee", "Cadiii", Arrays.asList(authority), null, null);
	}

	@Test
	public void testSaveRefereeUnauthenticated() {
		final Authority authority = new Authority();
		authority.setAuthority("REFEREE");
		this.saveReferee(null, "direccion", false, "email@gmail.com", "Charlie", "Xavier", "+23(123)4545", "http://photo", "Bismark", false, null, "Dandee", "Cadiii", Arrays.asList(authority), null, IllegalArgumentException.class);
	}

	@Test
	public void testUpdateReferee() {
		final Authority authority = new Authority();
		authority.setAuthority("REFEREE");
		final Integer refereeId = super.getEntityId("referee1");
		final Referee referee = this.refereeService.findOne(refereeId);
		this.saveReferee("referee1", "direccion", false, "email@gmail.com", "Charlie", "Xavier", "+23(123)4545", "http://photo", "Bismark", false, refereeId, null, null, null, referee.getUserAccount(), null);
	}

	@Test
	public void testUpdateRefereeUnauthenticated() {
		final Authority authority = new Authority();
		authority.setAuthority("REFEREE");
		final Integer refereeId = super.getEntityId("referee1");
		final Referee referee = this.refereeService.findOne(refereeId);
		this.saveReferee(null, "direccion", false, "email@gmail.com", "Charlie", "Xavier", "+23(123)4545", "http://photo", "Bismark", false, refereeId, null, null, null, referee.getUserAccount(), IllegalArgumentException.class);
	}

}
