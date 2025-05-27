package org.spring.finance_app_proyecto.Service;

import org.spring.finance_app_proyecto.DTO.StockCreateDTO;
import org.spring.finance_app_proyecto.DTO.StockSaleRequestDTO;
import org.spring.finance_app_proyecto.DTO.StockSaleResponseDTO;
import org.spring.finance_app_proyecto.Model.Stock;
import org.spring.finance_app_proyecto.Model.StockSale;
import org.spring.finance_app_proyecto.Repository.SaleStockRepository;
import org.spring.finance_app_proyecto.Repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaleStockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private SaleStockRepository saleStockRepository;

    // Método para obtener stocks por ticker para la venta (ya existente)
    public List<StockCreateDTO> getStocksByTickerForSale(Integer userId, String ticker) {
        List<Stock> stocks = stockRepository.findByUserIdAndTickerContainingIgnoreCase(userId, ticker);
        return stocks.stream()
                .filter(stock -> stock.getQuantity() > 0) // Solo stocks con cantidad disponible
                .map(this::convertToStockCreateDTO) // Usar el método de conversión existente
                .collect(Collectors.toList());
    }

    @Transactional
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
     *
     * @param userId El ID del usuario actual.
     * @param ticker El ticker de la acción para filtrar (opcional, puede ser null o vacío).
     * @param sellDate La fecha de venta para filtrar (opcional, puede ser null).
     * @return Una lista de StockSaleResponseDTO que representan las ventas filtradas.
     */
    public List<StockSaleResponseDTO> getFilteredUserSales(Integer userId, String ticker, LocalDate sellDate) {
        List<StockSale> sales;

        boolean hasTicker = ticker != null && !ticker.trim().isEmpty();
        boolean hasSellDate = sellDate != null;

        if (hasTicker && hasSellDate) {
            // Filtrar por ticker y fecha de venta
            sales = saleStockRepository.findByUserIdAndStockTickerContainingIgnoreCaseAndSellDate(userId, ticker, sellDate);
        } else if (hasTicker) {
            // Filtrar solo por ticker
            sales = saleStockRepository.findByUserIdAndStockTickerContainingIgnoreCase(userId, ticker);
        } else if (hasSellDate) {
            // Filtrar solo por fecha de venta
            sales = saleStockRepository.findByUserIdAndSellDate(userId, sellDate);
        } else {
            // Si no hay filtros, obtener las últimas 10 ventas del usuario
            sales = saleStockRepository.findTop10ByUserIdOrderBySellDateDescCreatedAtDesc(userId);
        }

        // Mapear las entidades StockSale a DTOs de respuesta
        return sales.stream()
                .map(this::convertToStockSaleResponseDTO)
                .collect(Collectors.toList());
    }

    // Nuevo método auxiliar para convertir Stock a StockCreateDTO (para el endpoint de búsqueda de stocks para vender)
    private StockCreateDTO convertToStockCreateDTO(Stock stock) {
        StockCreateDTO dto = new StockCreateDTO();
        dto.setId(stock.getId());
        dto.setTicker(stock.getTicker());
        dto.setPurchasePrice(stock.getPurchasePrice());
        dto.setQuantity(stock.getQuantity());
        dto.setPurchaseDate(stock.getPurchaseDate());
        return dto;
    }

    // Método auxiliar para convertir StockSale a StockSaleResponseDTO (para el historial de ventas)
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