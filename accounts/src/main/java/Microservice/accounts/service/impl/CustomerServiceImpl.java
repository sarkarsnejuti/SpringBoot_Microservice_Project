package Microservice.accounts.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import Microservice.accounts.controller.repository.AccountsRepository;
import Microservice.accounts.controller.repository.CustomerRepository;
import Microservice.accounts.dto.AccountsDto;
import Microservice.accounts.dto.CardsDto;
import Microservice.accounts.dto.CustomerDetailsDto;
import Microservice.accounts.entity.Accounts;
import Microservice.accounts.entity.Customer;
import Microservice.accounts.exception.ResourceNotFoundException;
import Microservice.accounts.mapper.AccountsMapper;
import Microservice.accounts.mapper.CustomerMapper;
import Microservice.accounts.service.ICustomersService;
import Microservice.accounts.service.client.CardsFeignClient;
import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomersService{
	
	
	private AccountsRepository accountsRepository;
	private CustomerRepository customerRepository;
	private CardsFeignClient cardsFeignClient;

	@Override
	public CustomerDetailsDto fetchCustomerDetails(String mobileNumber) {
		
		Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(()-> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
		
		Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(()-> 
		new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString()));
		
		CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
		customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));
		
	ResponseEntity<CardsDto>cardsDtoResponseEntity = cardsFeignClient.fetchCard(mobileNumber);
	customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());
	
	return customerDetailsDto;
	}

}
