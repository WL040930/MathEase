function validateForm() {
    const isUsernameValid = document.getElementById('username').value.length >= 6;
    const isEmailValid = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(document.getElementById('email').value);
    const isPasswordValid = document.getElementById('password').value.length >= 8;
    const isConfirmPasswordValid = document.getElementById('confirmPassword').value === document.getElementById('password').value;

    return isUsernameValid && isEmailValid && isPasswordValid && isConfirmPasswordValid;
}

function validateUsername() {
    const username = document.getElementById('username').value;
    const usernameInput = document.getElementById('username');
    const inputMessage = document.getElementById('input-message');

    if (username.length < 6) {
        usernameInput.classList.add('invalid');
        inputMessage.textContent = 'Username must be at least 6 characters.';
    } else {
        usernameInput.classList.remove('invalid');
        inputMessage.textContent = ''; // Clear error message if valid
    }

    updateButtonState();
}

function validateEmail() {
    const email = document.getElementById('email').value;
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const emailInput = document.getElementById('email');
    const inputMessage = document.getElementById('input-message');

    if (!emailPattern.test(email)) {
        emailInput.classList.add('invalid');
        inputMessage.textContent = 'Please enter a valid email address.';
    } else {
        emailInput.classList.remove('invalid');
        inputMessage.textContent = ''; // Clear error message if valid
    }

    updateButtonState();
}

function validatePassword() {
    const password = document.getElementById('password').value;
    const passwordInput = document.getElementById('password');
    const inputMessage = document.getElementById('input-message');

    if (password.length < 8) {
        passwordInput.classList.add('invalid');
        inputMessage.textContent = 'Password must be at least 8 characters.';
    } else {
        passwordInput.classList.remove('invalid');
        inputMessage.textContent = ''; // Clear error message if valid
    }

    validateConfirmPassword(); // Trigger confirm password validation
}

function validateConfirmPassword() {
    const confirmPassword = document.getElementById('confirmPassword').value;
    const confirmPasswordInput = document.getElementById('confirmPassword');
    const password = document.getElementById('password').value;
    const inputMessage = document.getElementById('input-message');

    if (confirmPassword !== password || confirmPassword.length < 8) {
        confirmPasswordInput.classList.add('invalid');
        inputMessage.textContent = 'Passwords do not match. (Minimum 8 characters)';
    } else {
        confirmPasswordInput.classList.remove('invalid');
        inputMessage.textContent = ''; // Clear error message if valid
    }

    updateButtonState();
}

function updateButtonState() {
    const usernameInput = document.getElementById('username');
    const emailInput = document.getElementById('email');
    const passwordInput = document.getElementById('password');
    const confirmPasswordInput = document.getElementById('confirmPassword');
    const registerButton = document.getElementById('buttonRegister');

    const isUsernameValid = usernameInput.value.length >= 6;
    const isEmailValid = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(emailInput.value);
    const isPasswordValid = passwordInput.value.length >= 8;
    const isConfirmPasswordValid = confirmPasswordInput.value === passwordInput.value;

    if (isUsernameValid && isEmailValid && isPasswordValid && isConfirmPasswordValid) {
        registerButton.disabled = false;
        registerButton.classList.remove('disabled');
    } else {
        registerButton.disabled = true;
        registerButton.classList.add('disabled');
    }
}

function handleFileInput() {
    var fileInput = document.getElementById('file');
    var previewImage = document.getElementById('previewImage');
    var selectedFile = fileInput.files[0];

    if (selectedFile) {
        var reader = new FileReader();

        reader.onload = function (e) {
            previewImage.src = e.target.result;
            previewImage.style.display = 'block';
        };

        reader.readAsDataURL(selectedFile);
    } else {
        // Clear the preview if no file is selected
        previewImage.src = '';
        previewImage.style.display = 'none';
    }
}