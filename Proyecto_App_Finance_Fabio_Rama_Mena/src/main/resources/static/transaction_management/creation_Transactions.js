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

    try {
        const response = await fetch('/api/transactions/create', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            credentials: 'include',
            body: JSON.stringify(transactionData)
        });

        const result = await response.json();
        const messageEl = document.getElementById('message');

        if (response.ok) {
            messageEl.textContent = 'Transacción creada correctamente';
            messageEl.style.color = 'green';
            document.getElementById('transactionForm').reset();
        } else {
            messageEl.textContent = result.error || 'Error al crear la transacción';
            messageEl.style.color = 'red';
        }
    } catch (error) {
        console.error('Error:', error);
        const messageEl = document.getElementById('message');
        messageEl.textContent = 'Fallo en la conexión con el servidor';
        messageEl.style.color = 'red';
    }
});
