package org.spring.finance_app_proyecto.Service;

import org.spring.finance_app_proyecto.DTO.StockCreateDTO;
import org.spring.finance_app_proyecto.DTO.StockSaleRequestDTO;
import org.spring.finance_app_proyecto.DTO.StockSaleResponseDTO;
import org.spring.finance_app_proyecto.DTO.UserSalesSummaryDTO; // Nuevo DTO
import org.spring.finance_app_proyecto.Model.Stock;
import org.spring.finance_app_proyecto.Model.StockSale;
import org.spring.finance_app_proyecto.Repository.SaleStockRepository;
import org.spring.finance_app_proyecto.Repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode; // Para redondear porcentajes
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaleStockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private SaleStockRepository saleStockRepository;

    // Método para obtener stocks por ticker para la venta (sin cambios)
    public List<StockCreateDTO> getStocksByTickerForSale(Integer userId, String ticker) {
        List<Stock> stocks = stockRepository.findByUserIdAndTickerContainingIgnoreCase(userId, ticker);
        return stocks.stream()
                .filter(stock -> stock.getQuantity() > 0) // Solo stocks con cantidad disponible
                .map(this::convertToStockCreateDTO) // Usar el método de conversión existente
                .collect(Collectors.toList());
    }

    @Transactional // (sin cambios)
    public StockSaleResponseDTO registerStockSale(StockSaleRequestDTO requestDTO, Integer userId) {
        // 1. Validar y obtener el stock original
        Stock stockToUpdate = stockRepository.findByIdAndUserId(requestDTO.getStockId(), userId)
                .orElseThrow(() -> new RuntimeException("Acción original no encontrada o no pertenece al usuario."));

        // 2. Validar cantidad a vender
        if (requestDTO.getQuantity() <= 0) {
            throw new IllegalArgumentException("La cantidad a vender debe ser mayor que cero.");
        }
        if (requestDTO.getQuantity() > stockToUpdate.getQuantity()) {
            throw new IllegalArgumentException("No puedes vender más acciones de las que posees para esta instancia. Cantidad disponible: " + stockToUpdate.getQuantity());
        }

        // 3. Crear el registro de venta
        StockSale stockSale = new StockSale();
        stockSale.setStock(stockToUpdate); // Relacionar con la instancia de Stock original
        stockSale.setQuantity(requestDTO.getQuantity());
        stockSale.setSellPrice(requestDTO.getSellPrice());
        stockSale.setSellDate(requestDTO.getSellDate());
        stockSale.setCreatedAt(LocalDate.now()); // Fecha de inserción actual

        StockSale savedSale = saleStockRepository.save(stockSale);

        // 4. Actualizar la cantidad en el stock original
        stockToUpdate.setQuantity(stockToUpdate.getQuantity() - requestDTO.getQuantity());
        stockRepository.save(stockToUpdate); // Guardar los cambios en el stock original

        // 5. Calcular ganancia/pérdida
        BigDecimal purchaseCostPerShare = stockToUpdate.getPurchasePrice();
        BigDecimal sellRevenuePerShare = requestDTO.getSellPrice();

        BigDecimal totalPurchaseCost = purchaseCostPerShare.multiply(BigDecimal.valueOf(requestDTO.getQuantity()));
        BigDecimal totalSellRevenue = sellRevenuePerShare.multiply(BigDecimal.valueOf(requestDTO.getQuantity()));
        BigDecimal totalGainLoss = totalSellRevenue.subtract(totalPurchaseCost);

        // 6. Construir y devolver el DTO de respuesta
        return new StockSaleResponseDTO(
                savedSale.getId(),
                stockToUpdate.getTicker(),
                savedSale.getQuantity(),
                savedSale.getSellPrice(),
                savedSale.getSellDate(),
                totalGainLoss,
                "Venta registrada exitosamente. Ganancia/Pérdida: " + totalGainLoss.toPlainString() + "€"
        );
    }

    /**
     * Obtiene una lista de ventas de acciones para un usuario,
     * opcionalmente filtradas por ticker y/o fecha de venta.
     * Si no se aplican filtros, se devuelven las últimas 10 ventas.
     * Adicionalmente, calcula el total de ganancia/pérdida y el porcentaje.
     *
     * @param userId El ID del usuario actual.
     * @param ticker El ticker de la acción para filtrar (opcional, puede ser null o vacío).
     * @param sellDate La fecha de venta para filtrar (opcional, puede ser null).
     * @return Un UserSalesSummaryDTO que contiene la lista de ventas y los totales.
     */
    public UserSalesSummaryDTO getFilteredUserSalesSummary(Integer userId, String ticker, LocalDate sellDate) {
        List<StockSale> sales;

        boolean hasTicker = ticker != null && !ticker.trim().isEmpty();
        boolean hasSellDate = sellDate != null;

        if (hasTicker && hasSellDate) {
            sales = saleStockRepository.findByUserIdAndStockTickerContainingIgnoreCaseAndSellDate(userId, ticker, sellDate);
        } else if (hasTicker) {
            sales = saleStockRepository.findByUserIdAndStockTickerContainingIgnoreCase(userId, ticker);
        } else if (hasSellDate) {
            sales = saleStockRepository.findByUserIdAndSellDate(userId, sellDate);
        } else {
            sales = saleStockRepository.findTop10ByUserIdOrderBySellDateDescCreatedAtDesc(userId);
        }

        // Mapear las entidades StockSale a DTOs de respuesta
        List<StockSaleResponseDTO> salesDTOs = sales.stream()
                .map(this::convertToStockSaleResponseDTO)
                .collect(Collectors.toList());

        // Calcular el total de ganancia/pérdida y el porcentaje
        BigDecimal totalGainLoss = BigDecimal.ZERO;
        BigDecimal totalPurchaseValue = BigDecimal.ZERO; // Valor total de compra de las acciones vendidas
        BigDecimal totalSellValue = BigDecimal.ZERO;     // Valor total de venta de las acciones vendidas

        for (StockSaleResponseDTO saleDTO : salesDTOs) {
            totalGainLoss = totalGainLoss.add(saleDTO.getTotalGainLoss());

            // Recalcular los valores de compra y venta para el porcentaje
            // Para esto, necesitamos el precio de compra original por acción
            // y el precio de venta por acción de cada StockSale.
            // Esto se obtiene al momento de convertir StockSale a StockSaleResponseDTO
            // donde calculamos totalPurchaseCost y totalSellRevenue.
            // Aquí podemos sumar los valores de cada DTO si ya los hemos mapeado.
            // Para evitar recalcularlo, podríamos añadir el totalPurchaseCost y totalSellRevenue
            // al StockSaleResponseDTO si fueran necesarios, pero el ejercicio pide el porcentaje
            // del TOTAL de ganancias/pérdidas.

            // Para el porcentaje, necesitamos el costo total de las acciones vendidas.
            // Para obtenerlo de forma precisa, iteramos sobre las entidades StockSale (no los DTOs convertidos)
            // o hacemos los cálculos en el convertToStockSaleResponseDTO y los acumulamos.
            // La forma más eficiente es acumularlos en la misma iteración que se calculó el totalGainLoss.
        }

        // Una forma más limpia de calcular los totales para el porcentaje:
        BigDecimal aggregatedTotalPurchaseValue = BigDecimal.ZERO;
        BigDecimal aggregatedTotalSellValue = BigDecimal.ZERO;

        for (StockSale sale : sales) {
            BigDecimal purchaseCostPerShare = sale.getStock().getPurchasePrice();
            BigDecimal sellRevenuePerShare = sale.getSellPrice();

            BigDecimal saleTotalPurchaseCost = purchaseCostPerShare.multiply(BigDecimal.valueOf(sale.getQuantity()));
            BigDecimal saleTotalSellRevenue = sellRevenuePerShare.multiply(BigDecimal.valueOf(sale.getQuantity()));

            aggregatedTotalPurchaseValue = aggregatedTotalPurchaseValue.add(saleTotalPurchaseCost);
            aggregatedTotalSellValue = aggregatedTotalSellValue.add(saleTotalSellRevenue);
        }


        BigDecimal percentageGainLoss = BigDecimal.ZERO;
        // Calcular el porcentaje solo si hay un valor de compra total para evitar división por cero
        if (aggregatedTotalPurchaseValue.compareTo(BigDecimal.ZERO) != 0) {
            // Fórmula: ((Valor Total de Venta - Valor Total de Compra) / Valor Total de Compra) * 100
            percentageGainLoss = (aggregatedTotalSellValue.subtract(aggregatedTotalPurchaseValue))
                    .divide(aggregatedTotalPurchaseValue, 4, RoundingMode.HALF_UP) // 4 decimales para precisión
                    .multiply(BigDecimal.valueOf(100));
        }


        return new UserSalesSummaryDTO(salesDTOs, totalGainLoss, percentageGainLoss);
    }

    // Nuevo método auxiliar para convertir Stock a StockCreateDTO (para el endpoint de búsqueda de stocks para vender)
    // (sin cambios)
    private StockCreateDTO convertToStockCreateDTO(Stock stock) {
        StockCreateDTO dto = new StockCreateDTO();
        dto.setId(stock.getId());
        dto.setTicker(stock.getTicker());
        dto.setPurchasePrice(stock.getPurchasePrice());
        dto.setQuantity(stock.getQuantity());
        dto.setPurchaseDate(stock.getPurchaseDate());
        return dto;
    }

    // Método auxiliar para convertir StockSale a StockSaleResponseDTO (sin cambios relevantes en la lógica)
    private StockSaleResponseDTO convertToStockSaleResponseDTO(StockSale stockSale) {
        // Para calcular la ganancia/pérdida, necesitamos el precio de compra del stock original.
        // Accedemos a él a través de la relación ManyToOne de StockSale con Stock.
        BigDecimal purchaseCostPerShare = stockSale.getStock().getPurchasePrice();
        BigDecimal sellRevenuePerShare = stockSale.getSellPrice();

        BigDecimal totalPurchaseCost = purchaseCostPerShare.multiply(BigDecimal.valueOf(stockSale.getQuantity()));
        BigDecimal totalSellRevenue = sellRevenuePerShare.multiply(BigDecimal.valueOf(stockSale.getQuantity()));
        BigDecimal totalGainLoss = totalSellRevenue.subtract(totalPurchaseCost);

        return new StockSaleResponseDTO(
                stockSale.getId(),
                stockSale.getStock().getTicker(), // Obtenemos el ticker del Stock asociado
                stockSale.getQuantity(),
                stockSale.getSellPrice(),
                stockSale.getSellDate(),
                totalGainLoss,
                "Venta completada." // Mensaje genérico para la visualización en la tabla
        );
    }
}