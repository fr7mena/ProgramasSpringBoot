// File: src/main/resources/static/transaction_management/transaction_management.js
window.addEventListener('DOMContentLoaded', async () => {
    const userName = localStorage.getItem('userName');
    const userInfoDiv = document.getElementById('userInfo');

    if (userName) {
        userInfoDiv.innerHTML = `<p>Hola, <strong>${userName}</strong>, ¿qué gestión transaccional deseas realizar hoy?</p>`;
    } else {
        // Si no hay datos en localStorage, verifica la sesión con el backend como respaldo
        const response = await fetch('/api/users/session', {
            credentials: 'include'
        });

        const data = await response.json();

        if (response.ok && data.userName) {
            userInfoDiv.innerHTML = `<p>Hola, <strong>${data.userName}</strong>, ¿qué gestión transaccional deseas realizar hoy?</p>`;
            // Guarda el nombre de usuario en localStorage para futuras visitas a esta página
            localStorage.setItem('userName', data.userName);
            // No guardamos ID ni Email si no se van a mostrar
        } else {
            userInfoDiv.textContent = 'Error al cargar la información del usuario. Redirigiendo al login...';
            setTimeout(() => {
                window.location.href = '../login/login.html';
            }, 2000);
        }
    }
});