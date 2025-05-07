// File: src/main/resources/static/dashboard/dashboard.js

window.addEventListener('DOMContentLoaded', async () => {
    // Verificar sesión activa
    const response = await fetch('/api/users/session', {
        credentials: 'include'
    });

    const data = await response.json();
    const welcomeMessage = document.getElementById('welcomeMessage');

    if (response.ok) {
        welcomeMessage.textContent = `Bienvenido, usuario ID: ${data.userId}`;
    } else {
        window.location.href = '../login/login.html';
    }
});

document.getElementById('logoutButton').addEventListener('click', () => {
    document.getElementById('logoutModal').style.display = 'block';
});

document.getElementById('cancelLogout').addEventListener('click', () => {
    document.getElementById('logoutModal').style.display = 'none';
});

document.getElementById('confirmLogout').addEventListener('click', async () => {
    const response = await fetch('/api/users/logout', {
        method: 'POST',
        credentials: 'include'
    });

    if (response.ok) {
        window.location.href = '../login/login.html';
    } else {
        alert('Error cerrando sesión');
    }
});
