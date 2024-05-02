document.addEventListener('DOMContentLoaded', function() {
    var successMessage = document.querySelector('.success-message');

    function showMessage(element, message) {
        element.querySelector('span').innerText = message;
        element.style.display = 'block';
        setTimeout(function() {
            element.style.display = 'none';
        }, 10000);
    }

    showMessage(successMessage, 'Your username has been updated')

});