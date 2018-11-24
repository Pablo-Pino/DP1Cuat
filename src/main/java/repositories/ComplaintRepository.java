
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Complaint;
import domain.Referee;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Integer> {

	@Query("select Complaint c where c.referee == ?1")
	Collection<Complaint> SearchComplaintByReferee(Referee r);

	@Query("select Complaint c where c.referee IS NULL")
	Collection<Complaint> SearchComplaintWithoutReferee();
}
