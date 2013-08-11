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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.yarn.config.annotation.yarn.EnableYarn;
import org.springframework.yarn.config.annotation.yarn.EnableYarn.Enable;
import org.springframework.yarn.config.annotation.yarn.SpringYarnConfigurerAdapter;
import org.springframework.yarn.config.annotation.yarn.builders.YarnClientBuilder;
import org.springframework.yarn.config.annotation.yarn.builders.YarnConfigBuilder;
import org.springframework.yarn.config.annotation.yarn.builders.YarnEnvironmentBuilder;
import org.springframework.yarn.config.annotation.yarn.builders.YarnResourceLocalizerBuilder;

@Configuration
@EnableYarn(enable=Enable.CLIENT)
public class ClientConfiguration extends SpringYarnConfigurerAdapter {

	@Value("${hd.fs}")
	private String fsUri;

	@Value("${hd.rm}")
	private String rmAddress;

	@Bean
	public PropertySourcesPlaceholderConfigurer myPropertySourcesPlaceholderConfigurer() {
		PropertySourcesPlaceholderConfigurer p = new PropertySourcesPlaceholderConfigurer();
		Resource[] resourceLocations = new Resource[]{new ClassPathResource("hadoop.properties")};
		p.setLocations(resourceLocations);
		return p;
	}

	@Override
	public void configure(YarnConfigBuilder config) throws Exception {
		config
			.fileSystemUri(fsUri)
			.resourceManagerAddress(rmAddress);
	}

	@Override
	public void configure(YarnResourceLocalizerBuilder localizer) throws Exception {
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
	public void configure(YarnEnvironmentBuilder environment) throws Exception {
		environment
			.withClasspath()
				.entry("./*");
	}

	@Override
	public void configure(YarnClientBuilder client) throws Exception {
		client
			.withMasterRunner()
				.clazz(MasterConfiguration.class);
	}

}
