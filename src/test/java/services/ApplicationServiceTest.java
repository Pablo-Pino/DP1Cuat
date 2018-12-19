
package services;

import java.util.Collection;
import java.util.Map;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Application;
import domain.Folder;
import domain.Message;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ApplicationServiceTest extends AbstractTest {

	//Service under test ----------------------------------------

	@Autowired
	private ApplicationService	applicationService;
	
	//------------------------------------------------------------

	@Autowired 
	private FolderService folderService;
	@Autowired
	private MessageService messageService;
	
	@Test
	public void testCreate() {
		final Application a = this.applicationService.create();
		Assert.notNull(a);
	}

	@Test
	public void testFindOneCorrecto() {
		Application app;
		final int idBusqueda = super.getEntityId("application1");
		app = this.applicationService.findOne(idBusqueda);
		Assert.notNull(app);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindOneIncorrecto() {
		Application app;

		final int idBusqueda = super.getEntityId("tussamApp");
		app = this.applicationService.findOne(idBusqueda);
		Assert.isNull(app);
	}

	@Test
	public void testFindAll() {
		Collection<Application> apps;

		apps = this.applicationService.findAll();
		Assert.notNull(apps);
		Assert.notEmpty(apps); //porque sabemos que hemos creado algunos con el populate
	}

	@Test
	public void testSaveApplicationCorrecto() {
		Application app;
		app = this.applicationService.findOne(this.getEntityId("application1"));
		this.authenticate(app.getHandyWorker().getUserAccount().getUsername());
		Assert.notNull(app);
		app.setPrice(14.55);
		app.setWorkerComments("WABBADALABALUPLUP");
		app = this.applicationService.save(app);
		this.applicationService.flush();
		// Quitar esto de mostrar en consola antes de entregar
		for (final Folder f : this.folderService.findAllByActor(app.getHandyWorker()))
			for (final Message m : this.messageService.findByFolder(f))
				System.out.println(m.getBody());
		for (final Folder f : this.folderService.findAllByActor(app.getFixupTask().getCustomer()))
			for (final Message m : this.messageService.findByFolder(f))
				System.out.println(m.getBody());
		Assert.isTrue(app.getPrice() == (14.55));
		Assert.isTrue(app.getWorkerComments().equals("WABBADALABALUPLUP"));
		this.unauthenticate();
	}
	@Test(expected = IllegalArgumentException.class)
	public void testSaveApplicationIncorrecto() {
		Application app;
		app = this.applicationService.findOne(this.getEntityId("application1"));
		Assert.notNull(app);
		app.setPrice(14.55);
		app.setWorkerComments("WABBADALABALUPLUP");
		app = this.applicationService.save(app);
		Assert.isTrue(app.getPrice() == (1.45));
		Assert.isTrue(app.getWorkerComments().equals("mal"));

	}

	@Test
	public void testApplicationPriceStats() {
		this.authenticate("admin1");
		final Map<String, Double> result = this.applicationService.applicationPriceStats();
		System.out.println("Las variables de aplicaciones por precio son:" + result);

	}
	@Test
	public void testApplicationPendingRatio() {
		this.authenticate("admin1");
		final Map<String, Double> result = this.applicationService.pendingRatio();
		System.out.println("El ratio de las apps PENDING es :" + result);
	}

	@Test
	public void testApplicationAcceptedRatio() {
		this.authenticate("admin1");
		final Map<String, Double> result = this.applicationService.acceptedRatio();
		System.out.println("El ratio de las apps ACCEPTED es :" + result);
	}

	@Test
	public void testApplicationRejectedRatio() {
		this.authenticate("admin1");
		final Map<String, Double> result = this.applicationService.appsRejectedRatio();
		System.out.println("El ratio de las apps REJECTED es :" + result);
	}
	@Test(expected = IllegalArgumentException.class)
	public void testChangeStatusMal() {
		Application app;
		final String status = "REJECTED";
		app = this.applicationService.findOne(this.getEntityId("application1"));
		this.applicationService.changeStatus(app, status);
		app = this.applicationService.save(app);
		Assert.isTrue(app.getStatus().equals("REJECTED"));

	}
	@Test
	public void testChangeStatusBien() {
		Application app;
		final String status = "ACCEPTED";
		app = this.applicationService.findOne(this.getEntityId("application7"));
		this.authenticate(app.getHandyWorker().getUserAccount().getUsername());
		this.applicationService.changeStatus(app, status);
		app = this.applicationService.save(app);
		Assert.isTrue(app.getStatus().equals("ACCEPTED"));

	}

}
