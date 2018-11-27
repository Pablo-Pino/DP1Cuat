
package services;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.Authority;
import security.UserAccount;
import utilities.AbstractTest;
import domain.Complaint;
import domain.Folder;
import domain.Message;
import domain.Referee;
import domain.SocialProfile;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class RefereeServiceTest extends AbstractTest {

	// Services

	@Autowired
	private RefereeService	refereeService;
	@Autowired
	private FolderService	folderService;


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
			Assert.isTrue(!(referee.getBanned() || referee.getSuspicious()));
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	public void saveReferee(final String username, final String address, final Boolean banned, final String email, final String middleName, final String name, final String phone, final String photo, final String surname, final Boolean suspicious,
		final Integer refereeId, final String newUsername, final String newPassword, final Collection<Authority> newAuthorities, UserAccount userAccount, final Collection<Complaint> complaints, final Collection<Folder> folders,
		final Collection<Message> receivedMessages, final Collection<Message> sendedMessages, final Collection<SocialProfile> socialProfiles, final Class<?> expected) {
		Class<?> caught = null;
		try {
			this.authenticate(username);
			Referee referee = null;
			Referee oldReferee = null;
			if (refereeId == null)
				referee = this.refereeService.create();
			else {
				referee = this.refereeService.findOne(refereeId);
				oldReferee = referee;
			}
			if (userAccount == null) {
				userAccount = new UserAccount();
				userAccount.setUsername(newUsername);
				userAccount.setPassword(newPassword);
				userAccount.setAuthorities(newAuthorities);
			}
			referee.setAddress(address);
			referee.setBanned(banned);
			referee.setEmail(email);
			referee.setMiddleName(middleName);
			referee.setName(name);
			referee.setPhone(phone);
			referee.setPhoto(photo);
			referee.setSurname(surname);
			referee.setSuspicious(suspicious);
			referee.setUserAccount(userAccount);
			referee.setComplaints(complaints);
			referee.setFolders(folders);
			referee.setReceivedMessages(receivedMessages);
			referee.setSendedMessages(sendedMessages);
			referee.setSocialProfiles(socialProfiles);
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
				Assert.isTrue(!savedReferee.getBanned());
				Assert.isTrue(!savedReferee.getSuspicious());
				Assert.isTrue(savedReferee.getComplaints().isEmpty());
				Assert.isTrue(savedReferee.getReceivedMessages().isEmpty());
				Assert.isTrue(savedReferee.getSendedMessages().isEmpty());
				Assert.isTrue(savedReferee.getSocialProfiles().isEmpty());
				final List<String> systemFolderNames = Arrays.asList((new String[] {
					"inbox", "outbox", "spambox", "trashbox"
				}));
				for (final Folder f : savedReferee.getFolders()) {
					systemFolderNames.remove(f.getName());
					Assert.isTrue(f.getSystem());
					Assert.isTrue(f.getActor().equals(savedReferee));
					Assert.isTrue(f.getChildFolder().isEmpty());
					Assert.isTrue(f.getMessages().isEmpty());
					Assert.isTrue(f.getParentFolder().equals(f));
					Assert.notNull(this.folderService.findOne(f.getId()));
				}
			} else {
				Assert.isTrue(savedReferee.getBanned() == oldReferee.getBanned());
				Assert.isTrue(savedReferee.getSuspicious() == oldReferee.getSuspicious());
				Assert.isTrue(savedReferee.getComplaints().equals(oldReferee.getComplaints()));
				Assert.isTrue(savedReferee.getReceivedMessages().equals(oldReferee.getReceivedMessages()));
				Assert.isTrue(savedReferee.getSendedMessages().equals(oldReferee.getSendedMessages()));
				Assert.isTrue(savedReferee.getSocialProfiles().equals(oldReferee.getSocialProfiles()));
				Assert.isTrue(savedReferee.getFolders().equals(oldReferee.getFolders()));
			}
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	public void deleteReferee(final String username, final Integer refereeId, final Class<?> expected) {
		Class<?> caught = null;
		try {
			this.authenticate(username);
			final Referee referee = this.refereeService.findOne(refereeId);
			this.refereeService.delete(referee);
			this.refereeService.flush();
			Assert.isNull(this.refereeService.findOne(refereeId));
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
