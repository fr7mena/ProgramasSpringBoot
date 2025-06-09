// File: src/main/resources/static/stock_management/displaying_Stocks.js
document.addEventListener('DOMContentLoaded', () => {
    const totalInvestedContainer = document.getElementById('totalInvestedContainer');
    const currentTotalInvested = document.getElementById('currentTotalInvested');
    const latestStocksTableBody = document.getElementById('latestStocksTableBody');
    const filterStocksForm = document.getElementById('filterStocksForm');
    const filteredStocksContainer = document.getElementById('filteredStocksContainer');
    const filteredStocksTableBody = document.getElementById('filteredStocksTableBody');
    const messageDisplay = document.getElementById('messageDisplay');

    // Función para formatear la fecha
    function formatDate(dateString) {
        if (!dateString) return '';
        const date = new Date(dateString);
        return date.toLocaleDateString(); // Formato local de la fecha
    }

    // Función para mostrar mensajes en la página
    function showMessage(message, isError = false) {
        if (messageDisplay) {
            messageDisplay.textContent = message;
            // Usar las clases CSS para los mensajes de éxito/error
            messageDisplay.className = `message ${isError ? 'error' : 'success'}`;
            messageDisplay.style.display = 'block';
        } else {
            console.error('Error: El elemento messageDisplay no se encontró en el DOM.');
        }
    }

    // Cargar el total invertido
    async function loadTotalInvested() {
        try {
            const response = await fetch('/api/stocks/total-invested', {
                credentials: 'include'
            });
            const data = await response.json();
            if (response.ok) {
                currentTotalInvested.textContent = data.totalInvested ? parseFloat(data.totalInvested).toFixed(2) : '0.00';
            } else {
                showMessage('Error al cargar el total invertido: ' + data.error, true);
                currentTotalInvested.textContent = 'Error al cargar';
            }
        } catch (error) {
            console.error('Error al cargar el total invertido:', error);
            showMessage('Ocurrió un error al cargar el total invertido.', true);
            currentTotalInvested.textContent = 'Error al cargar';
        }
    }

    // Cargar las últimas 10 acciones
    async function loadLatestStocks() {
        try {
            const response = await fetch('/api/stocks/latest-stocks', {
                credentials: 'include'
            });
            const stocks = await response.json();
            latestStocksTableBody.innerHTML = ''; // Limpiar tabla

            if (response.ok && stocks && stocks.length > 0) {
                stocks.forEach(stock => {
                    const row = latestStocksTableBody.insertRow();
                    row.insertCell().textContent = stock.ticker;
                    row.insertCell().textContent = stock.purchasePrice ? parseFloat(stock.purchasePrice).toFixed(2) + ' €' : '0.00 €';
                    row.insertCell().textContent = stock.quantity;
                    row.insertCell().textContent = formatDate(stock.purchaseDate);
                });
            } else {
                const row = latestStocksTableBody.insertRow();
                const cell = row.insertCell();
                cell.colSpan = 4; // Ocupar todas las columnas
                cell.textContent = 'No hay acciones recientes.';
                cell.style.textAlign = 'center'; // Centrar el mensaje
            }
        } catch (error) {
            console.error('Error al cargar las últimas acciones:', error);
            showMessage('Ocurrió un error al cargar las últimas acciones.', true);
            latestStocksTableBody.innerHTML = '<tr><td colspan="4">Error al cargar</td></tr>';
        }
    }

    // Evento para actualizar la tabla con acciones filtradas
    filterStocksForm.addEventListener('submit', async (e) => {
        e.preventDefault();

        // Limpiar mensajes anteriores
        showMessage('', false); // Limpiar y ocultar

        const ticker = document.getElementById('tickerFilter').value.trim();
        const purchaseDate = document.getElementById('purchaseDateFilter').value || null; // null si está vacío

        const filterData = {
            ticker: ticker,
            purchaseDate: purchaseDate
        };

        // Si ambos campos de filtro están vacíos, no enviar la solicitud y mostrar todas las acciones
        if (!ticker && !purchaseDate) {
            filteredStocksContainer.style.display = 'none'; // Asegurarse de ocultar la tabla filtrada
            showMessage('Por favor, introduce al menos un criterio de búsqueda.', true);
            return;
        }

        try {
            const response = await fetch('/api/stocks/filter-stocks', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                credentials: 'include',
                body: JSON.stringify(filterData)
            });

            const result = await response.json();
            filteredStocksTableBody.innerHTML = ''; // Limpiar tabla
            filteredStocksContainer.style.display = 'block'; // Mostrar contenedor

            if (response.ok && result && Array.isArray(result) && result.length > 0) {
                result.forEach(stock => {
                    const row = filteredStocksTableBody.insertRow();
                    row.insertCell().textContent = stock.ticker;
                    row.insertCell().textContent = stock.purchasePrice ? parseFloat(stock.purchasePrice).toFixed(2) + ' €' : '0.00 €';
                    row.insertCell().textContent = stock.quantity;
                    row.insertCell().textContent = formatDate(stock.purchaseDate);
                });
                showMessage(`Se encontraron ${result.length} acciones con los filtros aplicados.`, false);
            } else if (response.ok && result && result.message) { // Manejar mensaje del backend si no hay resultados
                filteredStocksTableBody.insertRow().insertCell().colSpan = 4;
                filteredStocksTableBody.rows[0].textContent = result.message;
                filteredStocksTableBody.rows[0].style.textAlign = 'center';
                showMessage(result.message, false);
            }
            else { // Caso de error o lista vacía sin mensaje
                filteredStocksTableBody.insertRow().insertCell().colSpan = 4;
                filteredStocksTableBody.rows[0].textContent = 'No se encontraron acciones con estos filtros.';
                filteredStocksTableBody.rows[0].style.textAlign = 'center';
                showMessage('No se encontraron acciones con estos filtros.', true);
            }
        } catch (error) {
            console.error('Error al filtrar acciones:', error);
            showMessage('Ocurrió un error al filtrar acciones. Por favor, inténtalo de nuevo.', true);
            filteredStocksTableBody.innerHTML = '<tr><td colspan="4">Error al cargar</td></tr>';
        }
    });

    // Cargar datos al inicio
    loadTotalInvested();
    loadLatestStocks();
});