package model;
public class Resume {

    private int resumeId;
    private int userId;
    private String fileName;
    private String filePath;
    private String skills;

    public Resume() {
    }

    public Resume(int resumeId, int userId, String fileName,
                  String filePath, String skills) {
        this.resumeId = resumeId;
        this.userId = userId;
        this.fileName = fileName;
        this.filePath = filePath;
        this.skills = skills;
    }

    public int getResumeId() {
        return resumeId;
    }

    public void setResumeId(int resumeId) {
        this.resumeId = resumeId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }
}