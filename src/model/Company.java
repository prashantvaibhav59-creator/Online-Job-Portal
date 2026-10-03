package model;
public class Company {

    private int companyId;
    private String companyName;
    private String description;
    private String location;
    private int employerId;

    public Company() {
    }

    public Company(int companyId, String companyName,
                   String description, String location, int employerId) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.description = description;
        this.location = location;
        this.employerId = employerId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public int getEmployerId() {
        return employerId;
    }

    public void setEmployerId(int employerId) {
        this.employerId = employerId;
    }
}