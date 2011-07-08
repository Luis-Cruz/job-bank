package module.jobBank.domain.beans;

import java.io.Serializable;
import java.util.Collection;

import module.jobBank.domain.JobOfferProcess;
import module.jobBank.domain.JobOfferState;

public class JobOfferViewBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private JobOfferState processesState;
    private Collection<JobOfferProcess> processes;
    private int processesCount;


    public JobOfferViewBean() {
	super();
    }


    public void setProcessesState(JobOfferState processesState) {
	this.processesState = processesState;
    }


    public JobOfferState getProcessesState() {
	return processesState;
    }


    public void setProcesses(Collection<JobOfferProcess> processes) {
	this.processes = processes;
    }


    public Collection<JobOfferProcess> getProcesses() {
	return processes;
    }


    public void setProcessesCount(int processesCount) {
	this.processesCount = processesCount;
    }


    public int getProcessesCount() {
	return processesCount;
    }
}
