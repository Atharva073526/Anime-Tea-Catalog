// ===== Anime Tea - Vanilla JS Frontend =====

const API_BASE = "http://localhost:8080";

// DOM references
const grid = document.getElementById("anime-grid");
const statusEl = document.getElementById("status");
const searchForm = document.getElementById("search-form");
const searchInput = document.getElementById("search-input");
const modal = document.getElementById("modal");
const modalCard = document.getElementById("modal-card");
const modalBackdrop = document.getElementById("modal-backdrop");

// ===== Helpers =====

// Build a safe poster element, falling back when no image is available
function createPoster(src, alt, fallbackClass) {
    if (src) {
        const img = document.createElement("img");
        img.src = src;
        img.alt = alt;
        img.loading = "lazy";
        img.className = fallbackClass === "anime-postter" ? "anime-postter" : "modal-postter";
        img.addEventListener("error", () => {
            const wrap = img.parentElement;
            wrap.innerHTML = "";
            const note = document.createElement("div");
            note.className = "anime-postter-fallback";
            note.textContent = "No Image";
            wrap.appendChild(note);
        });
        return img;
    }
    const note = document.createElement("div");
    note.className = "anime-postter-fallback";
    note.textContent = "No Image";
    return note;
}

// Determine whether a trailer URL is valid (not null/empty/...watch?v=null)
function hasValidTrailer(url) {
    if (!url) return false;
    if (url.trim() === "") return false;
    if (url.includes("watch?v=null")) return false;
    if (url.endsWith("=null")) return false;
    return true;
}

// Show a status message (loading / error / empty)
function showStatus(message, isLoading = false, isError = false) {
    grid.innerHTML = "";
    statusEl.hidden = false;
    statusEl.innerHTML = "";
    statusEl.classList.toggle("status--error", isError);
    if (isLoading) {
        const spinner = document.createElement("div");
        spinner.className = "spinner";
        statusEl.appendChild(spinner);
    }
    const text = document.createElement("p");
    text.textContent = message;
    statusEl.appendChild(text);
    if (isError) {
        const retry = document.createElement("button");
        retry.className = "status-retry";
        retry.textContent = "Retry";
        retry.addEventListener("click", loadHomepage);
        statusEl.appendChild(retry);
    }
}

// Hide the status area
function hideStatus() {
    statusEl.hidden = true;
    statusEl.innerHTML = "";
}

// ===== API =====

async function fetchAnime(endpoint) {
    const response = await fetch(`${API_BASE}${endpoint}`);
    if (!response.ok) {
        throw new Error(`Request failed: ${response.status}`);
    }
    const data = await response.json();
    return Array.isArray(data) ? data : [];
}

// ===== Rendering =====

function renderCards(animeList) {
    hideStatus();
    grid.innerHTML = "";

    if (!animeList.length) {
        showStatus("No anime found.");
        return;
    }

    const fragment = document.createDocumentFragment();

    animeList.forEach((anime) => {
        const card = document.createElement("div");
        card.className = "anime-card";

        const posterWrap = document.createElement("div");
        posterWrap.className = "anime-postter-wrap";
        posterWrap.appendChild(createPoster(anime.posterImage, anime.title || "Anime poster", "anime-postter"));

        const title = document.createElement("div");
        title.className = "anime-title";
        title.textContent = anime.title || "Untitled";
        title.title = anime.title || "Untitled";

        card.appendChild(posterWrap);
        card.appendChild(title);

        // Open details on poster or title click
        card.addEventListener("click", () => openDetails(anime));

        fragment.appendChild(card);
    });

    grid.appendChild(fragment);
}

// ===== Anime Details Modal =====

function openDetails(anime) {
    modalCard.innerHTML = "";

    // Close button
    const closeBtn = document.createElement("button");
    closeBtn.className = "modal-close";
    closeBtn.setAttribute("aria-label", "Close");
    closeBtn.textContent = "×";
    closeBtn.addEventListener("click", closeModal);

    // Poster
    const posterWrap = document.createElement("div");
    posterWrap.className = "modal-postter-wrap";
    posterWrap.appendChild(createPoster(anime.posterImage, anime.title || "Anime poster", "modal-postter"));

    // Body
    const body = document.createElement("div");
    body.className = "modal-body";

    const title = document.createElement("h2");
    title.className = "modal-title";
    title.textContent = anime.title || "Untitled";

    const ratingWrap = document.createElement("div");
    ratingWrap.className = "modal-rating";
    const ratingLabel = document.createElement("span");
    ratingLabel.className = "modal-rating-label";
    ratingLabel.textContent = "Rating";
    const ratingValue = document.createElement("span");
    ratingValue.textContent = anime.rating || "N/A";
    ratingWrap.appendChild(ratingLabel);
    ratingWrap.appendChild(document.createTextNode(" "));
    ratingWrap.appendChild(ratingValue);

    const description = document.createElement("p");
    description.className = "modal-description";
    description.textContent = anime.description || "No description available.";

    // Trailer button
    const trailerBtn = document.createElement("a");
    trailerBtn.className = "modal-trailer-btn";
    trailerBtn.textContent = "▶ Watch Trailer";

    const trailerNote = document.createElement("p");
    trailerNote.className = "modal-trailer-note";

    if (hasValidTrailer(anime.trailerUrl)) {
        trailerBtn.href = anime.trailerUrl;
        trailerBtn.target = "_blank";
        trailerBtn.rel = "noopener noreferrer";
    } else {
        trailerBtn.textContent = "Trailer Not Available";
        trailerBtn.classList.add("disabled");
        trailerBtn.setAttribute("aria-disabled", "true");
        trailerBtn.style.pointerEvents = "none";
        trailerNote.textContent = "Trailer Not Available";
    }

    body.appendChild(title);
    body.appendChild(ratingWrap);
    body.appendChild(description);
    body.appendChild(trailerBtn);
    body.appendChild(trailerNote);

    modalCard.appendChild(closeBtn);
    modalCard.appendChild(posterWrap);
    modalCard.appendChild(body);

    modal.hidden = false;
    document.body.style.overflow = "hidden";
}

function closeModal() {
    modal.hidden = true;
    modalCard.innerHTML = "";
    document.body.style.overflow = "";
}

// Close modal on backdrop click
modalBackdrop.addEventListener("click", closeModal);

// Close modal on Escape key
document.addEventListener("keydown", (e) => {
    if (e.key === "Escape" && !modal.hidden) {
        closeModal();
    }
});

// ===== Search & Homepage =====

async function loadHomepage() {
    showStatus("Loading...", true);
    try {
        const anime = await fetchAnime("/anime/random");
        renderCards(anime);
    } catch (err) {
        console.error(err);
        showStatus("Unable to load anime. Please try again.", false, true);
    }
}

async function searchAnime(query) {
    showStatus("Loading...", true);
    try {
        const anime = await fetchAnime(`/anime?title=${encodeURIComponent(query)}`);
        renderCards(anime);
    } catch (err) {
        console.error(err);
        showStatus("Unable to load anime. Please try again.", false, true);
    }
}

// Submit handler (covers both Enter key and button click)
searchForm.addEventListener("submit", (e) => {
    e.preventDefault();
    const query = searchInput.value.trim();
    if (!query) {
        loadHomepage();
    } else {
        searchAnime(query);
    }
});

// ===== Init =====
loadHomepage();
