
package repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Finder;

@Repository
public interface FinderRepository extends GenericRepository<Finder> {

	@Query("select f from Finder f where f.handyWorker.id = ?1")
	Finder findByHandyWorkerId(int handyWorkerId);
	
}
