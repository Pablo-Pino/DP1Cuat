
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Message;

@Repository
public interface MessageRepository extends GenericRepository<Message> {

	@Query("select m from Message m where m.sender.id = ?1")
	Collection<Message> findSendedMessages(int actorId);
	
	@Query("select m from Message m where m.receiver.id = ?1")
	Collection<Message> findReceivedMessages(int actorId);
	
}
