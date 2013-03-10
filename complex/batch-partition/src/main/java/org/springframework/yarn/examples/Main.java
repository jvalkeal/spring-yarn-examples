package org.springframework.yarn.examples;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.yarn.client.YarnClient;

/**
 * Main class for batch-partition example.
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
            System.out.println("Submitting batch-partition example");
            YarnClient client = (YarnClient) context.getBean("yarnClient");
            client.submitApplication();
            System.out.println("Submitted batch-partition example");
        } catch (Throwable e) {
            log.error("Error in main method", e);
        } finally {
            if (context != null) {
                context.close();
            }
        }

    }

}
