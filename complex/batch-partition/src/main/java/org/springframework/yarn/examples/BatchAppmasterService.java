package org.springframework.yarn.examples;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.yarn.am.GenericRpcMessage;
import org.springframework.yarn.am.RpcMessage;
import org.springframework.yarn.batch.repository.JobRepositoryRemoteService;
import org.springframework.yarn.integration.ip.mind.MindAppmasterService;
import org.springframework.yarn.integration.ip.mind.MindRpcMessageHolder;

/**
 * Application master service implementation which is used for
 * remote Spring Batch steps to talk to Job Repository. Simply
 * passes requests to {@link JobRepositoryRemoteService}.
 * 
 * @author Janne Valkealahti
 *
 */
public class BatchAppmasterService extends MindAppmasterService {

    private static final Log log = LogFactory.getLog(BatchAppmasterService.class);
    
    private JobRepositoryRemoteService jobRepositoryRemoteService;
    
    public MindRpcMessageHolder handleMessage(MindRpcMessageHolder holder) {
        
        if(log.isDebugEnabled()) {
            log.debug("Incoming MindRpcMessageHolder: " + holder);
        }
        
        GenericRpcMessage<MindRpcMessageHolder> outRpcMessage = new GenericRpcMessage<MindRpcMessageHolder>(holder);
        RpcMessage<?> inMessage = jobRepositoryRemoteService.get(outRpcMessage);        
        MindRpcMessageHolder out = (MindRpcMessageHolder) inMessage.getBody();
        
        if(log.isDebugEnabled()) {
            log.debug("Outgoing MindRpcMessageHolder: " + holder);
        }
        
        return out;
    }

    public void setJobRepositoryRemoteService(JobRepositoryRemoteService jobRepositoryRemoteService) {
        this.jobRepositoryRemoteService = jobRepositoryRemoteService;
    }

}
