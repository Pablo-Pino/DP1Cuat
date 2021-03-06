
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Application;

@Component
@Transactional
public class ApplicationToStringConverter implements Converter<Application, String> {

	@Override
	public String convert(final Application app) {
		String result;
		if (app == null)
			result = null;
		else
			result = String.valueOf(app.getId());

		return result;
	}

}
