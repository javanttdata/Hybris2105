/*
 * Copyright (c) 2020 SAP SE or an SAP affiliate company. All rights reserved.
 */
package br.com.training.webservices.jalo;

import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.extension.ExtensionManager;
import org.apache.log4j.Logger;
import br.com.training.webservices.constants.TrainingwebservicesConstants;

public class TrainingwebservicesManager extends GeneratedTrainingwebservicesManager
{
	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger( TrainingwebservicesManager.class.getName() );
	
	public static final TrainingwebservicesManager getInstance()
	{
		final ExtensionManager em = JaloSession.getCurrentSession().getExtensionManager();
		return (TrainingwebservicesManager) em.getExtension(TrainingwebservicesConstants.EXTENSIONNAME);
	}
	
}
