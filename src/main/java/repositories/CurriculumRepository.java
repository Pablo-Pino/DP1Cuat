
package repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Curriculum;

@Repository
public interface CurriculumRepository extends GenericRepository<Curriculum> {

	@Query("select c from Curriculum c where c.handyWorker.id = ?1")
	Curriculum findByHandyWorkerId(int handyWorkerId);
	
}
