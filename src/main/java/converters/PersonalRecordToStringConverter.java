
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.PersonalRecord;

@Component
@Transactional
public class PersonalRecordToStringConverter implements Converter<PersonalRecord, String> {

	@Override
	public String convert(final PersonalRecord pr) {
		String result;
		if (pr == null)
			result = null;
		else
			result = String.valueOf(pr.getId());

		return result;
	}

}
