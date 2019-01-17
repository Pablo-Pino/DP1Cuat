
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Ticketable;

@Component
@Transactional
public class TicketableToStringConverter implements Converter<Ticketable, String> {

	@Override
	public String convert(final Ticketable t) {
		String result;
		if (t == null)
			result = null;
		else
			result = String.valueOf(t.getId());

		return result;
	}

}
