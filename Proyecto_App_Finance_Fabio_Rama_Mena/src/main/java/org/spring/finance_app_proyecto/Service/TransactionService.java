package org.spring.finance_app_proyecto.Service;

import org.spring.finance_app_proyecto.DTO.TransactionCreateDTO;
import org.spring.finance_app_proyecto.DTO.TransactionFilterDTO;
import org.spring.finance_app_proyecto.Model.Transaction;
import org.spring.finance_app_proyecto.Model.User;
import org.spring.finance_app_proyecto.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public BigDecimal getUserBalance(Integer userId) {
        List<Transaction> transactions = transactionRepository.findByUserIdOrderByTransactionDateDesc(userId, Pageable.unpaged());
        BigDecimal income = transactions.stream()
                .filter(tx -> "INCOME".equals(tx.getType()))
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal expense = transactions.stream()
                .filter(tx -> "EXPENSE".equals(tx.getType()))
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return income.subtract(expense);
    }

    public List<TransactionCreateDTO> getLast10Transactions(Integer userId) {
        Pageable topTen = PageRequest.of(0, 10);
        List<Transaction> lastTransactions = transactionRepository.findByUserIdOrderByTransactionDateDesc(userId, topTen);
        return lastTransactions.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<TransactionCreateDTO> getFilteredTransactions(Integer userId, TransactionFilterDTO filterDTO) {
        String type = filterDTO.getType();
        String category = filterDTO.getCategory();
        LocalDate startDate = filterDTO.getStartDate();
        LocalDate endDate = filterDTO.getEndDate();

        List<Transaction> filteredTransactions;

        if ("ALL".equals(type)) {
            if (category != null && !category.isEmpty() && !"ALL".equals(category)) {
                filteredTransactions = transactionRepository.findByUserIdAndCategoryAndTransactionDateBetween(
                        userId, category, startDate, endDate);
            } else {
                filteredTransactions = transactionRepository.findByUserIdAndTransactionDateBetween(
                        userId, startDate, endDate);
            }
        } else if ("INCOME".equals(type) || "EXPENSE".equals(type)) {
            if (category != null && !category.isEmpty() && !"ALL".equals(category)) {
                filteredTransactions = transactionRepository.findByUserIdAndTypeAndCategoryAndTransactionDateBetween(
                        userId, type, category, startDate, endDate);
            } else {
                filteredTransactions = transactionRepository.findByUserIdAndTypeAndTransactionDateBetween(
                        userId, type, startDate, endDate);
            }
        } else {
            filteredTransactions = new ArrayList<>();
        }
        return filteredTransactions.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private TransactionCreateDTO convertToDto(Transaction transaction) {
        TransactionCreateDTO dto = new TransactionCreateDTO();
        dto.setId(transaction.getId());
        dto.setType(transaction.getType());
        dto.setAmount(transaction.getAmount());
        dto.setCategory(transaction.getCategory());
        dto.setDescription(transaction.getDescription());
        dto.setTransactionDate(transaction.getTransactionDate());
        return dto;
    }
}