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

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.yarn.api.records.ContainerId;
import org.apache.hadoop.yarn.api.records.ContainerLaunchContext;
import org.springframework.yarn.YarnSystemConstants;
import org.springframework.yarn.am.AppmasterService;
import org.springframework.yarn.am.ContainerLauncherInterceptor;
import org.springframework.yarn.am.StaticEventingAppmaster;
import org.springframework.yarn.am.container.AbstractLauncher;
import org.springframework.yarn.thrift.hb.HeartbeatAppmasterService;
import org.springframework.yarn.thrift.hb.HeartbeatMasterClient;
import org.springframework.yarn.thrift.hb.HeartbeatNode;
import org.springframework.yarn.thrift.hb.HeartbeatNode.NodeState;

/**
 * Custom appmaster example for manually handling failed containers,
 * using container associated data for passing some state
 * into re-launched container.
 *
 * @author Janne Valkealahti
 *
 */
public class ThriftHeartbeatAppmaster extends StaticEventingAppmaster
	implements ContainerLauncherInterceptor, HeartbeatMasterClient {

	private final static Log log = LogFactory.getLog(ThriftHeartbeatAppmaster.class);

	private int restartsRemaining = 1;

	/** Queue for failed containers */
//	private Queue<ContainerId> failed = new ConcurrentLinkedQueue<ContainerId>();

	@Override
	protected void onInit() throws Exception {
		super.onInit();
		if(getLauncher() instanceof AbstractLauncher) {
			((AbstractLauncher)getLauncher()).addInterceptor(this);
		}
		((HeartbeatAppmasterService)getAppmasterService())
			.addHeartbeatMasterClientListener(this);
	}

	@Override
	public ContainerLaunchContext preLaunch(ContainerLaunchContext context) {
		log.info("preLaunch: " + context);

		AppmasterService service = getAppmasterService();
		if (service != null) {
			int port = service.getPort();
			String address = service.getHost();
			Map<String, String> env = new HashMap<String, String>(context.getEnvironment());
			env.put(YarnSystemConstants.AMSERVICE_PORT, Integer.toString(port));
			env.put(YarnSystemConstants.AMSERVICE_HOST, address);
			context.setEnvironment(env);
			return context;
		} else {
			return context;
		}

//		ContainerId containerId = context.getContainerId();
//		Integer attempt = 1;
//		ContainerId failedContainerId = failed.poll();
//		Object assignedData = (failedContainerId != null ? getContainerAssing().getAssignedData(failedContainerId) : null);
//		if (assignedData != null) {
//			attempt = (Integer) assignedData;
//			attempt += 1;
//		}
//		getContainerAssing().assign(containerId, attempt);
//
//		Map<String, String> env = new HashMap<String, String>(context.getEnvironment());
//		env.put("customappmaster.attempt", attempt.toString());
//		context.setEnvironment(env);
//		return context;
	}

	@Override
	protected boolean onContainerFailed(ContainerId containerId) {
		log.info("onContainerFailed: " + containerId);
//		failed.add(containerId);
//		getAllocator().allocateContainers(1);
		return true;
	}

	@Override
	public void nodeUp(HeartbeatNode node, NodeState state) {
		log.info("nodeUp");
	}

	@Override
	public void nodeWarn(HeartbeatNode node, NodeState state) {
		log.info("nodeWarn");
	}

	@Override
	public void nodeDead(HeartbeatNode node, NodeState state) {
		log.info("nodeDead");
		if (restartsRemaining-- > 0) {
			getAllocator().allocateContainers(1);
		}
	}

}
