
package services;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

@Transactional
public interface ServiceObjectDependantI<S, T> {

	public S findOne(Integer id);
	public List<S> findAll(Collection<Integer> ids);
	public List<S> findAll();
	public List<S> findAll(T dependency);
	public S create(T dependency);
	public S save(S object);
	public void delete(S object);

}
