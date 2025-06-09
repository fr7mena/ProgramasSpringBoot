// File: src/main/resources/static/transaction_management/creation_Transactions.js

document.getElementById('transactionForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const type = document.getElementById('type').value;
    const amount = parseFloat(document.getElementById('amount').value);
    const category = document.getElementById('category').value;
    const description = document.getElementById('description').value;
    const transactionDate = document.getElementById('transactionDate').value;

    const transactionData = {
        type,
        amount,
        category,
        description,
        transactionDate
    };

    const messageEl = document.getElementById('message');
    // Limpiar clases y texto anteriores
    messageEl.textContent = '';
    messageEl.className = 'message'; // Resetear a la clase base

    try {
        const response = await fetch('/api/transactions/create', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            credentials: 'include',
            body: JSON.stringify(transactionData)
        });

        const result = await response.json();

        if (response.ok) {
            messageEl.textContent = '¡Transacción creada correctamente!';
            messageEl.classList.add('success'); // Añadir clase de éxito
            document.getElementById('transactionForm').reset();
        } else {
            messageEl.textContent = result.error || 'Error al crear la transacción. Inténtalo de nuevo.';
            messageEl.classList.add('error'); // Añadir clase de error
        }
    } catch (error) {
        console.error('Error:', error);
        messageEl.textContent = 'Fallo en la conexión con el servidor. Por favor, verifica tu conexión.';
        messageEl.classList.add('error'); // Añadir clase de error
    } finally {
        messageEl.style.display = 'block'; // Asegurarse de que el mensaje sea visible
        setTimeout(() => {
            messageEl.style.display = 'none'; // Ocultar el mensaje después de un tiempo
        }, 5000); // 5 segundos
    }
});