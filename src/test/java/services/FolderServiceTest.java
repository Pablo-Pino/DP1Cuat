package services;

import java.util.Collection;
import java.util.List;
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



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional

public class FolderServiceTest extends AbstractTest{
	
	//Service under test ----------------------------------------

	@Autowired
	private FolderService	folderService;
	


	//------------------------------------------------------------

	@Test
	public void testCreate(Actor a) {
		final Folder f = this.folderService.create(a);
		Assert.notNull(f);
	}

	@Test
	public void testFindOneCorrecto() {
		Folder f;
		final int idBusqueda = super.getEntityId("folder1");
		f = this.folderService.findOne(idBusqueda);
		Assert.notNull(f);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindOneIncorrecto() {
		Folder f;

		final int idBusqueda = super.getEntityId("foldeh");
		f = this.folderService.findOne(idBusqueda);
		Assert.isNull(f);
	}

	@Test
	public void testFindAll() {
		Collection<Folder> folders;

		folders = this.folderService.findAll();
		Assert.notNull(folders);
		Assert.notEmpty(folders);
	}

	@Test
	public void testSaveFolderCorrecto() {
		Folder f;
		f = this.folderService.findOne(this.getEntityId("folder1Sponsor1"));
		Assert.notNull(f);
		f = this.folderService.save(f);
		Assert.isTrue(f.getName()=="inBox");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFolderIncorrecto() {
		Folder f;
		f = this.folderService.findOne(this.getEntityId("folder1Sponsor1"));
		Assert.notNull(f);
		f = this.folderService.save(f);
		Assert.isTrue(f.getName()=="uwu");
	}
	
	@Test
	public void testDelete() {
		Folder f;

		f = this.folderService.findOne(super.getEntityId("folder1Customer1"));
		this.folderService.delete(f);
		Assert.isNull(this.folderService.findOne(f.getId()));
	}
	
	@Test
	public void testfindFoldersByActor(Actor a) {
		this.authenticate("admin1");
		final List<Folder> result = this.folderService.findFoldersByActor(a);
		System.out.println("Lista de folders :" + result);
	}
	
	@Test
	public void testCreateSystemFolders(Actor a) {
		this.authenticate("admin1");
		final List<Folder> result = this.folderService.createSystemFolders(a);
		System.out.println("Creacion de System folders :" + result);
	}
	
	@Test
	public void testFolderByActorAndName(Actor a, String name) {
		this.authenticate("admin1");
		final Folder result = this.folderService.findFolderByActorAndName(a, name);
		System.out.println("La folder es :" + result);
	}
	
	
	
	
}