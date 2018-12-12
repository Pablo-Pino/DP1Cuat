
package repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Endorsable;

@Repository
public interface EndorsableRepository extends GenericRepository<Endorsable> {

	@Query("select a from Endorsable a where a.userAccount.id = ?1")
	Endorsable findOneByUserAccount(int userAccountId);
}
