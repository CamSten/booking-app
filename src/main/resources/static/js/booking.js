const extraBedOption = document.getElementById('extra-bed');
const modalBody = document.getElementById('modalBody');
const bookingState = {
    selectedDates: null,
    nights: 0,
    extra_bed: false
};
document.addEventListener("DOMContentLoaded", () => {
    initializeCalendar();
    showGuestSection();
});
function initializeCalendar(){
    const roomId = document.getElementById("room-id").value;
    fetch(`/bookings/room/${roomId}/blocked-dates`)
        .then(r => {
            if (!r.ok) {
                throw new Error("API error");
            }

            return r.json();
        })
        .then(bookings => {
            const disabledDates = bookings
                .filter(b => b.startdate && b.enddate)
                .map(b => ({
                    from: b.startdate,
                    to: b.enddate
                }));

            flatpickr("#calendar", {
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
        .catch(error => {
            console.error(error);
        });
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
    if (!bookingState.selectedDates || bookingState.selectedDates.length < 2) {
        alert("Please select valid dates");
        return;
    }
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

    fetch("/bookings", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(bookingRequest)
    })
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
