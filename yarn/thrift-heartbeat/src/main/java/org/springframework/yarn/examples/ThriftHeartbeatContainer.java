/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.yarn.examples;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.yarn.container.AbstractYarnContainer;
import org.springframework.yarn.container.YarnContainer;
import org.springframework.yarn.thrift.hb.HeartbeatAppmasterServiceClient;
import org.springframework.yarn.thrift.hb.gen.NodeInfo;

/**
 * Simple {@link YarnContainer} example which exit
 * after a sleeping period. This container is used
 * together with thrift based heartbeat system and
 * we do sleep to give some time for the service
 * to call appmaster and notify its existence.
 *
 * @author Janne Valkealahti
 *
 */
public class ThriftHeartbeatContainer extends AbstractYarnContainer implements ApplicationContextAware {

	private static final Log log = LogFactory.getLog(ThriftHeartbeatContainer.class);

	private ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}

	@Override
	protected void runInternal() {
		log.info("Hello from ThriftHeartbeatContainer");

		log.info("Setting default node info for heartbeat to start");
		HeartbeatAppmasterServiceClient serviceClient = context.getBean(HeartbeatAppmasterServiceClient.class);
		serviceClient.setNodeInfo(new NodeInfo());

		log.info("Sleeping 10 seconds");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
		}

		log.info("Exiting with ok");
		System.exit(0);
	}

}
