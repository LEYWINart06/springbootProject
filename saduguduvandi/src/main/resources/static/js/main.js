// Mobile navigation toggle
document.addEventListener('DOMContentLoaded', function () {
    var toggle = document.getElementById('navToggle');
    var links = document.getElementById('navLinks');
    if (toggle && links) {
        toggle.addEventListener('click', function () {
            links.classList.toggle('open');
        });
    }

    // Live total price calculation on the booking page
    var pickupInput = document.getElementById('pickupDate');
    var returnInput = document.getElementById('returnDate');
    var totalEl = document.getElementById('totalPriceValue');
    var daysEl = document.getElementById('totalDaysValue');

    if (pickupInput && returnInput && totalEl) {
        var pricePerDay = parseFloat(totalEl.dataset.price || '0');

        function recalculate() {
            var pickup = new Date(pickupInput.value);
            var ret = new Date(returnInput.value);
            var diffTime = ret - pickup;
            var days = Math.round(diffTime / (1000 * 60 * 60 * 24));

            if (days > 0 && !isNaN(days)) {
                totalEl.textContent = '₹' + (days * pricePerDay).toFixed(2);
                daysEl.textContent = days;
            } else {
                totalEl.textContent = '₹0.00';
                daysEl.textContent = '0';
            }
        }

        pickupInput.addEventListener('change', recalculate);
        returnInput.addEventListener('change', recalculate);
    }
});
