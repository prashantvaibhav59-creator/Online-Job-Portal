-- =========================================================
-- ONLINE JOB PORTAL DATABASE
-- Member 2: Database + JDBC + Employer Module
-- =========================================================

-- Create database
CREATE DATABASE IF NOT EXISTS online_job_portal;

-- Select database
USE online_job_portal;


-- =========================================================
-- 1. USERS TABLE
-- Stores login/account information for all users
-- =========================================================

CREATE TABLE IF NOT EXISTS users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'EMPLOYER', 'JOB_SEEKER') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


-- =========================================================
-- 2. COMPANIES TABLE
-- Stores company information for employers
-- =========================================================

CREATE TABLE IF NOT EXISTS companies (
    company_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    company_name VARCHAR(150) NOT NULL,
    description TEXT,
    location VARCHAR(150),
    website VARCHAR(255),

    CONSTRAINT fk_company_user
        FOREIGN KEY (user_id)
        REFERENCES users(user_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);


-- =========================================================
-- 3. JOBS TABLE
-- Stores jobs posted by companies
-- =========================================================

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

    CONSTRAINT fk_job_company
        FOREIGN KEY (company_id)
        REFERENCES companies(company_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);


-- =========================================================
-- 4. RESUMES TABLE
-- Stores resume information of job seekers
-- =========================================================

CREATE TABLE IF NOT EXISTS resumes (
    resume_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    resume_file VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_resume_user
        FOREIGN KEY (user_id)
        REFERENCES users(user_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);


-- =========================================================
-- 5. APPLICATIONS TABLE
-- Stores job applications submitted by job seekers
-- =========================================================

CREATE TABLE IF NOT EXISTS applications (
    application_id INT PRIMARY KEY AUTO_INCREMENT,
    job_id INT NOT NULL,
    user_id INT NOT NULL,
    resume_id INT,
    status ENUM(
        'APPLIED',
        'UNDER_REVIEW',
        'SHORTLISTED',
        'INTERVIEW',
        'SELECTED',
        'REJECTED'
    ) NOT NULL DEFAULT 'APPLIED',
    applied_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_application_job
        FOREIGN KEY (job_id)
        REFERENCES jobs(job_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT fk_application_user
        FOREIGN KEY (user_id)
        REFERENCES users(user_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT fk_application_resume
        FOREIGN KEY (resume_id)
        REFERENCES resumes(resume_id)
        ON DELETE SET NULL
        ON UPDATE CASCADE,

    CONSTRAINT unique_job_application
        UNIQUE (job_id, user_id)
);


-- =========================================================
-- DATABASE CREATED SUCCESSFULLY
-- =========================================================s