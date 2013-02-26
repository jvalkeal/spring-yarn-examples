package org.springframework.yarn.examples;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.yarn.container.YarnContainer;

public class MultiContextBeanExample implements YarnContainer {

    private static final Log log = LogFactory.getLog(MultiContextBeanExample.class);

    @Override
    public void run() {
        log.info("Hello from MultiContextBeanExample");
    }

}
