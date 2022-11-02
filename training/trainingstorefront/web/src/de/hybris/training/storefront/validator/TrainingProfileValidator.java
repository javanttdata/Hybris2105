/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package de.hybris.training.storefront.validator;

import de.hybris.platform.acceleratorstorefrontcommons.forms.UpdateProfileForm;
import de.hybris.platform.acceleratorstorefrontcommons.forms.validation.ProfileValidator;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.training.storefront.forms.TrainingUpdateProfileForm;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component("trainingProfileValidator")
public class TrainingProfileValidator extends ProfileValidator {

	private static final String CPF_REGEX = "cpf.regex" ;
	@Resource(name = "configurationService")
	private ConfigurationService configurationService;

	@Override
	public boolean supports(final Class<?> aClass)
	{
		return UpdateProfileForm.class.equals(aClass);
	}

	@Override
	public void validate(final Object object, final Errors errors)
	{
		final TrainingUpdateProfileForm updateProfileForm = (TrainingUpdateProfileForm) object;

		final String cpf = updateProfileForm.getCpf();
		final String rg = updateProfileForm.getRg();
		final Date birthDate = updateProfileForm.getBirthDate();

		validateCpf(errors, cpf);
		validateRg(errors, rg);
		validateBirthDate(errors, birthDate);
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

}
