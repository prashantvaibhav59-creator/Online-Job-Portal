package model;
public class Application {

    private int applicationId;
    private int jobId;
    private int userId;
    private String status;

    public Application() {
    }

    public Application(int applicationId, int jobId, int userId, String status) {
        this.applicationId = applicationId;
        this.jobId = jobId;
        this.userId = userId;
        this.status = status;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}