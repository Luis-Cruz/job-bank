package module.jobBank.domain.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import module.jobBank.domain.JobOffer;
import module.jobBank.domain.Student;
import module.workflow.domain.ProcessFile;

public class OfferCandidacyBean implements Serializable {

    private Student student;
    private JobOffer jobOffer;
    private ProcessFile processFile;
    private List<ProcessFile> attachFiles;

    public OfferCandidacyBean() {
        setAttachFiles(new ArrayList<ProcessFile>());
    }

    public List<ProcessFile> getAttachFiles() {
        return attachFiles;
    }

    public void setAttachFiles(List<ProcessFile> attachFiles) {
        this.attachFiles = attachFiles;
    }

    public boolean addAttachFiles(ProcessFile attachFile) {
        return this.attachFiles.add(attachFile);
    }

    public boolean removeAttachFiles(ProcessFile attachFile) {
        return this.attachFiles.remove(attachFile);
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public JobOffer getJobOffer() {
        return jobOffer;
    }

    public void setJobOffer(JobOffer jobOffer) {
        this.jobOffer = jobOffer;
    }

    public void clearFiles() {
        attachFiles = new ArrayList<ProcessFile>();
    }

    public void setProcessFile(ProcessFile processFile) {
        this.processFile = processFile;
    }

    public ProcessFile getProcessFile() {
        return processFile;
    }

}
