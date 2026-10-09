package util;

import dao.EmployerDAO;
import dao.CompanyDAO;
import dao.JobDAO;
import model.User;
import model.Company;
import model.Job;

import java.util.List;

public class EmployerWorkflowTest {

    public static void main(String[] args) {
        try {
            EmployerDAO employerDAO = new EmployerDAO();
            CompanyDAO companyDAO = new CompanyDAO();
            JobDAO jobDAO = new JobDAO();

            String email = "employer.test@example.com";
            String password = "Test@123";

            // Step 1: Login or register the test employer.
            User employer = employerDAO.loginEmployer(email, password);

            if (employer == null) {
                boolean registered = employerDAO.registerEmployer(
                    "Test Employer", email, password
                );

                if (registered) {
                    employer = employerDAO.loginEmployer(email, password);
                }
            }

            if (employer == null) {
                System.out.println("Employer registration/login failed.");
                return;
            }

            int employerId = employer.getUserId();
            System.out.println("Employer login successful. ID: " + employerId);

            // Step 2: Find or create the company profile.
            Company company = companyDAO.getCompanyByEmployerId(employerId);

            if (company == null) {
                Company newCompany = new Company(
                    0,
                    "Test Company",
                    "Company created for testing",
                    "Greater Noida",
                    employerId
                );

                if (companyDAO.createCompany(newCompany)) {
                    System.out.println("Company profile created.");
                }

                company = companyDAO.getCompanyByEmployerId(employerId);
            } else {
                System.out.println("Existing company profile found.");
            }

            if (company == null) {
                System.out.println("Company profile could not be retrieved.");
                return;
            }

            // Step 3: Create a test job.
            Job job = new Job(
                0,
                "Java Developer - Workflow Test",
                "Temporary job created to test editing and deletion",
                "Greater Noida",
                30000.0,
                employerId
            );

            if (!jobDAO.createJob(job)) {
                System.out.println("Job creation failed.");
                return;
            }

            System.out.println("Test job created successfully.");

            // Step 4: Retrieve the created job and its database ID.
            List<Job> jobs = jobDAO.getJobsByEmployerId(employerId);
            Job createdJob = null;

            for (Job existingJob : jobs) {
                if ("Java Developer - Workflow Test".equals(
                        existingJob.getTitle())) {
                    if (createdJob == null ||
                        existingJob.getJobId() > createdJob.getJobId()) {
                        createdJob = existingJob;
                    }
                }
            }

            if (createdJob == null) {
                System.out.println("Created job could not be retrieved.");
                return;
            }

            int jobId = createdJob.getJobId();
            System.out.println("Retrieved test job ID: " + jobId);

            // Step 5: Update the test job.
            Job updatedJob = new Job(
                jobId,
                "Java Developer - Updated Workflow Test",
                "Updated temporary job description",
                "Noida",
                40000.0,
                employerId,
                createdJob.getStatus()
            );

            if (!jobDAO.updateJob(updatedJob)) {
                System.out.println("Job update failed.");
                return;
            }

            System.out.println("Job update successful.");

            // Step 6: Delete only the test job created above.
            if (jobDAO.deleteJob(jobId, employerId)) {
                System.out.println("Test job deletion successful.");
            } else {
                System.out.println("Test job deletion failed.");
            }

            System.out.println("Employer workflow test completed.");

        } catch (Exception e) {
            System.out.println("Test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
