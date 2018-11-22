
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.FixupTask;

@Repository
public interface FixupTaskRepository extends JpaRepository<FixupTask, Integer> {

	//------------------------Query C2----------------------------------------
	//The average, the minimum, the maximum, and the standard deviation of the number of applications per fix-up task

	@Query("select min(f.applications.size),max(f.applications.size),avg(f.applications.size),sqrt(sum(f.applications.size * f.applications.size) /count(f.applications.size) - (avg(f.applications.size) *avg(f.applications.size))) from FixupTask f")
	Double[] appsStats();
	//------------------------Query C3---------------------------------------
	//The average, the minimum, the maximum, and the standard deviation of the maximum price of the fix-up tasks.

	@Query("select min(f.maximumPrice),max(f.maximumPrice),avg(f.maximumPrice),sqrt(sum(f.maximumPrice * f.maximumPrice) /count(f.maximumPrice) - (avg(f.maximumPrice) *avg(f.maximumPrice))) from FixupTask f")
	Double[] maxFixupStaskStats();

	//------------------------Query B1---------------------------------------
	//The minimum, the maximum, the average, and the standard deviation of the number of complaints per fix-up task

	@Query("select min(t.complaints.size), max(t.complaints.size), avg(t.complaints.size), sqrt(sum(t.complaints.size*t.complaints.size)/count(t.complaints.size)-(avg(t.complaints.size)*avg(t.complaints.size))) from FixupTask t")
	Double[] complaintsStatsPerFixuptast();

	//------------------------Query B2---------------------------------------
	//The minimum, the maximum, the average, and the standard deviation of the number of notes per referee report

	@Query("select min(r.notes.size), max(r.notes.size), avg(r.notes.size), sqrt(sum(r.notes.size*r.notes.size)/count(r.notes.size)-(avg(r.notes.size)*avg(r.notes.size))) from Report r")
	Double[] notesStatsPerRefereeReport();

	//-----------------------------------------------------------------
}
