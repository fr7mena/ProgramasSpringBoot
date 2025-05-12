// File: src/main/resources/static/transaction_management/remove_Transactions.js

const filterForm = document.getElementById('filterForm');
const deleteForm = document.getElementById('deleteForm');
const transactionsSelect = document.getElementById('transactionsSelect');
const messageEl = document.getElementById('message');

let transactionMap = new Map(); // Guardar id -> descripción completa

filterForm.addEventListener('submit', async (e) => {
    e.preventDefault();

    const type = document.getElementById('type').value;
    const category = document.getElementById('category').value;
    const startDate = document.getElementById('startDate').value;
    const endDate = document.getElementById('endDate').value;

    const response = await fetch('/api/transactions/filter', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        credentials: 'include',
        body: JSON.stringify({ type, category, startDate, endDate })
    });

    const result = await response.json();
    transactionsSelect.innerHTML = '';
    transactionMap.clear();

    if (response.ok && result.length > 0) {
        result.forEach(tx => {
            const option = document.createElement('option');
            option.value = tx.id;
            option.textContent = `${tx.transactionDate} | ${tx.amount} € | ${tx.category} - ${tx.description}`;
            transactionsSelect.appendChild(option);
            transactionMap.set(tx.id, option.textContent);
        });

        deleteForm.style.display = 'block';
        messageEl.textContent = '';
    } else {
        deleteForm.style.display = 'none';
        messageEl.textContent = result.error || 'No se encontraron transacciones con esos filtros';
    }
});

deleteForm.addEventListener('submit', async (e) => {
    e.preventDefault();

    const selectedOptions = Array.from(transactionsSelect.selectedOptions);
    const ids = selectedOptions.map(opt => parseInt(opt.value));

    if (ids.length === 0) {
        messageEl.textContent = 'Selecciona al menos una transacción para eliminar.';
        return;
    }

    const response = await fetch('/api/transactions/delete', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        credentials: 'include',
        body: JSON.stringify(ids)
    });

    const result = await response.json();

    if (response.ok) {
        messageEl.textContent = result.message;
        selectedOptions.forEach(opt => opt.remove());
    } else {
        messageEl.textContent = result.error || 'Error al eliminar transacciones';
    }
});
