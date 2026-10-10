
document.addEventListener("DOMContentLoaded", async function () {
    const navActions = document.querySelector(".nav-actions");
    const jobList = document.querySelector(".job-list");
    const searchForm = document.querySelector(".search-form");
    const keywordInput = document.querySelector("#keyword");
    const locationInput = document.querySelector("#location");
    const jobsSection = document.querySelector("#jobs");
    const resultMessage = document.createElement("p");

    // Check login status and account role
    if (navActions) {
        try {
            const response = await fetch("session-status");
            const session = await response.json();

            if (session.loggedIn) {
                if (session.role === "EMPLOYER") {
                    navActions.innerHTML = `
                        <a href="index.html" class="login-link">Home</a>
                        <a href="pages/employer-dashboard.html"
                           class="login-link">Employer Dashboard</a>
                        <a href="logout" class="signup-btn">Log Out</a>
                    `;
                } else {
                    navActions.innerHTML = `
                        <a href="index.html" class="login-link">Home</a>
                        <a href="logout" class="signup-btn">Log Out</a>
                    `;
                }
            }
        } catch (error) {
            console.error("Could not check login status:", error);
        }
    }

    // Load approved jobs before enabling search and categories
    if (!jobList) return;

    try {
        const response = await fetch("approved-jobs");

        if (!response.ok) {
            throw new Error("Failed to load jobs");
        }

        jobList.innerHTML = await response.text();
    } catch (error) {
        console.error("Job loading error:", error);
        jobList.innerHTML =
            "<p>Could not load jobs. Please refresh.</p>";
        return;
    }

    // Use the newly loaded database job cards
    const jobCards = jobList.querySelectorAll(".job-card");

    resultMessage.id = "search-message";
    resultMessage.setAttribute("aria-live", "polite");
    resultMessage.style.marginBottom = "20px";
    resultMessage.style.padding = "12px";
    resultMessage.style.borderRadius = "8px";
    resultMessage.style.backgroundColor = "#eef4ff";
    resultMessage.style.color = "#1d4ed8";
    resultMessage.style.fontWeight = "600";

    if (jobsSection) {
        jobsSection.insertBefore(resultMessage, jobList);
    }

    // Search jobs by keyword and location
    if (searchForm && keywordInput && locationInput) {
        searchForm.addEventListener("submit", function (event) {
            event.preventDefault();

            const keyword = keywordInput.value.trim().toLowerCase();
            const location = locationInput.value.trim().toLowerCase();
            let visibleJobs = 0;

            jobCards.forEach(function (card) {
                const text = card.textContent.toLowerCase();
                const matchesKeyword =
                    !keyword || text.includes(keyword);
                const matchesLocation =
                    !location || text.includes(location);

                card.style.display =
                    matchesKeyword && matchesLocation ? "" : "none";

                if (matchesKeyword && matchesLocation) {
                    visibleJobs++;
                }
            });

            resultMessage.textContent = visibleJobs === 0
                ? "No matching jobs found. Try another keyword or location."
                : visibleJobs + (visibleJobs === 1
                    ? " job found."
                    : " jobs found.");

            jobsSection.scrollIntoView({ behavior: "smooth" });
        });
    }

    // Filter jobs by category
    document.querySelectorAll("[data-category]").forEach(
        function (categoryCard) {
            categoryCard.addEventListener("click", function () {
                const category = categoryCard.dataset.category;
                let visibleJobs = 0;

                jobCards.forEach(function (card) {
                    const text = card.textContent.toLowerCase();
                    let matches = false;

                    if (category === "technology") {
                        matches = text.includes("java")
                            || text.includes("web developer")
                            || text.includes("software")
                            || text.includes("developer");
                    } else if (category === "data") {
                        matches = text.includes("ai/ml")
                            || text.includes("machine learning")
                            || text.includes("python")
                            || text.includes("data");
                    } else if (category === "design") {
                        matches = text.includes("design")
                            || text.includes("ui/ux");
                    } else if (category === "business") {
                        matches = text.includes("business")
                            || text.includes("marketing")
                            || text.includes("management");
                    }

                    card.style.display = matches ? "" : "none";

                    if (matches) {
                        visibleJobs++;
                    }
                });

                resultMessage.textContent = visibleJobs === 0
                    ? "No approved jobs are available in this category yet."
                    : visibleJobs + (visibleJobs === 1
                        ? " job found in this category."
                        : " jobs found in this category.");

                jobsSection.scrollIntoView({ behavior: "smooth" });
            });
        }
    );
});
