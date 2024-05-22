document.addEventListener("DOMContentLoaded", function() {
    // Set the default tab as active
    document.getElementById('profileTab').classList.add('active');
    document.getElementById('profileContent').classList.add('active');

    // Add click event listeners to tab buttons
    const tabButtons = document.querySelectorAll('.tab-button');
    tabButtons.forEach(button => {
        button.addEventListener('click', () => {
            const tabName = button.id.replace('Tab', '');
            toggleTab(tabName);
        });
    });
});

function toggleTab(tabName) {
    const tabButtons = document.querySelectorAll('.tab-button');
    const tabContents = document.querySelectorAll('.content');

    tabContents.forEach(content => {
        content.classList.remove('active');
    });

    tabButtons.forEach(button => {
        button.classList.remove('active');
    });

    document.getElementById(tabName + 'Content').classList.add('active');
    document.getElementById(tabName + 'Tab').classList.add('active');

    if (tabName === 'settings') {
        document.getElementById('profileContent').style.display = 'none';
    } else {
        document.getElementById('profileContent').style.display = 'block';
    }
}

function handleKeyPress(event) {
    if (event.keyCode === 13) {
        event.preventDefault();

        const newUsername = event.target.value; // Get the new username
        const userId = document.querySelector('input[name="userId"]').value;

    if (newUsername.length < 6) {
        document.getElementById('errorMessage').innerText = 'Username must be at least 6 characters long.';
        document.getElementById('errorMessage').style.color = 'red';
        return;
    } else {
        document.getElementById('errorMessage').innerText = '';
        document.getElementById('errorMessage').style.color = 'black';
    }

        updateUsername(userId, newUsername);

        event.target.blur();
    }
}


function updateUsername(userId, newUsername) {
    const url = `/update-username/${userId}`;

    // Display loading overlay
    document.getElementById('loadingOverlay').style.display = 'flex';

    fetch(url, {
        method: 'POST',
        headers: {
        'Content-Type': 'application/json',
    },
        body: JSON.stringify({ username: newUsername }),
    }) 
    .then(response => {
        if (response.ok) {
            console.log('Username updated successfully');
        } else {
            console.error('Failed to update username');
        }
        
        document.getElementById('loadingOverlay').style.display = 'none';
    })
    .catch(error => {
        console.error('Error updating username:', error);
        document.getElementById('loadingOverlay').style.display = 'none';
    });
}

function updatePassword(userId, newPassword) {
    const url = `/update-password/${userId}`;

    document.getElementById('loadingOverlay').style.display = 'flex';

    fetch(url, {
        method: 'POST',
        headers: {
        'Content-Type': 'application/json',
    },
    body: JSON.stringify({ password: newPassword }),
    })
    .then(response => {
        if (response.ok) {
            console.log('Password updated successfully');
        } else {
            console.error('Failed to update Password');
        }
        document.getElementById('loadingOverlay').style.display = 'none';
    })
    .catch(error => {
        console.error('Error updating password:', error);
        document.getElementById('loadingOverlay').style.display = 'none';
    });
}

    // Function to open the password reset popup
function openPasswordResetPopup() {
    const popup = document.getElementById('passwordResetPopup');
    if (popup) {
        popup.style.display = 'block';
        // Scroll to top of the popup
        popup.scrollTop = 0;
    }
}

    // Function to close the password reset popup
function closePasswordResetPopup() {
    const popup = document.getElementById('passwordResetPopup');
    if (popup) {
        popup.style.display = 'none';
    }
}

    // Function to handle password reset confirmation
function confirmPasswordReset() {
    var newPassword = document.getElementById('newPassword').value;
    var confirmPassword = document.getElementById('confirmPassword').value;
    var errorPassword = document.getElementById('errorPassword');
    const userId = document.querySelector('input[name="userId"]').value;

    if (newPassword.length < 8) {
        errorPassword.innerText = 'Password must be at least 8 characters long.';
        errorPassword.style.color = 'red';
        return;
    } else {
        errorPassword.innerText = '';
        errorPassword.style.color = 'black';
    }

    if (newPassword !== confirmPassword) {
        errorPassword.innerText = 'Passwords do not match.';
        errorPassword.style.color = 'red';
        return;
    } else {
        errorPassword.innerText = '';
        errorPassword.style.color = 'black';
    }

    updatePassword(userId, newPassword);

    document.getElementById('newPassword').value = '';
    document.getElementById('confirmPassword').value = '';

    closePasswordResetPopup();
}


document.addEventListener("DOMContentLoaded", function() {
document.getElementById('resetButton').addEventListener('click', openPasswordResetPopup);
});

    // Function to preview profile picture before upload
function previewProfilePicture(event) {
    const input = event.target;
    if (input.files && input.files[0]) {
        const reader = new FileReader();
        reader.onload = function(e) {
            const previewImage = document.getElementById('previewImage');
            previewImage.src = e.target.result;
        }
        reader.readAsDataURL(input.files[0]); // Read the uploaded file as a data URL
    }
}


function saveProfileChanges() {
    const fileInput = document.getElementById('profilePictureInput');
    const file = fileInput.files[0];

    if (!file) {
        console.error('No file selected.');
        return;
    }

    const formData = new FormData();
    formData.append('profilePicture', file);

    const userId = document.querySelector('input[name="userId"]').value;
    const url = `/upload-profile-picture/${userId}`;

    const xhr = new XMLHttpRequest();

    xhr.open('POST', url, true);

    xhr.onload = function () {
        if (xhr.status === 200) {
            console.log('Profile picture uploaded successfully');
        } else {
            console.error('Failed to upload profile picture');
        }
    };

    xhr.onerror = function () {
        console.error('Network error during profile picture upload');
    };

    // Send the FormData
    xhr.send(formData);
}

document.addEventListener('DOMContentLoaded', function() {
    var successMessage = document.querySelector('.success-message');

    function showMessage(element, message) {
        element.querySelector('span').innerText = message;
        element.style.display = 'block';
        setTimeout(function() {
            element.style.display = 'none';
        }, 10000);
    }

    // Example usage: Call this function to display the success message
    showMessage(successMessage, 'Your profile picture has been updated. Please reload the page.');

});