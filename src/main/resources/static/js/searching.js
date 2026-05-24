document.addEventListener("DOMContentLoaded", () => {
    initializeCalendar();
});
const bookingState = {
    selectedDates: null,
};
function formatDate(date) {
    return date.toISOString().split("T")[0];
}
function initializeCalendar() {
    flatpickr("#calendar", {
        mode: "range",
        inline: true,
        minDate: "today",
        onChange: function (selectedDates) {
            bookingState.selectedDates = selectedDates;
            if (selectedDates.length === 2) {
                showSearchButton();
            } else {
                hideSearchButton();
            }
        }
    });
}
function showSearchButton(){
        document.getElementById('search-button').style.display = "block";
}

function searchRooms(){
    const startDate = formatDate(bookingState.selectedDates[0]);
    const endDate = formatDate(bookingState.selectedDates[1]);
    fetch(`/bookings/availability/${startDate}/${endDate}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Booking failed");
            }
            return response.json();
        })
        .then(data => {
            console.log(data);


            console.log("SUCCESSFUL SEARCH")

        });
}

function hideSearchButton(){
    document.getElementById('search-button').style.display = "none";
}
document.getElementById('search-button')
    .addEventListener('click', searchRooms);