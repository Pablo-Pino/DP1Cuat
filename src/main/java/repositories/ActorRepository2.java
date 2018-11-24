
package repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;

@Repository
public interface ActorRepository2 extends GenericRepository<Actor> {

	@Query("select a from Actor a where a.userAccount.id = ?1")
	Actor findOneByUserAccount(int userAccountId);

}
