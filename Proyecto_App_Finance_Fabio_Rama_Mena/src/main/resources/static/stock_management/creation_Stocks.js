document.addEventListener('DOMContentLoaded', () => {
    const createStockForm = document.getElementById('createStockForm');
    const messageDisplay = document.getElementById('messageDisplay'); // Nuevo elemento para mostrar mensajes

    // Función para mostrar mensajes en la página
    function showMessage(message, isError = false) {
        // Asegurarse de que messageDisplay no sea null antes de intentar usarlo
        if (messageDisplay) {
            messageDisplay.textContent = message;
            // Usar las clases CSS para los mensajes de éxito/error
            messageDisplay.className = `message ${isError ? 'error' : 'success'}`;
            messageDisplay.style.display = 'block'; // Asegurarse de que el mensaje sea visible
        } else {
            console.error('Error: El elemento messageDisplay no se encontró en el DOM.');
            // Fallback a alert si el elemento no se encuentra, aunque no es lo deseado
            alert(message);
        }
    }

    createStockForm.addEventListener('submit', async (event) => {
        event.preventDefault();

        // Limpiar mensajes anteriores si el elemento existe
        if (messageDisplay) {
            messageDisplay.textContent = '';
            messageDisplay.style.display = 'none';
        }

        // Obtener los valores de los campos del formulario
        const ticker = document.getElementById('ticker').value;
        const purchasePrice = parseFloat(document.getElementById('purchasePrice').value);
        const quantity = parseInt(document.getElementById('quantity').value);
        const purchaseDate = document.getElementById('purchaseDate').value;

        // Crear un objeto con los datos del formulario
        const stockData = {
            ticker: ticker,
            purchasePrice: purchasePrice,
            quantity: quantity,
            purchaseDate: purchaseDate,
        };

        try {
            // Enviar la solicitud POST con los datos en el cuerpo
            const response = await fetch('/api/stocks/create', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json', // Indicar que los datos son JSON
                },
                body: JSON.stringify(stockData), // Convertir el objeto a JSON
                credentials: 'include',
            });

            // Parsear la respuesta JSON
            const responseData = await response.json();

            // Manejar la respuesta del servidor
            if (response.ok) {
                // Modificado para usar la clase 'success'
                showMessage(responseData.message /*+ ". ID de la acción creada: " + responseData.stockId*/, false);
                // Opcional: Redirigir después de un breve retraso para que el usuario vea el mensaje
                setTimeout(() => {
                    window.location.href = 'dashboard_stock.html';
                }, 2000); // Redirige después de 2 segundos
            } else {
                // Modificado para usar la clase 'error'
                showMessage('Error al crear la acción: ' + responseData.error, true); // Mostrar mensaje de error
            }
        } catch (error) {
            console.error('Error al enviar la solicitud:', error);
            // Modificado para usar la clase 'error'
            showMessage('Ocurrió un error al crear la acción. Por favor, inténtalo de nuevo.', true); // Mostrar error genérico
        }
    });
});