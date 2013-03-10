package org.springframework.yarn.examples;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

/**
 * Example tasklet.
 * 
 * @author Janne Valkealahti
 *
 */
public class PrintTasklet implements Tasklet {

    private static final Log log = LogFactory.getLog(PrintTasklet.class);
    
    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public ExitStatus execute() throws Exception {
        log.info("execute1: " + message);
        System.out.print(message);
        return ExitStatus.COMPLETED;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("execute2: " + message);
        return null;
    }
    
}