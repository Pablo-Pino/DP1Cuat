package services;

import java.util.Collection;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Actor;
import domain.Folder;
import domain.Message;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional

public class MessageServiceTest extends AbstractTest{
	
	//Service under test ----------------------------------------

	@Autowired
	private MessageService	messageService;
	
	//Supporting Service
	
	@Autowired
	private FolderService	folderService;
	
	@Autowired
	private ActorService actorService;


	//------------------------------------------------------------

	@Test
	public void testCreate() {
		final Folder f =this.folderService.findOne(this.getEntityId("folder1Customer1"));
		this.authenticate("customer1");
		final Message m = this.messageService.create(f);
		Assert.notNull(m);
	}

	@Test
	public void testFindOneCorrecto() {
		Message m;
		final int idBusqueda = super.getEntityId("message1");
		m = this.messageService.findOne(idBusqueda);
		Assert.notNull(m);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindOneIncorrecto() {
		Message m;
		final int idBusqueda = super.getEntityId("mesageh");
		m = this.messageService.findOne(idBusqueda);
		Assert.isNull(m);
	}

	@Test
	public void testFindAll() {
		Collection<Message> messages;

		messages = this.messageService.findAll();
		Assert.notNull(messages);
		Assert.notEmpty(messages);
	}

	@Test
	public void saveTestCorrecto() {
		Message m, saved;
		this.authenticate("customer1");
		final int mId = this.getEntityId("message1");
		m = this.messageService.findOne(mId);
		Assert.notNull(m);

		m.setSubject("uwu");
		saved = this.messageService.save(m);
		Assert.isTrue(saved.getSubject().equals("uwu"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void saveTestIncorrecto() {
		Message m;
		Message saved;
		final int mrId = this.getEntityId("message1");
		m = this.messageService.findOne(mrId);
		Assert.notNull(m);

		m.setBody(null);
		saved = this.messageService.save(m);
		Assert.isNull(saved);
	}
	
	@Test
	public void deleteTestCorrecto() {
		Message m;
		Actor a;

		this.authenticate("handywoker1");
		a = actorService.findPrincipal();
		final int mId = this.getEntityId("message1");
		m = this.messageService.findOne(mId);
		Assert.notNull(m);

		this.messageService.delete(m);
		System.out.println(m);
		//Assert.isNull(this.messageService.findOne(mId));
		//Comprobar si esta en la papelera
		Assert.isTrue(!(this.folderService.findFolderByActorAndName(a, "trashbox").getMessages().contains(m)));
	}

	@Test(expected = IllegalArgumentException.class)
	public void deleteTestIncorrecto() {
		Message m;
		final int mId = this.getEntityId("Error intencionado");
		m = this.messageService.findOne(mId);
		Assert.notNull(m);

		this.messageService.delete(m);
		Assert.isNull(m = this.messageService.findOne(mId));
	}

}