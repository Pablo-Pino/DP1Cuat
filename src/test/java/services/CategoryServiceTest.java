
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
import domain.Category;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class CategoryServiceTest extends AbstractTest {

	//Service under test ----------------------------------------

	@Autowired
	private CategoryService	categoryService;


	//-------------Test----------------------------------
	@Test
	public void testCreate() {
		final Category a = this.categoryService.create();
		Assert.notNull(a);
	}

	@Test
	public void testFindOneCorrecto() {
		Category cat;
		final int idBusqueda = super.getEntityId("categoryCarpentry");
		cat = this.categoryService.findOne(idBusqueda);
		Assert.notNull(cat);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindOneIncorrecto() {
		Category cat;

		final int idBusqueda = super.getEntityId("ksksjkkj");
		cat = this.categoryService.findOne(idBusqueda);
		Assert.isNull(cat);
	}

	@Test
	public void testFindAll() {
		Collection<Category> categories;

		categories = this.categoryService.findAll();
		Assert.notNull(categories);
		Assert.notEmpty(categories); //porque sabemos que hemos creado algunos con el populate
	}

	@Test
	public void testSaveCategoryCorrecto() {
		Category cat;
		cat = this.categoryService.findOne(this.getEntityId("categoryWindowRepair"));
		Assert.notNull(cat);
		cat.setName("Tuercas y tornillos");
		cat = this.categoryService.save(cat);
		Assert.isTrue(cat.getName().equals("Tuercas y tornillos"));

	}

	@Test(expected = IllegalArgumentException.class)
	public void testSaveCategoryIncorrecto() {
		Category cat;
		cat = this.categoryService.findOne(this.getEntityId("categoryWindowRepair"));
		Assert.notNull(cat);
		cat.setName("Tuercas y tornillos");
		cat = this.categoryService.save(cat);
		Assert.isTrue(cat.getName().equals("INCORRECTO"));

	}

	@Test
	public void testDelete() {
		Category cat;
		cat = this.categoryService.findOne(this.getEntityId("categoryWindowRepair"));
		this.categoryService.deleteCategories(cat);
	}

	//---------------------- Pruebas , no olvidar borrar lo que sobre------------------------

	@Test(expected = IllegalArgumentException.class)
	public void testDeleteCategoryRoot() {
		Category cat;
		cat = this.categoryService.findOne(this.getEntityId("categoryRoot"));
		this.categoryService.deleteCategories(cat);
	}

}
