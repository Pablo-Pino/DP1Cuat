
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Folder;

@Repository
public interface FolderRepository extends GenericRepository<Folder> {

	@Query("select f from Folder f where f.actor.id = ?1")
	List<Folder> findFoldersByActor(Integer actorId);

	@Query("select f from Folder f where f.actor.id = ?1 and f.name = ?2")
	Folder findFolderByActorAndName(Integer actorId, String name);

	@Query("select distinct f from Folder f join f.messages m where f.actor.id = ?1 and m.id = ?2")
	Folder findFolderByActorAndMessage();
	
	@Query("select f from Folder f where f.parent.id = ?1")
	Collection<Folder> findByParentId(int parentId);

}
