
package repositories;

import org.springframework.stereotype.Repository;

import domain.Message;

@Repository
public interface MessageRepository extends GenericRepository<Message> {

}
