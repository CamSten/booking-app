const table = document.getElementById("bookings-container");
const customerid = document.getElementById("customer-id").value;
createBookingView();

function createBookingView(){
    table.innerHTML = "";
    fetch(`/bookings/customer/active/${customerid}`)
        .then(r => r.json())
        .then(bookings => {
            if (bookings.length === 0) {
                table.innerHTML = "<tr><td colspan='5'>No upcoming bookings</td></tr>";
                return;
            }
            bookings.forEach(booking => {
                getBookingRow(booking);
            });
        })
        .catch(error => {
            console.error(error);
            alert("Something went wrong with retrieving bookings");
        })
}

function getBookingRow(booking) {
    const row = document.createElement("tr");
    row.classList.add("booking-row");
    row.innerHTML = `
        <td>
            <img src="/images/rooms/room_${booking.roomid}_1.jpg" class="booking-thumbnail" alt="Room image">        </td>
            <td>${booking.startdate}</td>
            <td>${booking.enddate}</td>
            <td>${booking.guestcount}</td>
            <td>${booking.cost} SEK</td>`;
    row.addEventListener("click", () => {
        showBookingDetails(booking);
    });
    table.appendChild(row);
}
function showBookingDetails(booking){
    const modal = new bootstrap.Modal(document.getElementById('myModal'));
    const modalBody = document.getElementById('modalBody');
    const modalFooter = document.querySelector(".modal-footer");
    modalBody.innerHTML = `
        <img src="/images/rooms/room_${booking.roomid}_1.jpg" class="booking-thumbnail" alt="Room image">        <h5>Room ${booking.roomid}</h5>
        <p><strong>Dates:</strong><br>
            ${booking.startdate} → ${booking.enddate}</p>
        <p><strong>Guests:</strong>
            ${booking.guestcount}</p>
        ${booking.extrabed ? `
        <p><strong>Extra bed:</strong> Yes</p>` : ''}
        <p><strong>Total cost:</strong>
            ${booking.cost} SEK</p>`;
    modalFooter.innerHTML = `
        <div class="modal-actions">
            <button class="modal-btn modal-btn-primary edit-booking-button">
                Edit booking</button>
            <button class="modal-btn modal-btn-danger delete-booking-button">
                Cancel booking</button>
        </div>`;
    document.querySelector(".edit-booking-button").onclick = () => editBooking(booking);
    document.querySelector(".delete-booking-button").onclick = () => showDeleteConfirm(booking);
    modal.show();
}

function editBooking(booking){
    window.location.href =
        `/book?roomId=${booking.roomid}&bookingId=${booking.id}`;
}
function showDeleteConfirm(booking){
    const modalElement = document.getElementById('myModal');
    const modal = bootstrap.Modal.getInstance(modalElement)
        || new bootstrap.Modal(modalElement);
    const modalBody = document.getElementById('modalBody');
    const modalFooter = document.querySelector(".modal-footer");
    modalBody.innerHTML = `
        <p>Are you sure you want to cancel this booking?</p>`;
    modalFooter.innerHTML = `
        <button class="modal-btn modal-btn-danger yes">Yes</button>
        <button class="modal-btn modal-btn-secondary no">No</button>`;
    modalFooter.querySelector(".yes").onclick = () => deleteBooking(booking);
    modalFooter.querySelector(".no").onclick = () => modal.hide();
    modal.show();
}

function deleteBooking(booking){
    const id = booking.id;
    fetch(`/bookings/${id}/cancel`,{
        method: "PUT"})
        .then(response => {
            if (!response.ok) {
                throw new Error("Cancel failed");
            }
        })
        .then(() => {
            createBookingView();
            showFeedback("Your booking has been cancelled.");
        })
        .catch(() => {
            showFeedback("Something went wrong with the cancellation.");
        });
}

function showFeedback(message) {
    const modalElement = document.getElementById('myModal');
    const modal = bootstrap.Modal.getInstance(modalElement)
        || new bootstrap.Modal(modalElement);
    document.getElementById('modalBody').innerHTML = `
        <p>${message}</p>`;
    document.querySelector(".modal-footer").innerHTML = `
        <button class="modal-btn modal-btn-primary" data-bs-dismiss="modal">Close</button>`;
    modal.show();
}

function showAccountDeleteConfirm(event, form){
    event.preventDefault();
    const modalElement = document.getElementById('myModal');
    const modal = bootstrap.Modal.getInstance(modalElement) || new bootstrap.Modal(modalElement);
    const modalTitle = document.querySelector(".modal-title");
    if(modalTitle) modalTitle.innerHTML = "Delete Account";
    document.getElementById('modalBody').innerHTML = `
        <p style="color: #5a514d; font-size: 1.1rem; text-align: center; margin-top: 15px;">
            Are you absolutely sure you want to delete your account? This action cannot be undone.
        </p>`;
    document.querySelector(".modal-footer").innerHTML = `
        <button class="modal-btn modal-btn-danger yes text-center" style="width: 100%; margin-bottom: 8px;">Yes, Delete My Account</button>
        <button class="modal-btn modal-btn-secondary no w-100">Cancel</button>`;
    document.querySelector(".modal-footer .yes").onclick = () => form.submit();
    document.querySelector(".modal-footer .no").onclick = () => modal.hide();
    modal.show();
}