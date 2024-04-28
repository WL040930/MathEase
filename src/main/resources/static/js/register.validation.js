
document.getElementById('registrationForm').addEventListener('submit', function(event) {
    var password = document.getElementById('password').value;
    var confirmPassword = document.getElementById('confirmPassword').value;

    if (password !== confirmPassword) {
        alert('Password and Confirm Password do not match!');
        event.preventDefault(); // Prevent form submission
    }
});