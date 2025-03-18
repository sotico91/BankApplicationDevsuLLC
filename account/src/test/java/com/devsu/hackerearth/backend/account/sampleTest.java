package com.devsu.hackerearth.backend.account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.devsu.hackerearth.backend.account.controller.AccountController;
import com.devsu.hackerearth.backend.account.model.TransactionType;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;
import com.devsu.hackerearth.backend.account.service.AccountService;
import com.devsu.hackerearth.backend.account.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class sampleTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private AccountService accountService;

	@Autowired
	private AccountController accountController;

	@MockBean
	private TransactionService transactionService;

	@BeforeAll
	static void setUp() {
		System.setProperty("spring.datasource.url", "jdbc:h2:mem:testdb;MODE=PostgreSQL");
		System.setProperty("spring.datasource.username", "sa");
		System.setProperty("spring.datasource.password", "");
	}

	@BeforeEach
	void setup() {
		TransactionDto transaction = new TransactionDto(1L, new Date(), TransactionType.DEPOSIT.name(), 100.0, 500.0,
				1L);
		when(transactionService.getById(1L)).thenReturn(transaction);
	}

	@Test
	void testCreateTransaction() throws Exception {
		TransactionDto transactionDto = new TransactionDto(null, new Date(), "DEPOSIT", 100.0, 0.0, 1L);
		TransactionDto savedTransaction = new TransactionDto(1L, new Date(), "DEPOSIT", 100.0, 500.0, 1L);

		when(transactionService.create(any(TransactionDto.class))).thenReturn(savedTransaction);

		mockMvc.perform(post("/api/transactions")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(transactionDto)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.accountId").value(1L))
				.andExpect(jsonPath("$.amount").value(100.0))
				.andExpect(jsonPath("$.type").value("DEPOSIT"));
	}

	@Test
	void testGetTransactionById() throws Exception {
		mockMvc.perform(get("/api/transactions/{id}", 1L))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.accountId").exists())
				.andExpect(jsonPath("$.amount").exists())
				.andExpect(jsonPath("$.type").exists());
	}

	@Test
	void createAccountTest() {

		AccountDto newAccount = new AccountDto(1L, "number", "savings", 0.0, true, 1L);
		AccountDto createdAccount = new AccountDto(1L, "number", "savings", 0.0, true, 1L);
		when(accountService.create(newAccount)).thenReturn(createdAccount);

		ResponseEntity<AccountDto> response = accountController.create(newAccount);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(createdAccount, response.getBody());
	}
}
