
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
import domain.Finder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class FinderServiceTest extends AbstractTest {

	//Service under test ------------------------------------------
	@Autowired
	private FinderService	finderService;


	//test-------------------------------------------------------------------

	@Test
	public void createCorrecto() {
		Finder finder;
		finder = this.finderService.create();
		Assert.notNull(finder);
	}

	@Test
	public void findOneCorrecto() {
		Finder res;
		final int resId = this.getEntityId("finder1");
		res = this.finderService.findOne(resId);
		Assert.notNull(res);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindOneIncorrecto() {
		Finder res;
		final int resId = this.getEntityId("fallo");
		res = this.finderService.findOne(resId);
		Assert.notNull(res);
	}

	@Test
	public void testFindAllCorrecto() {
		Collection<Finder> finders;

		finders = this.finderService.findAll();
		Assert.notNull(finders);
		Assert.notEmpty(finders); //porque sabemos que hemos creado algunos con el populate
	}

	@Test
	public void saveTestCorrecto() {
		Finder finder, saved;
		final int finderId = this.getEntityId("finder1");
		finder = this.finderService.findOne(finderId);
		Assert.notNull(finder);

		finder.setKeyword("nueva Key word");
		saved = this.finderService.save(finder);
		Assert.isTrue(saved.getKeyword().equals("nueva Key word"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void saveTestIncorrecto() {
		Finder finder, saved;
		final int finderId = this.getEntityId("finder1");
		finder = this.finderService.findOne(finderId);
		Assert.notNull(finder);

		finder.setKeyword(null);
		saved = this.finderService.save(finder);
		Assert.isNull(saved);
	}
	@Test
	public void deleteTestCorrecto() {
		Finder finder;
		final int finderId = this.getEntityId("finder2");
		finder = this.finderService.findOne(finderId);
		Assert.notNull(finder);

		this.finderService.delete(finder);
		Assert.isNull(finder = this.finderService.findOne(finderId));
	}
	@Test(expected = IllegalArgumentException.class)
	public void deleteTestIncorrecto() {
		Finder finder;
		final int finderId = this.getEntityId("fallo");
		finder = this.finderService.findOne(finderId);
		Assert.notNull(finder);

		this.finderService.delete(finder);
		Assert.isNull(finder = this.finderService.findOne(finderId));
	}

}
