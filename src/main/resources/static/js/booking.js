
const extraBedOption = document.getElementById('extra-bed');
const modalBody = document.getElementById('modalBody');
const bookingState = {
    selectedDates: null,
    nights: 0,
    extraBed: false
};

initializeCalendar();
showGuestSection();

function initializeCalendar(){
    const calendar = flatpickr("#calendar", {
        mode: "range",
        minDate: "today",
        dateFormat: "Y-m-d",
        inline: true,
        onChange: function(selectedDates, dateStr) {
            handleDateSelection(selectedDates, dateStr);
        }
    });
}

function showGuestSection(){
    document.getElementById('guest-section').style.display = "block";
    const guestSection = document.getElementById('guest-section');
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
    guestCount = document.getElementById('guest-count');
    guestCount.addEventListener('change', () => {
        updateTotalPrice(
            bookingState.selectedDates,
            Number(guestCount.value)
        );
    });
}
function handleDateSelection(selectedDates, dateStr){
    bookingState.selectedDates = selectedDates;
    updateTotalPrice(selectedDates, Number(guestCount.value));
    showBookingButton();
}

function showBookingButton(){
    document.getElementById('book-button').style.display = "block";
}

function updateTotalPrice(selectedDates, guests){
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
    const bookingRequest = {
        roomid: document.getElementById("room-id").value,
        startDate: bookingState.selectedDates[0],
        endDate: bookingState.selectedDates[1],
        guestCount: guestCount.value,
        extraBed: extraBedOption
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
        <img 
            src="/images/rooms/${data.roomid}/1.jpg"
            class="modal-room-image"
            alt="Room image">
        <h5>Room ${data.roomid}</h5>
        <p><strong>Dates:</strong><br>
            ${data.startDate} → ${data.endDate}</p>
        <p><strong>Guests:</strong>
            ${data.guestCount}</p>
        ${data.extraBed ? `
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
