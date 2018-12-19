
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
import domain.Warranty;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class WarrantyServiceTest extends AbstractTest {

	//Service under test ------------------------------------------

	@Autowired
	private WarrantyService	warrantyService;

	@Test
	public void testCreate() {
		Warranty warranty;

		warranty = this.warrantyService.create();
		Assert.notNull(warranty);
	}

	@Test
	public void testFindOne() {
		Warranty warranty;

		warranty = this.warrantyService.findOne(super.getEntityId("warranty1"));
		Assert.notNull(warranty);
	}
	@Test(expected = IllegalArgumentException.class)
	public void testFindOneError() {
		Warranty warranty;

		warranty = this.warrantyService.findOne(super.getEntityId("o1"));
		Assert.notNull(warranty);
	}
	@Test
	public void testFindAll() {

		final Collection<Warranty> warrantys = this.warrantyService.findAll();
		Assert.notNull(warrantys);
	}

	@Test
	public void testSave() {
		Warranty warranty;

		warranty = this.warrantyService.findOne(super.getEntityId("warranty1"));
		Assert.notNull(warranty);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSaveError() {
		Warranty warranty;

		warranty = this.warrantyService.findOne(super.getEntityId("manolo"));
		Assert.notNull(warranty);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDeleteWithTasks() {
		Warranty warranty;

		warranty = this.warrantyService.findOne(super.getEntityId("warranty1"));
		this.warrantyService.delete(warranty);
		Assert.isNull(this.warrantyService.findOne(warranty.getId()));
	}

	@Test
	public void testDelete() {
		Warranty warranty;

		warranty = this.warrantyService.findOne(super.getEntityId("warranty1"));
		this.warrantyService.delete(warranty);
		Assert.isNull(this.warrantyService.findOne(warranty.getId()));
	}
}
