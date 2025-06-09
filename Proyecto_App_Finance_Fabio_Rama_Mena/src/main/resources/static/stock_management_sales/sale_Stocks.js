// File: src/main/resources/static/stock_management_sales/sale_Stocks.js
document.addEventListener('DOMContentLoaded', () => {
    const messageDisplay = document.getElementById('messageDisplay');
    const findStockForm = document.getElementById('findStockForm');
    const stocksListContainer = document.getElementById('stocksListContainer');
    const stocksTableBody = document.getElementById('stocksTableBody');
    const selectStockForm = document.getElementById('selectStockForm');
    const selectStockBtn = document.getElementById('selectStockBtn');
    const saleFormContainer = document.getElementById('saleFormContainer');
    const saleDetailsForm = document.getElementById('saleDetailsForm');
    const selectedStockIdInput = document.getElementById('selectedStockId');
    const selectedStockTickerSpan = document.getElementById('selectedStockTicker');
    const selectedStockPurchasePriceInput = document.getElementById('selectedStockPurchasePrice');
    const selectedStockQuantityInput = document.getElementById('selectedStockQuantity');
    const quantityToSellInput = document.getElementById('quantityToSell');
    const sellPriceInput = document.getElementById('sellPrice');
    const sellDateInput = document.getElementById('sellDate');

    let selectedStockData = null; // Para almacenar los datos de la acción seleccionada

    // Función para mostrar mensajes
    // Utiliza las clases 'message success' y 'message error' definidas en styles.css
    function showMessage(message, isError = false) {
        messageDisplay.textContent = message;
        messageDisplay.className = `message ${isError ? 'error' : 'success'}`; // Usa la clase 'message' base
        messageDisplay.style.display = 'block';
    }

    // Ocultar mensaje después de un tiempo
    function hideMessage() {
        setTimeout(() => {
            messageDisplay.style.display = 'none';
            messageDisplay.textContent = ''; // Limpiar el contenido también
            messageDisplay.className = 'message'; // Resetear a solo la clase base 'message'
        }, 5000); // 5 segundos
    }

    // Formatear la fecha para mostrarla
    function formatDate(dateString) {
        if (!dateString) return '';
        const date = new Date(dateString);
        return date.toLocaleDateString('es-ES', { year: 'numeric', month: '2-digit', day: '2-digit' });
    }

    // --- Primer Formulario: Buscar Acciones por Ticker ---
    findStockForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        hideMessage(); // Limpiar y ocultar mensajes antes de la búsqueda
        stocksListContainer.style.display = 'none'; // Ocultar tabla de stocks
        saleFormContainer.style.display = 'none'; // Ocultar formulario de venta
        stocksTableBody.innerHTML = ''; // Limpiar tabla
        selectStockBtn.style.display = 'none'; // Ocultar botón de selección

        const ticker = document.getElementById('tickerSearch').value.trim();

        if (!ticker) {
            showMessage('Por favor, introduce un Ticker para buscar.', true); // true para error
            return;
        }

        try {
            const response = await fetch(`/api/stock-sales/find-by-ticker?ticker=${ticker}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                },
                credentials: 'include'
            });

            const data = await response.json();

            if (response.ok) {
                if (Array.isArray(data) && data.length > 0) { // Asegurarse de que data es un array
                    data.forEach(stock => {
                        const row = stocksTableBody.insertRow();
                        // Los data-attributes usarán los nombres de propiedades de StockCreateDTO
                        row.insertCell().innerHTML = `<input type="radio" name="selectedStock" value="${stock.id}"
                                data-ticker="${stock.ticker}"
                                data-purchase-price="${stock.purchasePrice}"
                                data-quantity="${stock.quantity}"
                                data-purchase-date="${stock.purchaseDate}">`;
                        row.insertCell().textContent = stock.id;
                        row.insertCell().textContent = stock.ticker;
                        row.insertCell().textContent = parseFloat(stock.purchasePrice).toFixed(2);
                        row.insertCell().textContent = stock.quantity;
                        row.insertCell().textContent = formatDate(stock.purchaseDate);
                    });
                    stocksListContainer.style.display = 'block'; // Mostrar la tabla de stocks
                    selectStockBtn.style.display = 'block'; // Mostrar botón de selección
                    showMessage(`Se encontraron ${data.length} acciones disponibles para vender con el ticker "${ticker}".`, false); // false para éxito
                } else if (data.message) { // Si el backend envía un objeto con 'message' cuando no hay resultados
                    showMessage(data.message, false); // Mostrar como éxito si es un mensaje informativo
                    stocksListContainer.style.display = 'none';
                    selectStockBtn.style.display = 'none';
                }
                else { // Si es un array vacío o respuesta inesperada
                    showMessage('No se encontraron acciones con ese ticker para vender o no tienes acciones disponibles.', false); // false para éxito
                    stocksListContainer.style.display = 'none';
                    selectStockBtn.style.display = 'none';
                }
            } else {
                showMessage(data.error || 'Error al buscar acciones.', true); // Mostrar error del backend
            }
        } catch (error) {
            console.error('Error al buscar acciones:', error);
            showMessage('Ocurrió un error al buscar acciones.', true); // true para error
        }
    });

    // --- Segundo Formulario: Seleccionar Instancia de Stock ---
    selectStockForm.addEventListener('submit', (e) => {
        e.preventDefault();
        hideMessage(); // Limpiar y ocultar mensajes

        const selectedRadio = document.querySelector('input[name="selectedStock"]:checked');

        if (!selectedRadio) {
            showMessage('Por favor, selecciona una acción para vender.', true); // true para error
            return;
        }

        // Las propiedades de selectedStockData provienen de los data-attributes, que a su vez reflejan StockCreateDTO
        selectedStockData = {
            id: selectedRadio.value,
            ticker: selectedRadio.dataset.ticker,
            purchasePrice: parseFloat(selectedRadio.dataset.purchasePrice),
            quantity: parseInt(selectedRadio.dataset.quantity),
            purchaseDate: selectedRadio.dataset.purchaseDate
        };

        // Rellenar campos ocultos y span visible
        selectedStockIdInput.value = selectedStockData.id;
        selectedStockTickerSpan.textContent = selectedStockData.ticker;
        selectedStockPurchasePriceInput.value = selectedStockData.purchasePrice;
        selectedStockQuantityInput.value = selectedStockData.quantity;

        // Establecer el max para la cantidad a vender
        quantityToSellInput.max = selectedStockData.quantity;
        quantityToSellInput.value = ''; // Limpiar el valor previo
        sellPriceInput.value = ''; // Limpiar el valor previo
        sellDateInput.valueAsDate = new Date(); // Establecer fecha actual por defecto

        stocksListContainer.style.display = 'none'; // Ocultar tabla de selección
        saleFormContainer.style.display = 'block'; // Mostrar formulario de venta
        showMessage(`Acción '${selectedStockData.ticker}' seleccionada.`, false); // false para éxito
        hideMessage(); // Ocultar el mensaje de éxito después de un tiempo
    });

    // --- Tercer Formulario: Registrar Venta ---
    saleDetailsForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        hideMessage(); // Limpiar y ocultar mensajes

        const quantityToSell = parseInt(quantityToSellInput.value);
        const sellPrice = parseFloat(sellPriceInput.value);
        const sellDate = sellDateInput.value; // Formato 'YYYY-MM-DD'

        // Validaciones del lado del cliente
        if (!selectedStockData) {
            showMessage('No hay ninguna acción seleccionada.', true);
            return;
        }
        if (isNaN(quantityToSell) || quantityToSell <= 0) {
            showMessage('La cantidad a vender debe ser un número positivo.', true);
            return;
        }
        if (quantityToSell > selectedStockData.quantity) {
            showMessage(`No puedes vender más acciones de las que posees (${selectedStockData.quantity}).`, true);
            return;
        }
        if (isNaN(sellPrice) || sellPrice <= 0) {
            showMessage('El precio de venta debe ser un número positivo.', true);
            return;
        }
        if (!sellDate) {
            showMessage('Por favor, selecciona una fecha de venta.', true);
            return;
        }

        // Calcular ganancia/pérdida provisional para el popup
        const purchaseCost = selectedStockData.purchasePrice * quantityToSell;
        const sellRevenue = sellPrice * quantityToSell;
        const gainLoss = sellRevenue - purchaseCost;

        const confirmMessage = `¿Estás seguro de que quieres registrar la venta de ${quantityToSell} acciones de ${selectedStockData.ticker} (ID: ${selectedStockData.id}) por ${sellPrice.toFixed(2)}€ cada una?\n\nCon esta venta, ${gainLoss >= 0 ? 'ganarías' : 'perderías'} ${Math.abs(gainLoss).toFixed(2)}€.`;

        if (!confirm(confirmMessage)) {
            showMessage('Venta cancelada.', false); // false para éxito
            hideMessage();
            return;
        }

        // Datos a enviar al backend
        const saleData = {
            stockId: selectedStockData.id,
            quantity: quantityToSell,
            sellPrice: sellPrice,
            sellDate: sellDate
        };

        try {
            const response = await fetch('/api/stock-sales/sell', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                credentials: 'include',
                body: JSON.stringify(saleData)
            });

            const result = await response.json();

            if (response.ok) {
                showMessage(result.message || 'Venta registrada exitosamente.', false); // false para éxito
                // Resetear formularios y estados
                findStockForm.reset();
                stocksListContainer.style.display = 'none';
                saleFormContainer.style.display = 'none';
                selectedStockData = null; // Limpiar los datos de la acción seleccionada
                hideMessage(); // Ocultar el mensaje de éxito después de un tiempo
            } else {
                showMessage(result.error || 'Error al registrar la venta.', true); // true para error
            }
        } catch (error) {
            console.error('Error al registrar la venta:', error);
            showMessage('Ocurrió un error al registrar la venta. Por favor, inténtalo de nuevo.', true); // true para error
        }
    });

    // Establecer la fecha actual por defecto en el campo de fecha de venta
    // Esto se hará cuando se seleccione una acción, en el listener de selectStockForm
});