package org.spring.finance_app_proyecto.Service;

import org.spring.finance_app_proyecto.DTO.StockCreateDTO;
import org.spring.finance_app_proyecto.Model.Stock;
import org.spring.finance_app_proyecto.Model.User;
import org.spring.finance_app_proyecto.Repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

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
        responseDto.setTicker(savedStock.getTicker());
        responseDto.setPurchasePrice(savedStock.getPurchasePrice());
        responseDto.setQuantity(savedStock.getQuantity());
        responseDto.setPurchaseDate(savedStock.getPurchaseDate());

        return responseDto;
    }
}