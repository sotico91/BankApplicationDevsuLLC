package com.devsu.hackerearth.backend.account.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.account.exceptions.AccountAlreadyExistsException;
import com.devsu.hackerearth.backend.account.exceptions.AccountNotFoundException;
import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.PartialAccountDto;
import com.devsu.hackerearth.backend.account.model.mapper.MapperAccount;
import com.devsu.hackerearth.backend.account.repository.AccountRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final MapperAccount mapperAccount;

    @Override
    public List<AccountDto> getAll() {
        // Get all accounts

        List<Account> accounts = accountRepository.findAll();
        List<AccountDto> accountListOut = mapperAccount.accountListToAccountDtoList(accounts);
        return accountListOut;
    }

    @Override
    public AccountDto getById(Long id) {
        // Get accounts by id

        Account accoutnOut = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Cuenta con id " + id + " no encontrado"));
        AccountDto accountDto = mapperAccount.accountToAccountDto(accoutnOut);
        return accountDto;
    }

    @Transactional
    @Override
    public AccountDto create(AccountDto accountDto) {
        // Create account
        if (accountRepository.findByNumber(accountDto.getNumber()).isPresent()) {
            throw new AccountAlreadyExistsException("El número de cuenta ya existe");
        }

        Account accountIn = mapperAccount.accountDtoToAccount(accountDto);
        Account accountOut = accountRepository.save(accountIn);
        AccountDto accountDtoOut = mapperAccount.accountToAccountDto(accountOut);

        return accountDtoOut;
    }

    @Transactional
    @Override
    public AccountDto update(Long id, AccountDto accountDto) {
        // Update account

        AccountDto accountDtoBd = getById(id);
        Account accountIn = mapperAccount.accountDtoInToAccountBd(accountDtoBd, accountDto);
        Account accountOut = accountRepository.save(accountIn);
        AccountDto accountDtoOut = mapperAccount.accountToAccountDto(accountOut);

        return accountDtoOut;
    }

    @Transactional
    @Override
    public AccountDto partialUpdate(Long id, PartialAccountDto partialAccountDto) {
        // Partial update account

        AccountDto accountDtoBd = getById(id);
        Account accountIn = mapperAccount.accountPartialDtoInToAccountBd(accountDtoBd, partialAccountDto);
        Account accountOut = accountRepository.save(accountIn);
        AccountDto accountDtoOut = mapperAccount.accountToAccountDto(accountOut);
        return accountDtoOut;
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        // Delete account

        getById(id);
        accountRepository.deleteById(id);
    }

    @Override
    public List<AccountDto> getAllAcccountsByClient(Long clientId) {

        List<Account> accounts = accountRepository.findByClientId(clientId);
        List<AccountDto> accountsList = mapperAccount.accountListToAccountDtoList(accounts);
        return accountsList;
    }

}
