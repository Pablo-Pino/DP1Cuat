
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Actor extends DomainEntity {

	//----------------Atributos-------------------

	private String						name;
	private String						middleName;
	private String						surname;
	private String						photo;
	private String						email;

	private String						phone;
	private String						address;
	private Boolean						banned;
	private Boolean						suspicious;

	//--------------Relaciones-----------

	private Collection<Message>			sendedMessages;
	private Collection<Message>			receivedMessages;
	private Collection<Folder>			folders;
	private Collection<SocialProfile>	socialProfiles;
	private UserAccount					userAccount;


	//-----------Getters y Setters------

	@Valid
	@NotNull
	@OneToMany(mappedBy = "sender")
	public Collection<Message> getSendedMessages() {
		return this.sendedMessages;
	}

	public void setSendedMessages(final Collection<Message> sendedMessages) {
		this.sendedMessages = sendedMessages;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "receiver")
	public Collection<Message> getReceivedMessages() {
		return this.receivedMessages;
	}

	public void setReceivedMessages(final Collection<Message> receivedMessages) {
		this.receivedMessages = receivedMessages;
	}

	@Valid
	@OneToOne(cascade = CascadeType.ALL, optional = false)
	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	@Valid
	@NotEmpty
	@NotNull
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "actor")
	public Collection<Folder> getFolders() {
		return this.folders;
	}

	public void setFolders(final Collection<Folder> folders) {
		this.folders = folders;
	}

	@Valid
	@NotNull
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "actor")
	public Collection<SocialProfile> getSocialProfiles() {
		return this.socialProfiles;
	}

	public void setSocialProfiles(final Collection<SocialProfile> socialProfiles) {
		this.socialProfiles = socialProfiles;
	}

	@NotBlank
	@NotNull
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getMiddleName() {
		return this.middleName;
	}

	public void setMiddleName(final String middleName) {
		this.middleName = middleName;
	}

	@NotBlank
	@NotNull
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	@URL
	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(final String photo) {
		this.photo = photo;
	}

	@NotBlank
	@Email
	@Pattern(regexp = "^(\\w+@(\\w+(\\.\\w*)*)?)|(\\w+( \\w+)* <\\w+@(\\w+(\\.\\w*)*)?>)$")
	@NotNull
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	@NotNull
	public Boolean getBanned() {
		return this.banned;
	}

	public void setBanned(final Boolean banned) {
		this.banned = banned;
	}

	@NotNull
	public Boolean getSuspicious() {
		return this.suspicious;
	}

	public void setSuspicious(final Boolean suspicious) {
		this.suspicious = suspicious;
	}

}
