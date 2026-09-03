let selectedRating = 0;
let submitButton = null;

export function leaveReview(roomId, customerId, modalBody, modalFooter) {
    selectedRating = 0;
    submitButton = null;
    modalBody.innerHTML = `
        <div class="review-card">
            <h2 class="text-center review-title">Add review</h2>
            <div class="star-container">
                <span class="star" data-rating="1">☆</span>
                <span class="star" data-rating="2">☆</span>
                <span class="star" data-rating="3">☆</span>
                <span class="star" data-rating="4">☆</span>
                <span class="star" data-rating="5">☆</span>
            </div>
            <div class="mb-3">
                <label class="form-label review-label">Share your thoughts</label>
                <textarea 
                    id="comment-section" class="comment-section" placeholder="Add a comment..." rows="4" required>
                </textarea>
            </div>
        </div>`;
    modalFooter.innerHTML = `<button type="button" class="review-submit-button" disabled>Leave review </button>`;
    const stars = modalBody.querySelectorAll(".star");
    const commentSection = modalBody.querySelector(".comment-section");
    submitButton = modalFooter.querySelector(".review-submit-button");
    stars.forEach(star => {
        star.addEventListener("click", () => {
            selectedRating = Number(star.dataset.rating);
            updateStars(stars);
            updateSubmitButton();
        });
    });
    commentSection.addEventListener("input", () => {
        updateSubmitButton();
    });
    submitButton.addEventListener("click", () => {
        const review = {
            roomId: roomId,
            customerId: customerId,
            rating: selectedRating,
            comment: commentSection.value
        };
        sendReview(review);
    });
}

function updateStars(stars) {
    stars.forEach(star => {
        const rating = Number(star.dataset.rating);
        star.textContent = (rating <= selectedRating) ? "★" :  star.textContent = "☆";
    });
}

function updateSubmitButton() {
    const comment = document.getElementById("comment-section").value.trim();
    if (selectedRating > 0 && comment.length > 0) {
        submitButton.disabled = false;
    }
    else {
        submitButton.disabled = true;
    }
}

function sendReview(review) {
    console.log("Review ready for backend:");
    console.log(review);
}