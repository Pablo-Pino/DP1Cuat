
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.HandyWorker;

@Repository
public interface HandyWorkerRepository extends JpaRepository<HandyWorker, Integer> {

	//@Query("select h from HandyWorker h join h.applications a where a.status='ACCEPTED' group by h.id order by sum(a.fixupTask.complaints.size) desc")
	//Collection<HandyWorker> getTop3HandyWorkerWithMoreComplaints();
	
	//@Query("select a.handyWorker from Application a where a.status='ACCEPTED' group by a.handyWorker order by select count(c) from Complaint c where c.fixupTask.id = a.fixupTask.id desc")
	//Collection<HandyWorker> getTop3HandyWorkerWithMoreComplaints();
	
	@Query("select h from HandyWorker h, Complaint c group by h " +
			"having exists(select a from Application a where a.status = 'ACCEPTED' and a.handyWorker.id = h.id and " +
			"c.fixuptask.id = a.fixupTask.id) order by count(c)")
	Collection<HandyWorker> getTop3HandyWorkerWithMoreComplaints();
	
	//@Query("select h from HandyWorker h where (select count(a)*1.0 from Application a where a.status = 'ACCEPTED' and a.handyWorker.id = h.id)/(select (count(a1)*1.0)/(select count(h1)*1.0 from HandyWorker h1) from Application a1 where a1.status = 'ACCEPTED') >= 1.1 order by h.applications.size)")
	//Collection<HandyWorker> listHandyWorkerApplication();
	
	@Query("select h from HandyWorker h where " +
			"(((select count(a)*1.0 from Application a where a.handyWorker.id = h.id and a.status = 'ACCEPTED') / (select count(a1)*1.0 from Application a1 where a1.handyWorker.id = h.id)) / " +
			"((select count(a2)*1.0 from Application a2 where a2.status = 'ACCEPTED') / (select count(a3)*1.0 from Application a3))) >= 1.1 order by count(a1)")
	Collection<HandyWorker> listHandyWorkerApplication();

}
