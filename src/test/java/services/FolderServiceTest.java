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



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional

public class FolderServiceTest extends AbstractTest{
	
	//Service under test ----------------------------------------

	@Autowired
	private FolderService	folderService;
	
	@Autowired
	private ActorService	actorService;


	//------------------------------------------------------------

	@Test
	public void testCreate() {
		final Actor a = this.actorService.findOne(13);
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
}