document.addEventListener('DOMContentLoaded', () => {
    const filterForm = document.getElementById('filterForm');
    const deleteForm = document.getElementById('deleteForm');
    const transactionsTableBody = document.getElementById('transactionsTableBody');
    const transactionsTableContainer = document.getElementById('transactionsTableContainer');
    const messageEl = document.getElementById('message');

    // Función para mostrar mensajes de feedback
    function showMessage(text, type) {
        messageEl.textContent = text;
        messageEl.className = 'message'; // Resetear a la clase base
        messageEl.classList.add(type); // Añadir clase 'success' o 'error'
        messageEl.style.display = 'block'; // Mostrar el mensaje
        setTimeout(() => {
            messageEl.style.display = 'none'; // Ocultar después de un tiempo
        }, 5000); // 5 segundos
    }

    filterForm.addEventListener('submit', async (e) => {
        e.preventDefault();

        const type = document.getElementById('type').value;
        const category = document.getElementById('category').value;
        const startDate = document.getElementById('startDate').value;
        const endDate = document.getElementById('endDate').value;

        // Ocultar mensajes anteriores al iniciar una nueva búsqueda
        messageEl.style.display = 'none';
        messageEl.textContent = '';
        messageEl.className = 'message';

        try {
            const response = await fetch('/api/transactions/filter', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                credentials: 'include',
                body: JSON.stringify({ type, category, startDate, endDate })
            });

            const result = await response.json();
            if (transactionsTableBody) {
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
                // No mostrar mensaje de éxito aquí, la tabla ya indica el resultado
            } else {
                if (transactionsTableContainer) {
                    transactionsTableContainer.style.display = 'none';
                }
                showMessage(result.error || 'No se encontraron transacciones con esos filtros.', 'error');
            }
        } catch (error) {
            console.error('Error al buscar transacciones:', error);
            showMessage('Fallo en la conexión con el servidor al buscar transacciones.', 'error');
        }
    });

    if (deleteForm) {
        deleteForm.addEventListener('submit', async (e) => {
            e.preventDefault();

            const checkboxes = document.querySelectorAll('input[name="transactionId"]:checked');
            const idsToDelete = Array.from(checkboxes).map(cb => parseInt(cb.value));

            // Ocultar mensajes anteriores al iniciar una nueva eliminación
            messageEl.style.display = 'none';
            messageEl.textContent = '';
            messageEl.className = 'message';

            if (idsToDelete.length === 0) {
                showMessage('Selecciona al menos una transacción para eliminar.', 'error');
                return;
            }

            try {
                const response = await fetch('/api/transactions/delete', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    credentials: 'include',
                    body: JSON.stringify(idsToDelete)
                });

                const result = await response.json();

                if (response.ok) {
                    showMessage(result.message || '¡Transacción(es) eliminada(s) correctamente!', 'success');
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
                    showMessage(result.error || 'Error al eliminar transacciones. Inténtalo de nuevo.', 'error');
                }
            } catch (error) {
                console.error('Error al eliminar transacciones:', error);
                showMessage('Fallo en la conexión con el servidor al eliminar transacciones.', 'error');
            }
        });
    }
});