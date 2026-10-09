package util;

import dao.ApplicationDAO;
import java.util.List;

public class ApplicationWorkflowTest {

    public static void main(String[] args) {
        try {
            ApplicationDAO dao = new ApplicationDAO();

            // Employer ID from the successful employer test
            int employerId = 1;

            // Step 1: Retrieve applications
            List<String[]> applicants =
                    dao.getApplicantsByEmployerId(employerId);

            System.out.println(
                    "Total applications received: " + applicants.size()
            );

            for (String[] applicant : applicants) {
                System.out.println("-------------------------");
                System.out.println("Application ID: " + applicant[0]);
                System.out.println("Job Title: " + applicant[2]);
                System.out.println("Applicant Name: " + applicant[4]);
                System.out.println("Applicant Email: " + applicant[5]);
                System.out.println("Status: " + applicant[6]);
            }

            // Step 2: Update status only if an application exists
            if (applicants.isEmpty()) {
                System.out.println(
                        "No applications found. Status update test skipped."
                );
            } else {
                String[] applicant = applicants.get(0);

                int applicationId = Integer.parseInt(applicant[0]);
                String currentStatus = applicant[6];

                System.out.println(
                        "Testing application ID: " + applicationId
                );
                System.out.println(
                        "Current status: " + currentStatus
                );

                // Avoid changing an already processed application.
                if ("APPLIED".equals(currentStatus)) {
                    boolean updated = dao.updateApplicationStatus(
                            applicationId,
                            employerId,
                            "UNDER_REVIEW"
                    );

                    if (updated) {
                        System.out.println(
                                "Application status updated to UNDER_REVIEW."
                        );
                    } else {
                        System.out.println(
                                "Status update failed. Check application ownership."
                        );
                    }
                } else {
                    System.out.println(
                            "Status update skipped because the application "
                            + "is already being processed."
                    );
                }
            }

            System.out.println("Application workflow test completed.");

        } catch (Exception e) {
            System.out.println("Test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
