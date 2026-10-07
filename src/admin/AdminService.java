package admin;

import java.util.ArrayList;
import java.util.List;

import model.Job;
import model.User;

public class AdminService {

    private final List<User> users = new ArrayList<>();
    private final List<Job> jobs = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
    }

    public void addJob(Job job) {
        jobs.add(job);
    }

    public boolean adminLogin(String email, String password) {

        for (User user : users) {

            if (user.getEmail().equals(email)
                    && user.getPassword().equals(password)
                    && "ADMIN".equalsIgnoreCase(user.getRole())) {

                return true;
            }
        }

        return false;
    }

    public List<User> getAllUsers() {
        return users;
    }

    public List<Job> getAllJobs() {
        return jobs;
    }

    public boolean approveJob(int jobId) {

        for (Job job : jobs) {

            if (job.getJobId() == jobId) {
                job.setStatus("APPROVED");
                return true;
            }
        }

        return false;
    }

    public boolean rejectJob(int jobId) {

        for (Job job : jobs) {

            if (job.getJobId() == jobId) {
                job.setStatus("REJECTED");
                return true;
            }
        }

        return false;
    }

    public int getTotalUsers() {
        return users.size();
    }

    public int getTotalJobs() {
        return jobs.size();
    }

    public int getPendingJobs() {

        int count = 0;

        for (Job job : jobs) {

            if ("PENDING".equalsIgnoreCase(job.getStatus())) {
                count++;
            }
        }

        return count;
    }

    public int getApprovedJobs() {

        int count = 0;

        for (Job job : jobs) {

            if ("APPROVED".equalsIgnoreCase(job.getStatus())) {
                count++;
            }
        }

        return count;
    }

    public int getRejectedJobs() {

        int count = 0;

        for (Job job : jobs) {

            if ("REJECTED".equalsIgnoreCase(job.getStatus())) {
                count++;
            }
        }

        return count;
    }
}