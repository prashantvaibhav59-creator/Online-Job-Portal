package model;

public class Job {

    private int jobId;
    private String title;
    private String description;
    private String location;
    private double salary;
    private int employerId;
    private String status;

    public Job() {
    }

    public Job(int jobId, String title, String description,
               String location, double salary, int employerId) {

        this.jobId = jobId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.salary = salary;
        this.employerId = employerId;
        this.status = "PENDING";
    }

    public Job(int jobId, String title, String description,
               String location, double salary, int employerId,
               String status) {

        this.jobId = jobId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.salary = salary;
        this.employerId = employerId;
        this.status = status;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getEmployerId() {
        return employerId;
    }

    public void setEmployerId(int employerId) {
        this.employerId = employerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}