package org.springframework.yarn.examples;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MultiContextBeanExample implements org.springframework.yarn.container.YarnContainer {

    private static final Log log = LogFactory.getLog(MultiContextBeanExample.class);

    @Override
    public void run() {
        log.info("Hello from MultiContextBeanExample");
    }

}
