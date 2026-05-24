const table = document.getElementById("bookings-container");
const customerid = document.getElementById("customer-id").value;
createBookingView();

function createBookingView(){
    let rows = "";
    fetch(`/bookings/customer/active/${customerid}`)
        .then(r => r.json())
        .then(bookings => {
            if (bookings.length === 0) {
                table.innerHTML = "<tr><td colspan='5'>No upcoming bookings</td></tr>";
                return;
            }
            bookings.forEach(booking => {
                rows += `
                <tr>
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
                </tr>
                `;
            });
            table.innerHTML = rows;
        })
        .catch(error => {
            console.error(error);
            alert("Something went wrong with retrieving bookings");
        })}