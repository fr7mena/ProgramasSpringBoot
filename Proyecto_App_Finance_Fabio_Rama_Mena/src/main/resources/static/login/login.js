document.getElementById('loginForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const name = document.getElementById('name').value;
    const password = document.getElementById('password').value;

    const response = await fetch('/api/users/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ name, password })
    });

    const result = await response.json();
    const messageEl = document.getElementById('loginMessage');
    if (response.ok) {
        messageEl.textContent = `¡Bienvenido ${result.name}! Redirigiendo...`;
        setTimeout(() => {
            window.location.href = '../dashboard/dashboard.html';
        }, 1000);
    } else {
        messageEl.textContent = result.error || 'Error desconocido';
    }
});