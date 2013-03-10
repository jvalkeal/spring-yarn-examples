package org.springframework.yarn.examples;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobInterruptedException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.step.NoSuchStepException;
import org.springframework.batch.core.step.StepLocator;
import org.springframework.yarn.container.YarnContainer;
import org.springframework.yarn.integration.IntegrationAppmasterServiceClient;

/**
 * Spring Yarn container implementation which executes a Spring Batch 
 * partitioned step.
 * 
 * @author Janne Valkealahti
 *
 */
public class BatchPartitionBeanExample implements YarnContainer {

    private static final Log log = LogFactory.getLog(BatchPartitionBeanExample.class);
    
    private IntegrationAppmasterServiceClient serviceClient;
    private StepLocator stepLocator;
    private JobExplorer jobExplorer;
    
    public BatchPartitionBeanExample(IntegrationAppmasterServiceClient serviceClient, StepLocator stepLocator, JobExplorer jobExplorer) {
        super();
        this.serviceClient = serviceClient;
        this.stepLocator = stepLocator;
        this.jobExplorer = jobExplorer;
    }

    public BatchPartitionBeanExample(IntegrationAppmasterServiceClient serviceClient) {
        super();
        this.serviceClient = serviceClient;
    }
    
    @Override
    public void run() {
        log.info("Starting message sender");
        log.info("Service client is " + serviceClient);        
        log.info("amservice.port: " + System.getenv("amservice.port"));
        log.info("amservice.address: " + System.getenv("amservice.address"));
        log.info("amservice.batch.stepname: " + System.getenv("amservice.batch.stepname"));
        log.info("amservice.batch.jobexecutionid: " + System.getenv("amservice.batch.jobexecutionid"));
        log.info("amservice.batch.stepexecutionid: " + System.getenv("amservice.batch.stepexecutionid"));
        
        
        Long jobExecutionId = Long.parseLong(System.getenv("amservice.batch.jobexecutionid"));
        Long stepExecutionId = Long.parseLong(System.getenv("amservice.batch.stepexecutionid"));
        String stepName = System.getenv("amservice.batch.stepname");

        log.info("Requesting StepExecution: " + jobExecutionId + " / " + stepExecutionId);
        StepExecution stepExecution = jobExplorer.getStepExecution(jobExecutionId, stepExecutionId);
        if (stepExecution == null) {
            throw new NoSuchStepException("No StepExecution could be located for this request: ");
        }
        log.info("Got StepExecution: " + stepExecution);
        
        Step step = stepLocator.getStep(stepName);
        log.info("Located step: " + step);
        if (step == null) {
            throw new NoSuchStepException(String.format("No Step with name [%s] could be located.", stepName));
        }

        try {
            log.info("Executing step: " + step + " / " + stepExecution);
            step.execute(stepExecution);
        }
        catch (JobInterruptedException e) {
            log.error("error executing step 1", e);
            stepExecution.setStatus(BatchStatus.STOPPED);
            // The receiver should update the stepExecution in repository
        }
        catch (Throwable e) {
            log.error("error executing step 2", e);
            stepExecution.addFailureException(e);
            stepExecution.setStatus(BatchStatus.FAILED);
            // The receiver should update the stepExecution in repository
        }
        
        log.info("Hello from BatchPartitionBeanExample");
    }

}
