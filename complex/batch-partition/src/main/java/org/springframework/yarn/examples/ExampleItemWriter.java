package org.springframework.yarn.examples;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

/**
 * Example item writer used in chunk.
 * 
 * @author Janne Valkealahti
 *
 */
@Component("writer")
public class ExampleItemWriter implements ItemWriter<Object> {

    private static final Log log = LogFactory.getLog(ExampleItemWriter.class);

    public void write(List<? extends Object> data) throws Exception {
        log.info(data);
    }

}
