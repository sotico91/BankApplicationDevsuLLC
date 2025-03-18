package com.devsu.hackerearth.backend.account.model.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.PartialAccountDto;

@Component
public class MapperAccount {

        public Account accountDtoToAccount(AccountDto accountDto) {

                Account account = new Account();
                account.setNumber(accountDto.getNumber());
                account.setType(accountDto.getType());
                account.setInitialAmount(accountDto.getInitialAmount());
                account.setClientId(accountDto.getClientId());
                account.setActive(accountDto.isActive());

                return account;
        }

        public AccountDto accountToAccountDto(Account account) {

                return AccountDto.builder()
                                .id(account.getId())
                                .clientId(account.getClientId())
                                .initialAmount(account.getInitialAmount())
                                .number(account.getNumber())
                                .type(account.getType())
                                .isActive(account.isActive())
                                .build();
        }

        public List<AccountDto> accountListToAccountDtoList(List<Account> accounts) {
                return accounts.stream()
                                .map(this::accountToAccountDto)
                                .collect(Collectors.toList());
        }

        public Account accountDtoInToAccountBd(AccountDto accountBd, AccountDto accountDtoIn) {

                Account account = new Account();
                account.setNumber(
                                (accountDtoIn.getNumber() != null && accountDtoIn.getNumber() != accountBd.getNumber())
                                                ? accountDtoIn.getNumber()
                                                : accountBd.getNumber());
                account.setType((accountDtoIn.getType() != null && accountDtoIn.getType() != accountBd.getType())
                                ? accountDtoIn.getType()
                                : accountBd.getNumber());
                account.setInitialAmount(accountDtoIn.getInitialAmount() != accountBd.getInitialAmount()
                                ? accountDtoIn.getInitialAmount()
                                : accountBd.getInitialAmount());
                account.setClientId((accountDtoIn.getClientId() != null
                                && accountDtoIn.getClientId() != accountBd.getClientId())
                                                ? accountDtoIn.getClientId()
                                                : accountBd.getClientId());
                account.setActive((accountDtoIn.isActive() != accountBd.isActive())
                                ? accountDtoIn.isActive()
                                : accountBd.isActive());

                account.setId(accountBd.getId());

                return account;

        }

        public Account accountPartialDtoInToAccountBd(AccountDto accountBd, PartialAccountDto partialAccountDto) {

                Account account = new Account();

                account.setActive(accountBd.isActive() != partialAccountDto.getIsActive()
                                ? partialAccountDto.getIsActive()
                                : accountBd.isActive());

                account.setNumber(accountBd.getNumber() != null ? accountBd.getNumber() : null);
                account.setType(accountBd.getType() != null ? accountBd.getType() : null);
                account.setInitialAmount(accountBd.getInitialAmount() != 0 ? accountBd.getInitialAmount() : null);
                account.setClientId(accountBd.getClientId() != null ? accountBd.getClientId() : null);

                account.setId(accountBd.getId());

                return account;
        }

}
