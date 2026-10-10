
CREATE DATABASE IF NOT EXISTS online_job_portal;
USE online_job_portal;

CREATE TABLE IF NOT EXISTS users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'EMPLOYER', 'JOB_SEEKER') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS companies (
    company_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    company_name VARCHAR(150) NOT NULL,
    description TEXT,
    location VARCHAR(150),
    website VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS jobs (
    job_id INT PRIMARY KEY AUTO_INCREMENT,
    company_id INT NOT NULL,
    title VARCHAR(150) NOT NULL,
    description TEXT NOT NULL,
    location VARCHAR(150),
    salary DECIMAL(10,2),
    job_type VARCHAR(50),
    experience_required VARCHAR(100),
    posted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM('PENDING', 'APPROVED', 'REJECTED')
        NOT NULL DEFAULT 'PENDING',
    FOREIGN KEY (company_id) REFERENCES companies(company_id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS resumes (
    resume_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    resume_file VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS applications (
    application_id INT PRIMARY KEY AUTO_INCREMENT,
    job_id INT NOT NULL,
    user_id INT NOT NULL,
    resume_id INT,
    status ENUM(
        'APPLIED', 'UNDER_REVIEW', 'SHORTLISTED',
        'INTERVIEW', 'SELECTED', 'REJECTED'
    ) NOT NULL DEFAULT 'APPLIED',
    applied_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (job_id) REFERENCES jobs(job_id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (resume_id) REFERENCES resumes(resume_id)
        ON DELETE SET NULL ON UPDATE CASCADE,
    UNIQUE (job_id, user_id)
);
