document.getElementById('loginForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const name = document.getElementById('name').value;
    const password = document.getElementById('password').value;

    const response = await fetch('/api/users/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        credentials: 'include',
        body: JSON.stringify({ name, password })
    });

    const result = await response.json();
    const messageEl = document.getElementById('loginMessage');
    if (response.ok) {
        messageEl.textContent = `¡Bienvenido ${result.name}! Redirigiendo...`;
        messageEl.className = 'message success'; // Añadir clase 'success'
        setTimeout(() => {
            window.location.href = '../dashboard/dashboard.html';
        }, 1000);
    } else {
        messageEl.textContent = result.error || 'Error desconocido';
        messageEl.className = 'message error'; // Añadir clase 'error'
    }
    // Asegurar que el mensaje se muestre
    messageEl.style.display = 'block';
});