function validateEmail() {
    const email = document.getElementById('email').value;
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const emailValid = emailPattern.test(email);
    const emailInput = document.getElementById('email');

    if (!emailValid) {
        emailInput.classList.add('invalid');
    } else {
        emailInput.classList.remove('invalid');
    }
}

function validatePassword() {
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirmPassword').value;
    const passwordInput = document.getElementById('password');
    const confirmPasswordInput = document.getElementById('confirmPassword');

    if (password !== confirmPassword) {
        passwordInput.classList.add('invalid');
        confirmPasswordInput.classList.add('invalid');
    } else {
        passwordInput.classList.remove('invalid');
        confirmPasswordInput.classList.remove('invalid');
    }

    updateButtonState();
}

function updateButtonState() {
    const emailInput = document.getElementById('email');
    const passwordInput = document.getElementById('password');
    const confirmPasswordInput = document.getElementById('confirmPassword');
    const registerButton = document.getElementById('buttonRegister');

    const emailValid = !emailInput.classList.contains('invalid');
    const passwordValid = !passwordInput.classList.contains('invalid') && !confirmPasswordInput.classList.contains('invalid');

    if (emailValid && passwordValid) {
        registerButton.disabled = false;
        registerButton.classList.remove('disabled');
    } else {
        registerButton.disabled = true;
        registerButton.classList.add('disabled');
    }
}