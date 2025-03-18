package com.devsu.hackerearth.backend.account.model.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.devsu.hackerearth.backend.account.model.Transaction;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.BankStatementDto;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;

@Component
public class MapperTransaction {

    public Transaction transactionDtoToTransaction(TransactionDto transactionDto) {
        Transaction transaction = new Transaction();
        transaction.setDate(transactionDto.getDate());
        transaction.setType(transactionDto.getType());
        transaction.setAmount(transactionDto.getAmount());
        transaction.setBalance(transactionDto.getBalance());
        transaction.setAccountId(transactionDto.getAccountId());
        return transaction;
    }

    public TransactionDto transactionToTransactionDto(Transaction transaction) {
        return TransactionDto.builder()
                .id(transaction.getId())
                .date(transaction.getDate())
                .type(transaction.getType())
                .amount(transaction.getAmount())
                .balance(transaction.getBalance())
                .accountId(transaction.getAccountId())
                .build();
    }

    public List<TransactionDto> transactionListToTransactionDtoList(List<Transaction> transactions) {
        return transactions.stream()
                .map(this::transactionToTransactionDto)
                .collect(Collectors.toList());
    }

    public BankStatementDto transactionToBankStatementDto(Transaction transaction, AccountDto accountDto,
            String clientName) {
        return BankStatementDto.builder()
                .date(transaction.getDate())
                .client(clientName)
                .accountNumber(accountDto.getNumber())
                .accountType(accountDto.getType())
                .initialAmount(accountDto.getInitialAmount())
                .isActive(accountDto.isActive())
                .transactionType(transaction.getType())
                .amount(transaction.getAmount())
                .balance(transaction.getBalance())
                .build();
    }

}
