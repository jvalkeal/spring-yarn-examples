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

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.yarn.api.records.ApplicationReport;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.yarn.client.YarnClient;

/**
 * Main class for list-applications example.
 *
 * @author Janne Valkealahti
 *
 */
public class Main {

	private static final Log log = LogFactory.getLog(Main.class);

	public static void main(String args[]) {

		ConfigurableApplicationContext context = null;

		try {
			context = new ClassPathXmlApplicationContext("application-context.xml");

			YarnClient client = (YarnClient) context.getBean("yarnClient");
			List<ApplicationReport> applications = client.listApplications();

			System.out.println("Listing Applications: \n\n");

			for (ApplicationReport a : applications) {
				System.out.println(a.toString());
				System.out.println("\n");
			}

		} catch (Throwable e) {
			log.error("Error in main method", e);
		} finally {
			if (context != null) {
				context.close();
			}
		}

	}

}
