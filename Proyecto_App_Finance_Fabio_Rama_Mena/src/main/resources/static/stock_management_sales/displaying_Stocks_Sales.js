document.addEventListener('DOMContentLoaded', () => {
    // Obtener referencias a los elementos del DOM
    const tickerInput = document.getElementById('tickerInput');
    const sellDateInput = document.getElementById('sellDateInput');
    const filterButton = document.getElementById('filterButton');
    const salesTableBody = document.getElementById('salesTableBody');
    const messageDisplay = document.getElementById('messageDisplay');

    // Función para mostrar mensajes en la UI
    const showMessage = (message, type) => {
        messageDisplay.textContent = message;
        // Añade una clase para aplicar estilos (ej. 'success' o 'error')
        messageDisplay.className = `message ${type}`;
        messageDisplay.style.display = 'block'; // Asegura que el mensaje sea visible
    };

    // Función para ocultar mensajes
    const hideMessage = () => {
        messageDisplay.style.display = 'none';
        messageDisplay.textContent = '';
        messageDisplay.className = 'message'; // Resetea las clases
    };

    // Función principal para obtener y mostrar las ventas del usuario
    const fetchAndDisplaySales = async () => {
        hideMessage(); // Ocultar mensajes anteriores antes de una nueva búsqueda

        const ticker = tickerInput.value.trim(); // Obtener valor del input Ticker, eliminando espacios
        const sellDate = sellDateInput.value;   // Obtener valor del input Fecha de Venta (formato YYYY-MM-DD)

        let url = '/api/stock-sales/user-sales-history'; // Endpoint de la API

        // Construir los parámetros de la URL
        const params = new URLSearchParams();
        if (ticker) {
            params.append('ticker', ticker);
        }
        if (sellDate) {
            params.append('sellDate', sellDate);
        }

        // Si hay parámetros, añadirlos a la URL
        if (params.toString()) {
            url += '?' + params.toString();
        }

        try {
            const response = await fetch(url); // Realizar la petición GET al backend

            if (!response.ok) {
                // Si la respuesta no es OK (ej. 401 Unauthorized, 500 Internal Server Error)
                const errorData = await response.json();
                showMessage(`Error: ${errorData.error || response.statusText}`, 'error');
                salesTableBody.innerHTML = '<tr><td colspan="5">Error al cargar las ventas.</td></tr>'; // Limpiar y mostrar error en tabla
                return; // Salir de la función
            }

            const data = await response.json(); // Parsear la respuesta JSON

            salesTableBody.innerHTML = ''; // Limpiar el cuerpo de la tabla antes de añadir nuevas filas

            // Verificar si la respuesta contiene un mensaje (ej. "No se encontraron ventas")
            if (data.message) {
                showMessage(data.message, 'success'); // Mostrar mensaje del backend
                salesTableBody.innerHTML = '<tr><td colspan="5">' + data.message + '</td></tr>'; // Mensaje en la tabla
            } else if (Array.isArray(data) && data.length > 0) {
                // Si hay datos de ventas, iterar y añadirlos a la tabla
                data.forEach(sale => {
                    const row = salesTableBody.insertRow(); // Insertar una nueva fila
                    row.insertCell(0).textContent = sale.ticker;
                    row.insertCell(1).textContent = sale.soldQuantity;
                    row.insertCell(2).textContent = parseFloat(sale.sellPrice).toFixed(2) + ' €'; // Formatear a 2 decimales y añadir €
                    row.insertCell(3).textContent = sale.sellDate; // La fecha ya viene en formato YYYY-MM-DD
                    // Formatear Ganancia/Pérdida y añadir €
                    const gainLoss = parseFloat(sale.totalGainLoss).toFixed(2);
                    const gainLossCell = row.insertCell(4);
                    gainLossCell.textContent = gainLoss + ' €';
                    // Opcional: Cambiar color de la celda según ganancia o pérdida
                    if (parseFloat(gainLoss) > 0) {
                        gainLossCell.style.color = 'green';
                    } else if (parseFloat(gainLoss) < 0) {
                        gainLossCell.style.color = 'red';
                    }
                });
                showMessage(`Se encontraron ${data.length} ventas.`, 'success');
            } else {
                // No se encontraron ventas y no hay un mensaje específico del backend
                showMessage('No se encontraron ventas con los filtros aplicados.', 'success');
                salesTableBody.innerHTML = '<tr><td colspan="5">No se encontraron ventas.</td></tr>';
            }

        } catch (error) {
            console.error('Error al obtener el historial de ventas:', error);
            showMessage('Error al conectar con el servidor o procesar los datos.', 'error');
            salesTableBody.innerHTML = '<tr><td colspan="5">Error al cargar las ventas.</td></tr>';
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
            event.preventDefault(); // Evitar el envío por defecto del formulario
            fetchAndDisplaySales();
        }
    });

    // Listener para el input de Fecha de Venta (filtrar al presionar Enter)
    sellDateInput.addEventListener('keypress', (event) => {
        if (event.key === 'Enter') {
            event.preventDefault(); // Evitar el envío por defecto del formulario
            fetchAndDisplaySales();
        }
    });
});