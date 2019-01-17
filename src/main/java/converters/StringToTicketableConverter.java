
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.TicketableRepository;
import domain.Ticketable;

@Component
@Transactional
public class StringToTicketableConverter implements Converter<String, Ticketable> {

	@Autowired
	private TicketableRepository	repository;


	@Override
	public Ticketable convert(final String s) {
		Ticketable res;
		int id;

		try {
			if (!StringUtils.isEmpty(s))
				res = null;
			else {
				id = Integer.valueOf(s);
				res = this.repository.findOne(id);
			}
		} catch (final Throwable t) {
			throw new IllegalArgumentException(t);
		}
		return res;
	}

}
