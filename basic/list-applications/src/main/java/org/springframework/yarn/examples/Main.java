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
