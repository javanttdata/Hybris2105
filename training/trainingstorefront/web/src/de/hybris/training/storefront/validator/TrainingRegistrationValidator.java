/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package de.hybris.training.storefront.validator;

import de.hybris.platform.acceleratorstorefrontcommons.constants.WebConstants;
import de.hybris.platform.acceleratorstorefrontcommons.forms.RegisterForm;
import de.hybris.platform.acceleratorstorefrontcommons.forms.validation.RegistrationValidator;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.training.storefront.forms.TrainingRegisterForm;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Validates registration forms.
 */
@Component("trainingRegistrationValidator")
public class TrainingRegistrationValidator extends RegistrationValidator {
	private static final String CPF_REGEX = "cpf.regex" ;

	public static final String EMAIL_REGEX = "email.regex";
		@Resource(name = "configurationService")
	private ConfigurationService configurationService;

	@Override
	public boolean supports(final Class<?> aClass)
	{
		return RegisterForm.class.equals(aClass);
	}

	@Override
	public void validate(final Object object, final Errors errors)
	{
		final TrainingRegisterForm registerForm = (TrainingRegisterForm) object;
		final String titleCode = registerForm.getTitleCode();
		final String firstName = registerForm.getFirstName();
		final String lastName = registerForm.getLastName();
		final String email = registerForm.getEmail();
		final String pwd = registerForm.getPwd();
		final String checkPwd = registerForm.getCheckPwd();
		final String cpf = registerForm.getCpf();
		final String rg = registerForm.getRg();
		final Date birthDate = registerForm.getBirthDate();
		final boolean termsCheck = registerForm.isTermsCheck();

		validateTitleCode(errors, titleCode);
		validateName(errors, firstName, "firstName", "register.firstName.invalid");
		validateName(errors, lastName, "lastName", "register.lastName.invalid");

		if (StringUtils.length(firstName) + StringUtils.length(lastName) > 255)
		{
			errors.rejectValue("lastName", "register.name.invalid");
			errors.rejectValue("firstName", "register.name.invalid");
		}

		validateEmail(errors, email);
		validatePassword(errors, pwd);
		comparePasswords(errors, pwd, checkPwd);
		validateCpf(errors, cpf);
		validateRg(errors, rg);
		validateBirthDate(errors, birthDate);
		validateTermsAndConditions(errors, termsCheck);
	}

	protected void validateRg(final Errors errors, final String rg)
	{
		if (StringUtils.isBlank(rg))
		{
			errors.rejectValue("rg", "register.rg.invalid");
		}

	}

	protected void validateCpf(final Errors errors, final String cpf)
	{
		if (StringUtils.isBlank(cpf) || !validatePatternCpf(cpf)){
			errors.rejectValue("cpf", "register.cpf.invalid");
		}
	}

	private boolean validatePatternCpf (final String cpf){
		final String regex = this.configurationService.getConfiguration().getString(CPF_REGEX);

		final Matcher matcher = Pattern.compile(regex).matcher(cpf);
		return matcher.matches();
	}

	protected void validateBirthDate(final Errors errors, final Date birthDate){
		if (birthDate == null){
			errors.rejectValue("birthDate", "register.birthDate.invalid");
		}else{
			final Calendar c = Calendar.getInstance();
			c.add(Calendar.YEAR, -18);

			if (c.getTime().before(birthDate)){
				errors.rejectValue("birthDate", "register.birthDate.invalid");
			}
		}
	}

	protected void comparePasswords(final Errors errors, final String pwd, final String checkPwd)
	{
		if (StringUtils.isNotEmpty(pwd) && StringUtils.isNotEmpty(checkPwd) && !StringUtils.equals(pwd, checkPwd))
		{
			errors.rejectValue("checkPwd", "validation.checkPwd.equals");
		}
		else
		{
			if (StringUtils.isEmpty(checkPwd))
			{
				errors.rejectValue("checkPwd", "register.checkPwd.invalid");
			}
		}
	}

	protected void validatePassword(final Errors errors, final String pwd)
	{
		if (StringUtils.isEmpty(pwd))
		{
			errors.rejectValue("pwd", "register.pwd.invalid");
		}
		else if (StringUtils.length(pwd) < 6 || StringUtils.length(pwd) > 255)
		{
			errors.rejectValue("pwd", "register.pwd.invalid");
		}
	}

	protected void validateEmail(final Errors errors, final String email)
	{
		if (StringUtils.isEmpty(email))
		{
			errors.rejectValue("email", "register.email.invalid");
		}
		else if (StringUtils.length(email) > 255 || !validateEmailAddress(email))
		{
			errors.rejectValue("email", "register.email.invalid");
		}
	}

	protected void validateName(final Errors errors, final String name, final String propertyName, final String property)
	{
		if (StringUtils.isBlank(name))
		{
			errors.rejectValue(propertyName, property);
		}
		else if (StringUtils.length(name) > 255)
		{
			errors.rejectValue(propertyName, property);
		}
	}

	protected void validateTitleCode(final Errors errors, final String titleCode)
	{
		if (StringUtils.isNotEmpty(titleCode) && StringUtils.length(titleCode) > 255)
		{
			errors.rejectValue("titleCode", "register.title.invalid");
		}
	}

	protected boolean validateEmailAddress(final String email)
	{
		final Matcher matcher = Pattern.compile(configurationService.getConfiguration().getString(WebConstants.EMAIL_REGEX))
				.matcher(email);
		return matcher.matches();
	}

	protected void validateTermsAndConditions(final Errors errors, final boolean termsCheck)
	{
		if (!termsCheck)
		{
			errors.rejectValue("termsCheck", "register.terms.not.accepted");
		}
	}
}
