package de.hybris.training.facades.customer.impl;

import de.hybris.platform.commercefacades.customer.impl.DefaultCustomerFacade;
import de.hybris.platform.commercefacades.user.data.RegisterData;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.training.facades.customer.TrainingCustomerFacade;

public class DefaultTrainingCustomerFacade extends DefaultCustomerFacade implements TrainingCustomerFacade {

    @Override
    protected void setCommonPropertiesForRegister(final RegisterData registerData, final CustomerModel customerModel) {
        super.setCommonPropertiesForRegister(registerData, customerModel);

        customerModel.setCpf(registerData.getCpf());
        customerModel.setRg(registerData.getRg());
        customerModel.setBirthDate(registerData.getBirthDate());
    }
}
