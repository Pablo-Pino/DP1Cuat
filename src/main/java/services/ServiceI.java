
package services;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

@Transactional
public interface ServiceI<S> {

	public S findOne(Integer id);
	public List<S> findAll(Collection<Integer> ids);
	public List<S> findAll();
	public S create();
	public S save(S object);
	public void delete(S object);

}
