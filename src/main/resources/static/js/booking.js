const extraBedOption = document.getElementById('extra-bed');
const modalBody = document.getElementById('modalBody');
const params = new URLSearchParams(window.location.search);
const bookingId = params.get("bookingId");
let editMode = false;
let calendar;
let guestcount;
const bookingState = {
    selectedDates: null,
    nights: 0,
};
document.addEventListener("DOMContentLoaded", () => {
    initializeCalendar();
    showGuestSection();
    if (bookingId){
        editMode = true;
        fetch(`/bookings/${bookingId}`)
            .then(response => response.json())
            .then(booking => applyBookingInfo(booking));
    }
});
function initializeCalendar(){
    const roomId = document.getElementById("room-id").value;
    let disabledDates;
    fetch(`/bookings/room/${roomId}/blocked-dates`)
        .then(r => {
            if (!r.ok) {
                throw new Error("API error");
            }
            return r.json();
        })
        .then(bookings => {

            let filteredBookings = bookings;

            if (editMode) {
                filteredBookings =
                    bookings.filter(
                        b => b.id !== Number(bookingId)
                    );
            }

            const disabledDates = filteredBookings
                .filter(b => b.startdate && b.enddate)
                .map(b => ({
                    from: b.startdate,
                    to: b.enddate
                }));

            calendar = flatpickr("#calendar", {
                mode: "range",
                inline: true,
                minDate: "today",
                disable: disabledDates,

                onChange: function(selectedDates, dateStr) {

                    bookingState.selectedDates =
                        selectedDates;

                    if (selectedDates.length === 2) {
                        handleDateSelection(
                            selectedDates,
                            dateStr
                        );
                    }
                }
            });
        })
}
function applyBookingInfo(booking){
    bookingState.selectedDates = [
        new Date(booking.startdate),
        new Date(booking.enddate)
    ];
    calendar.setDate([
        booking.startdate,
        booking.enddate
    ]);
    document.getElementById("booking-dates")
        .textContent =
        `${booking.startdate} → ${booking.enddate}`;
    guestcount.value = booking.guestcount;
    if (extraBedOption) {
        extraBedOption.checked = booking.extrabed;
    }
    updateTotalPrice(
        bookingState.selectedDates,
    );
    showBookingButton();
    document.getElementById("book-button")
        .textContent = "Save changes";
}
function showGuestSection(){
    const guestSection =
        document.getElementById('guest-section');
    guestSection.style.display = "block";

    let options = `
        <option value="1">1</option>
        <option value="2">2</option>
    `;
    if (extraBedOption) {
        options += `
            <option value="3">3</option>
        `;
    }
    guestSection.innerHTML = `
        <label>Guests:</label>
        <select id="guest-count">
            ${options}
        </select>
    `;
    guestcount =
        document.getElementById('guest-count');
    guestcount.addEventListener('change', () => {
        if (bookingState.selectedDates) {
            updateTotalPrice(
                bookingState.selectedDates,
                Number(guestcount.value)
            );
        }
    });
}
function handleDateSelection(selectedDates, dateStr){
    bookingState.selectedDates = selectedDates;
    bookingState.nights = 0
    document.getElementById("booking-dates").textContent = dateStr;
    updateTotalPrice(selectedDates, Number(guestcount.value));
    showBookingButton();
    console.log(dateStr);
}

function showBookingButton(){
    document.getElementById('book-button').style.display = "block";
}
function getGuestCount(){
    return Number(document.getElementById('guest-count').value);
}
function updateTotalPrice(selectedDates){
    const guests = getGuestCount();
    const roomPrice = Number(
        document.getElementById('room-price').value
    );
    if (selectedDates.length > 1) {
        const firstDate = selectedDates[0];
        const lastDate = selectedDates[1];
        const millisecondsPerDay = 1000 * 60 * 60 * 24;
        bookingState.nights = Math.round(
            (lastDate - firstDate) / millisecondsPerDay
        );
    }
    const totalPrice = bookingState.nights * guests * roomPrice;
    const totalPriceDisplay = document.getElementById('booking-total');
    totalPriceDisplay.innerHTML = `
        ${totalPrice} SEK`;
}

function handleBookingClick(){
    const formatDate = (d) => d.toISOString().split("T")[0];
    const bookingRequest = {
        roomid: document.getElementById("room-id").value,
        startdate: formatDate(bookingState.selectedDates[0]),
        enddate: formatDate(bookingState.selectedDates[1]),
        guestcount: guestcount.value,
        extrabed: extraBedOption
            ? extraBedOption.checked
            : false
    };

    let url = editMode  ? `/bookings/${bookingId}`
        : "/bookings";
    let method = editMode ? "PUT"
        : "POST";
    fetch(url, {
        method: method,
        headers: {
            "Content-Type": "application/json" },
        body: JSON.stringify(bookingRequest) })
        .then(response => {
            if (!response.ok) {
                throw new Error("Booking failed");
            }
            return response.json();
        })
        .then(data => {
            showConfirmationModal(data);
        })
        .catch(error => {
            console.error(error);
            alert("Something went wrong with booking");
        });
}

function showConfirmationModal(data){
    modalBody.innerHTML = `
        <img id = "modal_image"
            src="/images/single_room.png"
            class="modal-room-image"
            alt="Room image">
        <h5>Room ${data.roomid}</h5>
        <p><strong>Dates:</strong><br>
            ${data.startdate} → ${data.enddate}</p>
        <p><strong>Guests:</strong>
            ${data.guestcount}</p>
        ${data.extrabed ? `
    <p><strong>Extra bed:</strong> Yes</p>` : ''}
        <p><strong>Total cost:</strong>
            ${data.cost} SEK</p>`;
    const modal = new bootstrap.Modal(
        document.getElementById('myModal')
    );
    modal.show();
}
document.getElementById('book-button')
    .addEventListener('click', handleBookingClick);
