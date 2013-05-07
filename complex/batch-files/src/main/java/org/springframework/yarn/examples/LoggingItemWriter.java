package org.springframework.yarn.examples;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;

public class LoggingItemWriter implements ItemWriter<String> {

	private final static Log log = LogFactory.getLog(LoggingItemWriter.class);

	@Override
	public void write(List<? extends String> items) throws Exception {
		for (String item : items) {
			log.info("writing: " + item);
		}
	}

}
