package de.hybris.training.core.customer.impl;

import de.hybris.platform.commerceservices.customer.DuplicateUidException;
import de.hybris.platform.commerceservices.customer.impl.DefaultCustomerAccountService;
import de.hybris.platform.commerceservices.event.UpdatedProfileEvent;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.training.core.customer.TrainingCustomerAccountService;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNullStandardMessage;

public class DefaultTrainingCustomerAccountService extends DefaultCustomerAccountService implements TrainingCustomerAccountService {


    @Override
    public void updateProfile(final CustomerModel customerModel, final String titleCode, final String name, final String login, final String cpf, final String rg, final Date birthDate)
            throws DuplicateUidException {

        validateParameterNotNullStandardMessage("customerModel", customerModel);

        customerModel.setUid(login);
        customerModel.setName(name);
        customerModel.setCpf(cpf);
        customerModel.setRg(rg);
        customerModel.setBirthDate(birthDate);

        if (StringUtils.isNotBlank(titleCode))
        {
            customerModel.setTitle(getUserService().getTitleForCode(titleCode));
        }
        else
        {
            customerModel.setTitle(null);
        }
        internalSaveCustomer(customerModel);
        getEventService().publishEvent(initializeEvent(new UpdatedProfileEvent(), customerModel));

    }
}
