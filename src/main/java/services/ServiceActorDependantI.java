
package services;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import domain.Actor;

@Transactional
public interface ServiceActorDependantI<S> {

	public S findOne(Integer id);
	public List<S> findAll(Collection<Integer> ids);
	public List<S> findAll();
	public List<S> findAllByActor(Actor a);
	public S create(Actor a);
	public S save(S object);
	public void delete(S object);

}
