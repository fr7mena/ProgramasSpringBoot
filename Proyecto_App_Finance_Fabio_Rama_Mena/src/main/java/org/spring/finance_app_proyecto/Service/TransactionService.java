package org.spring.finance_app_proyecto.Service;

import org.spring.finance_app_proyecto.DTO.TransactionCreateDTO;
import org.spring.finance_app_proyecto.DTO.TransactionFilterDTO;
import org.spring.finance_app_proyecto.Model.Transaction;
import org.spring.finance_app_proyecto.Model.User;
import org.spring.finance_app_proyecto.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction createTransaction(TransactionCreateDTO dto, User user) {
        Transaction tx = new Transaction();
        tx.setUser(user);
        tx.setType(dto.getType());
        tx.setAmount(dto.getAmount());
        tx.setCategory(dto.getCategory());
        tx.setDescription(dto.getDescription());
        tx.setTransactionDate(dto.getTransactionDate());
        return transactionRepository.save(tx);
    }

    public List<Transaction> findByFilter(Integer userId, String type, String category, LocalDate start, LocalDate end) {
        return transactionRepository.findByUserIdAndTypeAndCategoryAndTransactionDateBetween(
                userId, type, category, start, end);
    }

    public void deleteByIds(List<Integer> ids) {
        transactionRepository.deleteAllById(ids);
    }
}