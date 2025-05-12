// File: src/main/resources/static/transaction_management/transaction_management.js
window.addEventListener('DOMContentLoaded', async () => {
    const userName = localStorage.getItem('userName');
    const userId = localStorage.getItem('userId');
    const userEmail = localStorage.getItem('userEmail');
    const userInfoDiv = document.getElementById('userInfo');

    if (userName && userId && userEmail) {
        userInfoDiv.innerHTML = `
            <p>Nombre de usuario: ${userName}</p>
            <p>ID de usuario: ${userId}</p>
            <p>Email de usuario: ${userEmail}</p>
        `;
    } else {
        // Si no hay datos en localStorage, verifica la sesión con el backend como respaldo
        const response = await fetch('/api/users/session', {
            credentials: 'include'
        });

        const data = await response.json();

        if (response.ok) {
            userInfoDiv.innerHTML = `
                <p>Nombre de usuario: ${data.userName}</p>
                <p>ID de usuario: ${data.userId}</p>
                <p>Email de usuario: ${data.userEmail}</p>
            `;
            // Guarda los datos en localStorage para futuras visitas a esta página
            localStorage.setItem('userName', data.userName);
            localStorage.setItem('userId', data.userId);
            localStorage.setItem('userEmail', data.userEmail);
        } else {
            userInfoDiv.textContent = 'Error al cargar la información del usuario. Redirigiendo al login...';
            setTimeout(() => {
                window.location.href = '../login/login.html';
            }, 2000);
        }
    }
});