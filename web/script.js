
const searchForm = document.querySelector(".search-form");
const keywordInput = document.querySelector("#keyword");
const locationInput = document.querySelector("#location");
const jobCards = document.querySelectorAll(".job-card");

// Create a message for search results
const jobsSection = document.querySelector("#jobs");
const resultMessage = document.createElement("p");

resultMessage.id = "search-message";
resultMessage.setAttribute("aria-live", "polite");
resultMessage.style.marginBottom = "20px";
resultMessage.style.padding = "12px";
resultMessage.style.borderRadius = "8px";
resultMessage.style.backgroundColor = "#eef4ff";
resultMessage.style.color = "#1d4ed8";
resultMessage.style.fontWeight = "600";

jobsSection.insertBefore(
    resultMessage,
    jobsSection.querySelector(".job-list")
);

searchForm.addEventListener("submit", function (event) {
    event.preventDefault();

    const keyword = keywordInput.value.trim().toLowerCase();
    const location = locationInput.value.trim().toLowerCase();

    let visibleJobs = 0;

    jobCards.forEach(function (card) {
        const jobText = card.textContent.toLowerCase();

        const matchesKeyword =
            keyword === "" || jobText.includes(keyword);

        const matchesLocation =
            location === "" || jobText.includes(location);

        if (matchesKeyword && matchesLocation) {
            card.style.display = "";
            visibleJobs++;
        } else {
            card.style.display = "none";
        }
    });

    resultMessage.textContent =
        visibleJobs === 0
            ? "No matching jobs found. Try another keyword or location."
            : visibleJobs + (visibleJobs === 1
                ? " job found."
                : " jobs found.");

    jobsSection.scrollIntoView({ behavior: "smooth" });
});

const categoryCards = document.querySelectorAll("[data-category]");

categoryCards.forEach(function (categoryCard) {
    categoryCard.addEventListener("click", function () {
        const category = categoryCard.dataset.category;

        keywordInput.value = "";
        locationInput.value = "";

        jobCards.forEach(function (card) {
            const jobText = card.textContent.toLowerCase();

            let matches = false;

            if (category === "technology") {
                matches =
                    jobText.includes("java") ||
                    jobText.includes("web developer");
            } else if (category === "data") {
                matches =
                    jobText.includes("ai/ml") ||
                    jobText.includes("machine learning") ||
                    jobText.includes("python");
            }

            card.style.display = matches ? "" : "none";
        });

        const visibleJobs = Array.from(jobCards).filter(function (card) {
            return card.style.display !== "none";
        }).length;

        resultMessage.textContent = visibleJobs === 0
            ? "No sample jobs are available in this category yet."
            : visibleJobs + (visibleJobs === 1
                ? " job found in this category."
                : " jobs found in this category.");

        jobsSection.scrollIntoView({ behavior: "smooth" });
    });
});