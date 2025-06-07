document.getElementById('registerForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const name = document.getElementById('name').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    const response = await fetch('/api/users/register', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ name, email, password })
    });

    const result = await response.json();
    const messageEl = document.getElementById('registerMessage');
    if (response.ok) {
        messageEl.textContent = `Usuario ${result.name} registrado con éxito.`;
        messageEl.className = 'message success'; // Añadir clase 'success'
    } else {
        messageEl.textContent = result.error || 'Error desconocido';
        messageEl.className = 'message error'; // Añadir clase 'error'
    }
    // Asegurar que el mensaje se muestre
    messageEl.style.display = 'block';
});