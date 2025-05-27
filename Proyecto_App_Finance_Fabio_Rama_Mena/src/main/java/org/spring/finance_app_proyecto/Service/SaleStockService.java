// File: src/main/java/org/spring/finance_app_proyecto/Service/StockSaleService.java
package org.spring.finance_app_proyecto.Service;

import org.spring.finance_app_proyecto.DTO.StockCreateDTO; // Importar StockCreateDTO
import org.spring.finance_app_proyecto.DTO.StockSaleRequestDTO;
import org.spring.finance_app_proyecto.DTO.StockSaleResponseDTO;
// import org.spring.finance_app_proyecto.DTO.StockToSellDTO; // ELIMINAR ESTA IMPORTACIÓN
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

    // Método para obtener stocks por ticker para la venta
    public List<StockCreateDTO> getStocksByTickerForSale(Integer userId, String ticker) { // Cambiar a StockCreateDTO
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

    // Nuevo método auxiliar para convertir Stock a StockCreateDTO (o reutilizar uno existente si ya lo tenías)
    // Este método es una copia del que tienes en StockService para evitar la dependencia circular.
    private StockCreateDTO convertToStockCreateDTO(Stock stock) {
        StockCreateDTO dto = new StockCreateDTO();
        dto.setId(stock.getId());
        dto.setTicker(stock.getTicker());
        dto.setPurchasePrice(stock.getPurchasePrice());
        dto.setQuantity(stock.getQuantity());
        dto.setPurchaseDate(stock.getPurchaseDate());
        return dto;
    }
}