document.addEventListener('DOMContentLoaded', () => {
    // Obtener referencias a los elementos del DOM
    const tickerInput = document.getElementById('tickerInput');
    const sellDateInput = document.getElementById('sellDateInput');
    const filterButton = document.getElementById('filterButton');
    const salesTableBody = document.getElementById('salesTableBody');
    const messageDisplay = document.getElementById('messageDisplay');

    // Nuevas referencias para los elementos de resumen
    const totalGainLossDisplay = document.getElementById('totalGainLossDisplay');
    const percentageGainLossDisplay = document.getElementById('percentageGainLossDisplay');

    // Función para mostrar mensajes en la UI
    const showMessage = (message, type) => {
        messageDisplay.textContent = message;
        messageDisplay.className = `message ${type}`;
        messageDisplay.style.display = 'block';
    };

    // Función para ocultar mensajes
    const hideMessage = () => {
        messageDisplay.style.display = 'none';
        messageDisplay.textContent = '';
        messageDisplay.className = 'message';
    };

    // Función para actualizar la sección de resumen
    const updateSummary = (totalGainLoss, percentageGainLoss) => {
        // Formatear el total de ganancia/pérdida
        const formattedTotal = parseFloat(totalGainLoss).toFixed(2) + ' €';
        totalGainLossDisplay.textContent = formattedTotal;
        totalGainLossDisplay.className = parseFloat(totalGainLoss) >= 0 ? 'profit' : 'loss'; // Clase para color

        // Formatear el porcentaje de ganancia/pérdida
        const formattedPercentage = parseFloat(percentageGainLoss).toFixed(2) + ' %';
        percentageGainLossDisplay.textContent = formattedPercentage;
        percentageGainLossDisplay.className = parseFloat(percentageGainLoss) >= 0 ? 'profit' : 'loss'; // Clase para color
    };


    // Función principal para obtener y mostrar las ventas del usuario
    const fetchAndDisplaySales = async () => {
        hideMessage(); // Ocultar mensajes anteriores antes de una nueva búsqueda

        const ticker = tickerInput.value.trim();
        const sellDate = sellDateInput.value;

        let url = '/api/stock-sales/user-sales-history';
        const params = new URLSearchParams();

        if (ticker) {
            params.append('ticker', ticker);
        }
        if (sellDate) {
            params.append('sellDate', sellDate);
        }

        if (params.toString()) {
            url += '?' + params.toString();
        }

        try {
            const response = await fetch(url);

            if (!response.ok) {
                const errorData = await response.json();
                showMessage(`Error: ${errorData.error || response.statusText}`, 'error');
                salesTableBody.innerHTML = '<tr><td colspan="5">Error al cargar las ventas.</td></tr>';
                updateSummary(0, 0); // Resetear totales en caso de error
                return;
            }

            const data = await response.json(); // La respuesta es ahora un UserSalesSummaryDTO o un Map con 'message'

            salesTableBody.innerHTML = ''; // Limpiar el cuerpo de la tabla

            let sales = [];
            let totalGainLoss = 0;
            let percentageGainLoss = 0;
            let displayMessage = '';

            // Adaptar el manejo de la respuesta: puede ser UserSalesSummaryDTO o un Map con 'message'
            if (data.message) {
                displayMessage = data.message;
                // Si viene un mensaje, los totales también vienen, incluso si son 0
                totalGainLoss = data.totalGainLoss || 0;
                percentageGainLoss = data.percentageGainLoss || 0;
            } else if (Array.isArray(data.sales)) { // Si es un UserSalesSummaryDTO válido con lista de ventas
                sales = data.sales;
                totalGainLoss = data.totalGainLoss;
                percentageGainLoss = data.percentageGainLoss;
                displayMessage = sales.length > 0 ? `Se encontraron ${sales.length} ventas.` : 'No se encontraron ventas.';
            } else {
                // Caso inesperado o si no se encontraron ventas pero no vino 'message'
                displayMessage = 'No se encontraron ventas con los filtros aplicados.';
            }

            // Actualizar el resumen siempre, incluso si no hay ventas
            updateSummary(totalGainLoss, percentageGainLoss);

            // Mostrar el mensaje en el área de mensajes
            showMessage(displayMessage, 'success');

            // Rellenar la tabla de ventas
            if (sales.length > 0) {
                sales.forEach(sale => {
                    const row = salesTableBody.insertRow();
                    row.insertCell(0).textContent = sale.ticker;
                    row.insertCell(1).textContent = sale.soldQuantity;
                    row.insertCell(2).textContent = parseFloat(sale.sellPrice).toFixed(2) + ' €';
                    row.insertCell(3).textContent = sale.sellDate;
                    const gainLoss = parseFloat(sale.totalGainLoss);
                    const gainLossCell = row.insertCell(4);
                    gainLossCell.textContent = gainLoss.toFixed(2) + ' €';
                    gainLossCell.className = gainLoss >= 0 ? 'profit' : 'loss'; // Aplicar color por venta
                });
            } else {
                salesTableBody.innerHTML = '<tr><td colspan="5">No hay ventas para mostrar.</td></tr>';
            }

        } catch (error) {
            console.error('Error al obtener el historial de ventas:', error);
            showMessage('Error al conectar con el servidor o procesar los datos.', 'error');
            salesTableBody.innerHTML = '<tr><td colspan="5">Error al cargar las ventas.</td></tr>';
            updateSummary(0, 0); // Resetear totales en caso de error
        }
    };

    // --- Event Listeners ---

    // Cargar las últimas 10 ventas al cargar la página inicialmente
    fetchAndDisplaySales();

    // Listener para el botón "Actualizar Tabla"
    filterButton.addEventListener('click', fetchAndDisplaySales);

    // Listener para el input de Ticker (filtrar al presionar Enter)
    tickerInput.addEventListener('keypress', (event) => {
        if (event.key === 'Enter') {
            event.preventDefault();
            fetchAndDisplaySales();
        }
    });

    // Listener para el input de Fecha de Venta (filtrar al presionar Enter)
    sellDateInput.addEventListener('keypress', (event) => {
        if (event.key === 'Enter') {
            event.preventDefault();
            fetchAndDisplaySales();
        }
    });
});