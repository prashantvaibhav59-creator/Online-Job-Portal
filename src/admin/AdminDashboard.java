package admin;

import model.Job;
import model.User;

public class AdminDashboard {

    public static void main(String[] args) {

        AdminService adminService = new AdminService();

        User admin = new User(
                1,
                "Admin",
                "admin@jobportal.com",
                "admin123",
                "ADMIN"
        );

        User user1 = new User(
                2,
                "Rahul",
                "rahul@gmail.com",
                "1234",
                "JOBSEEKER"
        );

        User user2 = new User(
                3,
                "Vansh",
                "vansh@gmail.com",
                "1234",
                "EMPLOYER"
        );

        adminService.addUser(admin);
        adminService.addUser(user1);
        adminService.addUser(user2);

        Job job1 = new Job(
                101,
                "Java Developer",
                "Java backend developer",
                "Noida",
                600000,
                3
        );

        Job job2 = new Job(
                102,
                "Frontend Developer",
                "HTML CSS JavaScript developer",
                "Delhi",
                500000,
                3
        );

        adminService.addJob(job1);
        adminService.addJob(job2);

        System.out.println("===== ADMIN DASHBOARD =====");

        System.out.println("Total Users: "
                + adminService.getTotalUsers());

        System.out.println("Total Jobs: "
                + adminService.getTotalJobs());

        System.out.println("Pending Jobs: "
                + adminService.getPendingJobs());

        System.out.println("Approved Jobs: "
                + adminService.getApprovedJobs());

        System.out.println("Rejected Jobs: "
                + adminService.getRejectedJobs());

        System.out.println();
        System.out.println("===== ALL USERS =====");

        for (User user : adminService.getAllUsers()) {

            System.out.println(
                    user.getUserId() + " | "
                    + user.getName() + " | "
                    + user.getEmail() + " | "
                    + user.getRole()
            );
        }

        System.out.println();
        System.out.println("===== ALL JOBS =====");

        for (Job job : adminService.getAllJobs()) {

            System.out.println(
                    job.getJobId() + " | "
                    + job.getTitle() + " | "
                    + job.getLocation() + " | "
                    + job.getStatus()
            );
        }

        System.out.println();
        System.out.println("Approving Job 101...");

        adminService.approveJob(101);

        System.out.println("Rejecting Job 102...");

        adminService.rejectJob(102);

        System.out.println();
        System.out.println("===== UPDATED JOBS =====");

        for (Job job : adminService.getAllJobs()) {

            System.out.println(
                    job.getJobId() + " | "
                    + job.getTitle() + " | "
                    + job.getStatus()
            );
        }
    }
}