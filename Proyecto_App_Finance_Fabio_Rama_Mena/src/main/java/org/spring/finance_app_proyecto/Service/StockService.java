// File: src/main/java/org/spring/finance_app_proyecto/Service/StockService.java
package org.spring.finance_app_proyecto.Service;

import org.spring.finance_app_proyecto.DTO.StockCreateDTO;
import org.spring.finance_app_proyecto.DTO.StockFilterDTO;
import org.spring.finance_app_proyecto.Model.Stock;
import org.spring.finance_app_proyecto.Model.User;
import org.spring.finance_app_proyecto.Repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public StockCreateDTO createStock(StockCreateDTO dto, Integer userId) {
        User user = userService.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Stock stock = new Stock();
        stock.setUser(user);
        stock.setTicker(dto.getTicker());
        stock.setPurchasePrice(dto.getPurchasePrice());
        stock.setQuantity(dto.getQuantity());
        stock.setPurchaseDate(dto.getPurchaseDate());
        stock.setCreatedAt(LocalDate.now());
        Stock savedStock = stockRepository.save(stock);

        StockCreateDTO responseDto = new StockCreateDTO();
        responseDto.setId(savedStock.getId()); // Asignar el ID del stock guardado al DTO
        responseDto.setTicker(savedStock.getTicker());
        responseDto.setPurchasePrice(savedStock.getPurchasePrice());
        responseDto.setQuantity(savedStock.getQuantity());
        responseDto.setPurchaseDate(savedStock.getPurchaseDate());

        return responseDto;
    }

    public BigDecimal getTotalInvested(Integer userId) {
        List<Stock> stocks = stockRepository.findByUserId(userId);
        return stocks.stream()
                .map(stock -> stock.getPurchasePrice().multiply(BigDecimal.valueOf(stock.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Modificado para devolver List<StockCreateDTO>
    public List<StockCreateDTO> getLast10Stocks(Integer userId) {
        Pageable topTen = PageRequest.of(0, 10);
        List<Stock> lastStocks = stockRepository.findByUserIdOrderByPurchaseDateDesc(userId, topTen);
        return lastStocks.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Modificado para devolver List<StockCreateDTO>
    public List<StockCreateDTO> getFilteredStocks(Integer userId, StockFilterDTO filterDTO) {
        String ticker = filterDTO.getTicker();
        LocalDate purchaseDate = filterDTO.getPurchaseDate();

        List<Stock> filteredStocks;

        if (ticker != null && !ticker.isEmpty() && purchaseDate != null) {
            filteredStocks = stockRepository.findByUserIdAndTickerAndPurchaseDate(userId, ticker, purchaseDate);
        } else if (ticker != null && !ticker.isEmpty()) {
            filteredStocks = stockRepository.findByUserIdAndTicker(userId, ticker);
        } else if (purchaseDate != null) {
            filteredStocks = stockRepository.findByUserIdAndPurchaseDate(userId, purchaseDate);
        } else {
            // Si no hay filtros, devolver todas las acciones del usuario
            filteredStocks = stockRepository.findByUserId(userId);
        }

        return filteredStocks.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Método auxiliar para convertir Stock a StockCreateDTO
    private StockCreateDTO convertToDto(Stock stock) {
        StockCreateDTO dto = new StockCreateDTO();
        dto.setId(stock.getId()); // Asegúrate de asignar el ID
        dto.setTicker(stock.getTicker());
        dto.setPurchasePrice(stock.getPurchasePrice());
        dto.setQuantity(stock.getQuantity());
        dto.setPurchaseDate(stock.getPurchaseDate());
        return dto;
    }
}