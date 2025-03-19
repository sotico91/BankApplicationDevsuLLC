package com.devsu.hackerearth.backend.account.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.devsu.hackerearth.backend.account.exceptions.ClientNotFoundException;
import com.devsu.hackerearth.backend.account.exceptions.TransactionNotFoundException;
import com.devsu.hackerearth.backend.account.model.TransactionType;
import com.devsu.hackerearth.backend.account.model.Transaction;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.BankStatementDto;

import com.devsu.hackerearth.backend.account.model.dto.ClientDto;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;
import com.devsu.hackerearth.backend.account.model.mapper.MapperTransaction;
import com.devsu.hackerearth.backend.account.repository.TransactionRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final WebClient.Builder webClientBuilder;
    private final MapperTransaction mapperTransaction;

    private static final String CLIENT_SERVICE_URL = "http://localhost:8001/api/clients";

    @Override
    public List<TransactionDto> getAll() {
        // Get all transactions
        return transactionRepository.findAll()
                .stream()
                .map(mapperTransaction::transactionToTransactionDto)
                .collect(Collectors.toList());
    }

    @Override
    public TransactionDto getById(Long id) {
        // Get transactions by id

        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("Transacción con id " + id + " no encontrada"));
        return mapperTransaction.transactionToTransactionDto(transaction);
    }

    @Transactional
    @Override
    public TransactionDto create(TransactionDto transactionDto) {
        // Create transaction

        AccountDto accountDto = accountService.getById(transactionDto.getAccountId());

        TransactionType transactionType;
        try {
            transactionType = TransactionType.valueOf(transactionDto.getType().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Tipo de transacción inválido: " + transactionDto.getType());
        }

        double newBalance = accountDto.getInitialAmount();

        switch (transactionType) {
            case DEPOSIT:
            case REFUND:
            case INTEREST_CREDIT:
                newBalance += transactionDto.getAmount();
                break;

            case WITHDRAWAL:
            case TRANSFER:
            case PAYMENT:
            case CHARGE:
            case FEE:
                if (newBalance < transactionDto.getAmount()) {
                    throw new IllegalStateException("Saldo insuficiente para esta operación");
                }
                newBalance -= transactionDto.getAmount();
                break;

            case REVERSAL:
            case ADJUSTMENT:
                newBalance += transactionDto.getAmount();
                break;

            default:
                throw new IllegalStateException("Tipo de transacción no soportado");
        }

        accountDto.setInitialAmount(newBalance);
        accountService.update(accountDto);

        Transaction transaction = mapperTransaction.transactionDtoToTransaction(transactionDto);
        transaction.setType(transactionType.name());
        transaction.setBalance(newBalance);
        Transaction transactionOut = transactionRepository.save(transaction);

        TransactionDto transactionDtoOut = mapperTransaction.transactionToTransactionDto(transactionOut);
        return transactionDtoOut;
    }

    @Override
    public List<BankStatementDto> getAllByAccountClientIdAndDateBetween(Long clientId, Date dateTransactionStart,
            Date dateTransactionEnd) {

        List<AccountDto> accounts = accountService.getAllAcccountsByClient(clientId);

        ClientDto client = webClientBuilder.build()
                .get()
                .uri(CLIENT_SERVICE_URL + "/" + clientId)
                .retrieve()
                .bodyToMono(ClientDto.class)
                .block();

        if (client == null) {
            throw new ClientNotFoundException(
                    "Cliente con id " + clientId + " no encontrado en el servicio de clientes");
        }

        List<BankStatementDto> transactions = accounts.stream()
                .flatMap(account -> transactionRepository
                        .findByAccountIdAndDateBetween(account.getId(), dateTransactionStart, dateTransactionEnd)
                        .stream()
                        .map(transaction -> mapperTransaction.transactionToBankStatementDto(transaction, account,
                                client.getName())))
                .collect(Collectors.toList());

        return transactions;
    }

    @Override
    public TransactionDto getLastByAccountId(Long accountId) {
        // If you need it

        Transaction transactionOut = transactionRepository.findTopByAccountIdOrderByDateDesc(accountId)
                .orElseThrow(
                        () -> new TransactionNotFoundException(
                                " Ultima transacción de la cuenta " + accountId + " no encontrada"));

        TransactionDto transactionDto = mapperTransaction.transactionToTransactionDto(transactionOut);
        return transactionDto;
    }

}