document.addEventListener('DOMContentLoaded', () => {
    const balanceContainer = document.getElementById('balanceContainer');
    const currentBalance = document.getElementById('currentBalance');
    const latestTransactionsTableBody = document.getElementById('latestTransactionsTableBody');
    const filterDashboardForm = document.getElementById('filterDashboardForm');
    const filteredTransactionsContainer = document.getElementById('filteredTransactionsContainer');
    const filteredTransactionsTableBody = document.getElementById('filteredTransactionsTableBody');

    // Función para formatear la fecha
    function formatDate(dateString) {
        if (!dateString) return '';
        const date = new Date(dateString);
        return date.toLocaleDateString();
    }

    // Cargar el saldo actual
    fetch('/api/transactions/balance', {
        credentials: 'include'
    })
        .then(response => response.json())
        .then(data => {
            currentBalance.textContent = data.balance ? parseFloat(data.balance).toFixed(2) : '0.00';
        })
        .catch(error => {
            console.error('Error al cargar el saldo:', error);
            currentBalance.textContent = 'Error al cargar';
        });

    // Cargar las últimas 10 transacciones
    fetch('/api/transactions/latest-transactions', {
        credentials: 'include'
    })
        .then(response => response.json())
        .then(transactions => {
            latestTransactionsTableBody.innerHTML = '';
            if (transactions && transactions.length > 0) {
                transactions.forEach(tx => {
                    const row = latestTransactionsTableBody.insertRow();
                    row.insertCell().textContent = formatDate(tx.transactionDate);
                    row.insertCell().textContent = tx.type;
                    row.insertCell().textContent = tx.amount ? parseFloat(tx.amount).toFixed(2) : '0.00';
                    row.insertCell().textContent = tx.category || '';
                    row.insertCell().textContent = tx.description || '';
                });
            } else {
                latestTransactionsTableBody.insertRow().insertCell().colSpan = 5;
                latestTransactionsTableBody.rows[0].textContent = 'No hay transacciones recientes.';
            }
        })
        .catch(error => {
            console.error('Error al cargar las últimas transacciones:', error);
            latestTransactionsTableBody.innerHTML = '<tr><td colspan="5">Error al cargar</td></tr>';
        });

    // Evento para actualizar la tabla con transacciones filtradas
    filterDashboardForm.addEventListener('submit', async (e) => {
        e.preventDefault();

        const type = document.getElementById('type').value;
        const startDate = document.getElementById('startDate').value || null;
        const endDate = document.getElementById('endDate').value || null;

        const filterData = {
            type: type, // CAMBIO AQUÍ: Envía el valor directamente
            startDate: startDate,
            endDate: endDate
        };

        console.log("Datos enviados para filtrar:", filterData);

        const response = await fetch('/api/transactions/filter-dashboard', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            credentials: 'include',
            body: JSON.stringify(filterData)
        });

        const result = await response.json();
        console.log("Respuesta del backend:", result);

        filteredTransactionsTableBody.innerHTML = '';
        filteredTransactionsContainer.style.display = 'block';

        if (response.ok && result.length > 0) {
            result.forEach(tx => {
                const row = filteredTransactionsTableBody.insertRow();
                row.insertCell().textContent = formatDate(tx.transactionDate);
                row.insertCell().textContent = tx.type;
                row.insertCell().textContent = tx.amount ? parseFloat(tx.amount).toFixed(2) : '0.00';
                row.insertCell().textContent = tx.category || '';
                row.insertCell().textContent = tx.description || '';
            });
        } else {
            filteredTransactionsTableBody.insertRow().insertCell().colSpan = 5;
            filteredTransactionsTableBody.rows[0].textContent = 'No se encontraron transacciones con estos filtros.';
        }
    });
});