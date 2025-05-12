document.addEventListener('DOMContentLoaded', () => {
    const filterForm = document.getElementById('filterForm');
    const deleteForm = document.getElementById('deleteForm');
    const transactionsTableBody = document.getElementById('transactionsTableBody');
    const transactionsTableContainer = document.getElementById('transactionsTableContainer');
    const messageEl = document.getElementById('message');

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
        if (transactionsTableBody) { // Añadimos una comprobación para asegurarnos de que el elemento existe
            transactionsTableBody.innerHTML = '';
        }

        if (response.ok && result.length > 0) {
            result.forEach(tx => {
                const row = transactionsTableBody.insertRow();

                const selectCell = row.insertCell();
                const checkbox = document.createElement('input');
                checkbox.type = 'checkbox';
                checkbox.name = 'transactionId';
                checkbox.value = tx.id;
                checkbox.classList.add('delete-checkbox');
                selectCell.appendChild(checkbox);

                const dateCell = row.insertCell();
                dateCell.textContent = tx.transactionDate || '';

                const amountCell = row.insertCell();
                amountCell.textContent = tx.amount ? `${tx.amount} €` : '';

                const categoryCell = row.insertCell();
                categoryCell.textContent = tx.category || '';

                const descriptionCell = row.insertCell();
                descriptionCell.textContent = tx.description || '';
            });

            if (transactionsTableContainer) {
                transactionsTableContainer.style.display = 'block';
            }
            if (messageEl) {
                messageEl.textContent = '';
            }
        } else {
            if (transactionsTableContainer) {
                transactionsTableContainer.style.display = 'none';
            }
            if (messageEl) {
                messageEl.textContent = result.error || 'No se encontraron transacciones con esos filtros';
            }
        }
    });

    if (deleteForm) {
        deleteForm.addEventListener('submit', async (e) => {
            e.preventDefault();

            const checkboxes = document.querySelectorAll('input[name="transactionId"]:checked');
            const idsToDelete = Array.from(checkboxes).map(cb => parseInt(cb.value));

            if (messageEl) {
                if (idsToDelete.length === 0) {
                    messageEl.textContent = 'Selecciona al menos una transacción para eliminar.';
                    return;
                }
            }

            const response = await fetch('/api/transactions/delete', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                credentials: 'include',
                body: JSON.stringify(idsToDelete)
            });

            const result = await response.json();

            if (response.ok) {
                if (messageEl) {
                    messageEl.textContent = result.message;
                }
                checkboxes.forEach(checkbox => {
                    const rowToRemove = checkbox.closest('tr');
                    if (rowToRemove && transactionsTableBody) {
                        transactionsTableBody.removeChild(rowToRemove);
                    }
                });
                if (transactionsTableBody && transactionsTableBody.rows.length === 0 && transactionsTableContainer) {
                    transactionsTableContainer.style.display = 'none';
                }
            } else {
                if (messageEl) {
                    messageEl.textContent = result.error || 'Error al eliminar transacciones';
                }
            }
        });
    }
});