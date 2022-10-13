package de.hybris.training.facades.populators;

import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;


public class TrainingCustomerPopulator implements Populator<CustomerModel, CustomerData> {

    @Override
    public void populate(CustomerModel source, CustomerData target) throws ConversionException {

        target.setCpf(source.getCpf());
        target.setRg(source.getRg());
        target.setBirthDate(source.getBirthDate());
    }
}
