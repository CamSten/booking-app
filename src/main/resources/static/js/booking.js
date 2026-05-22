
var guestCount = document.getElementById('guest-count');
const extraBedOption = document.getElementById('extra-bed')
const bookingState = {
    selectedDates: null,
    nights: 0,
    extraBed: false
};

initializeCalendar();

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

function checkExtraBed(){
    bookingState.extraBed = extraBedOption.checked;
    if (guestCount.value == 3){
        extraBedOption.innerHTML = true;
    }
    else if (bookingState.extraBed = true && guestCount < 3){
        const newCount = guestCount.value +=1;
        extraBedOption.innerHTML = `${newCount}`;
    }
}

function handleDateSelection(selectedDates, dateStr){
    bookingState.selectedDates = selectedDates;
    updateTotalPrice(selectedDates, guestCount);
    showBookingButton();
}

function showBookingButton(){
    document.getElementById('book-button')
        .addEventListener('click', handleBookingClick);
}

function updateTotalPrice(selectedDates, guestCount){
    if (selectedDates.length > 1) {
        const firstDate = selectedDates[0];
        const lastDate = selectedDates[1];
        bookingState.nights = lastDate - firstDate;
    }
    const totalPrice = bookingState.nights * guestCount;
    const totalPriceDisplay = document.getElementById('booking-total');
    totalPriceDisplay.innerHTML = `
        ${totalPrice} SEK`;
}


function handleBookingClick(){
    const bookingRequest = {
        roomNumber: document.getElementById("room-number").innerText,
        startDate: bookingState.selectedDates[0],
        endDate: bookingState.selectedDates[1],
        nights: bookingState.nights,
        guests: guestCount.value,
        extraBed: extraBedOption.checked
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

function showConfirmationModal(){


}

