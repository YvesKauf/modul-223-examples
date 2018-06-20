package ch.nyp.validation.validator;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserIdValidator implements ConstraintValidator<UserId, Long> {

	@Override
	public boolean isValid(Long value, ConstraintValidatorContext context) {
		//hier ist die eigene Valdierung drin, z.B. DB-Überprüfung.
		//Dieses Beispiel gibt true zurück wenn Zahl über 10;
		if (value != null && value > 10) {
			return true;
		}
		return false;
	}

}
