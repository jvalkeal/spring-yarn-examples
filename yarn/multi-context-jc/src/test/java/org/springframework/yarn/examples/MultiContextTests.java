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

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Scanner;

import org.apache.hadoop.yarn.api.records.YarnApplicationState;
import org.junit.Test;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.test.annotation.Timed;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.yarn.config.annotation.yarn.EnableYarn;
import org.springframework.yarn.config.annotation.yarn.EnableYarn.Enable;
import org.springframework.yarn.config.annotation.yarn.SpringYarnConfigurerAdapter;
import org.springframework.yarn.config.annotation.yarn.builders.YarnClientBuilder;
import org.springframework.yarn.config.annotation.yarn.builders.YarnEnvironmentBuilder;
import org.springframework.yarn.config.annotation.yarn.builders.YarnResourceLocalizerBuilder;
import org.springframework.yarn.test.context.MiniYarnCluster;
import org.springframework.yarn.test.context.YarnDelegatingSmartContextLoader;
import org.springframework.yarn.test.junit.AbstractYarnClusterTests;

/**
 * Tests for multi context example. We're checking that
 * application status is ok and log files looks
 * what is expected.
 *
 * @author Janne Valkealahti
 *
 */
@ContextConfiguration(loader=YarnDelegatingSmartContextLoader.class)
@MiniYarnCluster
public class MultiContextTests extends AbstractYarnClusterTests {

	@Test
	@Timed(millis=70000)
	public void testAppSubmission() throws Exception {
		YarnApplicationState state = submitApplicationAndWait();
		assertNotNull(state);
		assertTrue(state.equals(YarnApplicationState.FINISHED));

		File workDir = getYarnCluster().getYarnWorkDir();

		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		String locationPattern = "file:" + workDir.getAbsolutePath() + "/**/*.std*";
		Resource[] resources = resolver.getResources(locationPattern);

		assertThat(resources, notNullValue());
		assertThat(resources.length, is(4));

		for (Resource res : resources) {
			File file = res.getFile();
			if (file.getName().endsWith("stdout")) {
				// there has to be some content in stdout file
				assertThat(file.length(), greaterThan(0l));
				if (file.getName().equals("Container.stdout")) {
					Scanner scanner = new Scanner(file);
					String content = scanner.useDelimiter("\\A").next();
					scanner.close();
					// this is what container will log in stdout
					assertThat(content, containsString("Hello from MultiContextBeanExample"));
				}
			} else if (file.getName().endsWith("stderr")) {
				// can't have anything in stderr files
				assertThat(file.length(), is(0l));
			}
		}
	}

	@Configuration
	@EnableYarn(enable=Enable.CLIENT)
	static class Config extends SpringYarnConfigurerAdapter {

		@Override
		protected void configure(YarnResourceLocalizerBuilder localizer) throws Exception {
			localizer
				.withCopy()
					.copy("file:build/dependency-libs/*", "/lib/", false)
					.copy("file:build/libs/*", "/app/multi-context-jc/", false)
					.and()
				.withHdfs()
					.hdfs("/app/multi-context-jc/*.jar")
					.hdfs("/lib/*.jar");
		}

		@Override
		protected void configure(YarnEnvironmentBuilder environment) throws Exception {
			environment
				.withClasspath()
					.entry("./*");
		}

		@Override
		protected void configure(YarnClientBuilder client) throws Exception {
			client
				.withMasterRunner()
					.clazz(MasterConfiguration.class);
		}

	}

}
