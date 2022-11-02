/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package de.hybris.training.storefront.controllers.cms.training;

import de.hybris.platform.servicelayer.exceptions.AttributeNotSupportedException;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.training.core.hgbrasil.WeatherInfoService;
import de.hybris.training.core.hgbrasil.response.WeatherInfoResultsResponse;
import de.hybris.training.core.hgbrasil.response.WeatherInfoResponse;
import de.hybris.training.core.model.WeatherInfoComponentModel;
import de.hybris.training.storefront.controllers.ControllerConstants;
import de.hybris.training.storefront.controllers.cms.AbstractAcceleratorCMSComponentController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller("WeatherInfoComponentController")
@RequestMapping(value = ControllerConstants.Actions.Cms.WeatherInfoComponent)
public class WeatherinfoComponentController extends AbstractAcceleratorCMSComponentController<WeatherInfoComponentModel>
{
	@Resource(name = "modelService")
	private ModelService modelService;

	@Resource(name = "weatherInfoService")
	private WeatherInfoService weatherInfoService;

	@Override
	protected void fillModel(final HttpServletRequest request, final Model model, final WeatherInfoComponentModel component){

		for (final String property : getCmsComponentService().getReadableEditorProperties(component)) {
			try {
				final Object value = modelService.getAttributeValue(component, property);
				model.addAttribute(property, value);
			} catch (final AttributeNotSupportedException ignore) {
				// ignore
			}

		}

		final WeatherInfoResponse weatherInfo = this.weatherInfoService.getWeatherInfo();
		if(weatherInfo != null) {
			final WeatherInfoResultsResponse weatherInfoResults = weatherInfo.getResults();
			model.addAttribute("result", weatherInfoResults);
		}
	}
}
