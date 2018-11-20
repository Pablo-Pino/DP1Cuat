
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.HandyWorker;

@Repository
public interface HandyWorkerRepository extends JpaRepository<HandyWorker, Integer> {

	@Query("select h from HandyWorker h join h.applications a where a.status='ACCEPTED' group by h.id order by sum(a.fixupTask.complaints.size) desc")
	Collection<HandyWorker> getTop3HandyWorkerWithMoreComplaints();

}
