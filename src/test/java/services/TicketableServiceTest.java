
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
import domain.Ticketable;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class TicketableServiceTest extends AbstractTest {

	//Service under test ------------------------------------------

	@Autowired
	private TicketableService	ticketableService;


	@Test
	public void testFindOne() {
		Ticketable ticketable;

		ticketable = this.ticketableService.findOne(super.getEntityId("ticketable1"));
		Assert.notNull(ticketable);
	}

	@Test
	public void testFindAll() {

		final Collection<Ticketable> ticketables = this.ticketableService.findAll();
		Assert.notNull(ticketables);
	}

	@Test
	public void testSave() {
		Ticketable ticketable;

		ticketable = this.ticketableService.findOne(super.getEntityId("ticketable1"));
		Assert.notNull(ticketable);
	}

	@Test
	public void testDelete() {
		Ticketable ticketable;

		ticketable = this.ticketableService.findOne(super.getEntityId("ticketable1"));
		this.ticketableService.delete(ticketable);
		Assert.isNull(this.ticketableService.findOne(ticketable.getId()));
	}
}
