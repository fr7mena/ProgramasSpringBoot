// File: src/main/resources/static/dashboard/dashboard.js

window.addEventListener('DOMContentLoaded', async () => {
    const response = await fetch('/api/users/session', {
        credentials: 'include'
    });

    const data = await response.json();
    const welcomeMessage = document.getElementById('welcomeMessage');

    if (response.ok) {
        // Modificado: Solo el nombre de usuario y una frase de bienvenida a las funcionalidades
        welcomeMessage.textContent = `¡Bienvenido, ${data.userName}! ¿Qué gestión financiera deseas realizar hoy?`;

        // Mantener la información en localStorage por si se necesita en otras partes de la aplicación
        localStorage.setItem('userName', data.userName);
        localStorage.setItem('userId', data.userId);
        localStorage.setItem('userEmail', data.userEmail);
    } else {
        // Redirigir al login si no hay sesión
        window.location.href = '../login/login.html';
    }
});

document.getElementById('logoutButton').addEventListener('click', () => {
    // Cuando el modal está oculto por 'display: none', cambiarlo a 'flex' para que sea visible
    document.getElementById('logoutModal').style.display = 'flex';
});

document.getElementById('cancelLogout').addEventListener('click', () => {
    // Ocultar el modal volviendo a 'none'
    document.getElementById('logoutModal').style.display = 'none';
});

document.getElementById('confirmLogout').addEventListener('click', async () => {
    const response = await fetch('/api/users/logout', {
        method: 'POST',
        credentials: 'include'
    });

    if (response.ok) {
        localStorage.removeItem('userName');
        localStorage.removeItem('userId');
        localStorage.removeItem('userEmail');
        window.location.href = '../login/login.html';
    } else {
        alert('Error cerrando sesión');
        // Opcional: Podrías usar un mensaje con la clase 'message error' aquí también si el dashboard tuviera un elemento para ello.
    }
});