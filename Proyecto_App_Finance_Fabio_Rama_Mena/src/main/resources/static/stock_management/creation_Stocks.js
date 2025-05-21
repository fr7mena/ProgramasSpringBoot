document.addEventListener('DOMContentLoaded', () => {
    const createStockForm = document.getElementById('createStockForm');

    createStockForm.addEventListener('submit', async (event) => {
        event.preventDefault();

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
                alert(responseData.message + ". ID de la acción creada: " + responseData.stockId);
                window.location.href = 'dashboard_stock.html'; // Redirigir a la página de éxito
            } else {
                alert('Error al crear la acción: ' + responseData.error); // Mostrar mensaje de error
            }
        } catch (error) {
            console.error('Error al enviar la solicitud:', error);
            alert('Ocurrió un error al crear la acción. Por favor, inténtalo de nuevo.'); // Mostrar error genérico
        }
    });
});