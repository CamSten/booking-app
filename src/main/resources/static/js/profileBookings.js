const table = document.getElementById("bookings-container");
const modalBody = document.getElementById('modalBody');
const customerid = document.getElementById("customer-id").value;
createBookingView();

function createBookingView(){
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
            <img
                src="/images/single_room.png"
                alt="Room image"
                class="booking-thumbnail">
        </td>
        <td>${booking.startdate}</td>
        <td>${booking.enddate}</td>
        <td>${booking.guestcount}</td>
        <td>${booking.cost} SEK</td>
    `;
    row.addEventListener("click", () => {
        showBookingDetails(booking);
    });

    table.appendChild(row);
}
function showBookingDetails(booking){
    modalBody.innerHTML = `
        <img id = "modal_image"
            src="/images/single_room.png"
            class="modal-room-image"
            alt="Room image">
        <h5>Room ${booking.roomid}</h5>
        <p><strong>Dates:</strong><br>
            ${booking.startdate} → ${booking.enddate}</p>
        <p><strong>Guests:</strong>
            ${booking.guestcount}</p>
        ${booking.extrabed ? `
    <p><strong>Extra bed:</strong> Yes</p>` : ''}
        <p><strong>Total cost:</strong>
            ${booking.cost} SEK</p>
    <p><button class = "edit-booking-button">Edit</button></p>
    <p><button class = "delete-booking-button">Cancel</button></p>`;
    const modal = new bootstrap.Modal(
        document.getElementById('myModal')
    );
    createEditButton(booking)
    createDeleteButton(booking)
    modal.show();
}
function createEditButton(booking){
    const editButton = document.querySelector(".edit-booking-button");
    editButton.addEventListener("click", () => {
        editBooking(booking);
    });
}

function editBooking(){
    window.location.href =
        `/booking/${booking.roomid}?bookingId=${booking.id}`;
}
function createDeleteButton(booking){

}


