package de.hybris.training.facades.customer.impl;

import de.hybris.platform.commercefacades.customer.impl.DefaultCustomerFacade;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commercefacades.user.data.RegisterData;
import de.hybris.platform.commerceservices.customer.DuplicateUidException;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.training.core.customer.TrainingCustomerAccountService;
import de.hybris.training.facades.customer.TrainingCustomerFacade;
import org.springframework.util.Assert;

import javax.annotation.Resource;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNullStandardMessage;

public class DefaultTrainingCustomerFacade extends DefaultCustomerFacade implements TrainingCustomerFacade {

    private TrainingCustomerAccountService trainingCustomerAccountService;

    @Override
    protected void setCommonPropertiesForRegister(final RegisterData registerData, final CustomerModel customerModel) {
        super.setCommonPropertiesForRegister(registerData, customerModel);

        customerModel.setCpf(registerData.getCpf());
        customerModel.setRg(registerData.getRg());
        customerModel.setBirthDate(registerData.getBirthDate());
    }

    @Override
    public void updateProfile(final CustomerData customerData) throws DuplicateUidException
    {
        validateDataBeforeUpdate(customerData);

        final String name = getCustomerNameStrategy().getName(customerData.getFirstName(), customerData.getLastName());

        final CustomerModel customer = getCurrentSessionCustomer();
        customer.setOriginalUid(customerData.getDisplayUid());
        trainingCustomerAccountService.updateProfile(customer, customerData.getTitleCode(), name, customerData.getUid(),
                customerData.getCpf(), customerData.getRg(), customerData.getBirthDate());
    }

    protected void validateDataBeforeUpdate(final CustomerData customerData)
    {
        validateParameterNotNullStandardMessage("customerData", customerData);
        Assert.hasText(customerData.getFirstName(), "The field [FirstName] cannot be empty");
        Assert.hasText(customerData.getLastName(), "The field [LastName] cannot be empty");
        Assert.hasText(customerData.getUid(), "The field [Uid] cannot be empty");
    }

    public void setTrainingCustomerAccountService(TrainingCustomerAccountService trainingCustomerAccountService) {
        this.trainingCustomerAccountService = trainingCustomerAccountService;
    }
}
