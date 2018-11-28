
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
import domain.Endorsement;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class EndorsementServiceTest extends AbstractTest {

	//Service under test ------------------------------------------
	@Autowired
	private EndorsementService	endorsementService;


	//test-------------------------------------------------------------------

	@Test
	public void createCorrecto() {

		this.authenticate("customer1");
		final Endorsement endorsement = this.endorsementService.create();
		Assert.notNull(endorsement);
	}

	@Test
	public void findOneCorrecto() {
		Endorsement endorsement;
		final int idBusqueda = super.getEntityId("endorsement1");
		endorsement = this.endorsementService.findOne(idBusqueda);
		Assert.notNull(endorsement);
	}

	@Test
	public void findAllCorrecto() {
		Collection<Endorsement> endorsements;
		endorsements = this.endorsementService.findAll();
		Assert.notNull(endorsements);
		Assert.notEmpty(endorsements);
	}

	//	//@Test
	//	public void saveCorrecto(){
	//		final Endorsement endorsement, saved;
	//		final int endorsementId;
	//		endorn
	//	}
}
