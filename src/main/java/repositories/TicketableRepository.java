
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Ticketable;

public interface TicketableRepository extends JpaRepository<Ticketable, Integer> {

}
