
let submitButton = null;
export function leaveReview(booking, modalBody, modalFooter){
    modalBody.innerHTML = ``;
    modalFooter.innerHTML = ``;
    let comments = "";
    modalBody.innerHTML = ` <div class="review-card h-100">
        <h2 class="text-center mb-4" style="font-family: 'Playfair Display', serif; color: #5f4b45;">Add review</h2>
        <form method="post">
            <input type="hidden" name="bookingId" th:value="${param.bookingId}">
            <div class="mb-3">
                <label class="form-label" style="color: #a67b6b;font-size: 0.85rem;text-transform: uppercase;letter-spacing: 0.1em;"> Share your thoughts</label>
                <input type="text" id="comment-section" class="comment-section" placeholder="add a comment:"
                       required>
            </div>
            
        </form>
    </div>`
    const commentSection = modalBody.querySelector(".comment-section");
    commentSection.addEventListener(`input`,() => {
        const comment = commentSection.values;
        if (submitButton !== null && (comment === null || comment === ``)){
            modalFooter.innerHTML = ``;
        }
        else if (submitButton === null && comment !== null || comment !== ``) {
            showSubmitButton(modalFooter, comment);
        }
    });
}

function showSubmitButton(modalFooter, review) {
    modalFooter.innerHTML = `<button type="submit" id="comment-btn" class="comment-btn">Leave review</button>`;
    submitButton = modalFooter.querySelector(".comment-btn");
    submitButton.addEventListener(`click`, sendReview(review))
}

function sendReview(review){

}