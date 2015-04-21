/*
 * Hibernate Validator, declare and validate application constraints
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package org.hibernate.validator.internal.constraintvalidators.bv.future;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.Calendar;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Future;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.hibernate.validator.internal.util.IgnoreJava6Requirement;

/**
 * Check that the {@code java.time.Year} passed is in the future.
 *
 * @author Khalid Alqinyah
 */
@IgnoreJava6Requirement
public class FutureValidatorForYear implements ConstraintValidator<Future, Year> {

	@Override
	public void initialize(Future constraintAnnotation) {

	}

	@Override
	public boolean isValid(Year value, ConstraintValidatorContext context) {
		// null values are valid
		if ( value == null ) {
			return true;
		}

		Calendar now = context.unwrap( HibernateConstraintValidatorContext.class )
				.getTimeProvider()
				.getCurrentTime();

		Year currentYear = Year.of(
				LocalDateTime.ofInstant( now.toInstant(), now.getTimeZone().toZoneId() )
						.getYear()
		);

		return value.isAfter( currentYear );
	}
}