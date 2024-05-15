function openModal() {
    var modal = document.getElementById('logoutModal');
    modal.classList.add('active');
}

function closeModal() {
    var modal = document.getElementById('logoutModal');
    modal.classList.remove('active');
}

function confirmLogout() {
    window.location.href = /*[[ @{/logout} ]]*/ '/logout';
}

function cancelLogout() {
    var modal = document.getElementById('logoutModal');
    modal.classList.add('closing');
    setTimeout(function() {
        modal.classList.remove('active', 'closing');
    }, 300);
}