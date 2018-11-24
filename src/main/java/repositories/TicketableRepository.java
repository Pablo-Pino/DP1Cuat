
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Ticketable;

@Repository
public interface TicketableRepository extends JpaRepository<Ticketable, Integer> {

}
