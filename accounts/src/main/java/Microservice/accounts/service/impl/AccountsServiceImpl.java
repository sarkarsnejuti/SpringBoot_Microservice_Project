package Microservice.accounts.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import Microservice.accounts.constants.AccountsCostants;
import Microservice.accounts.controller.repository.AccountsRepository;
import Microservice.accounts.controller.repository.CustomerRepository;
import Microservice.accounts.dto.AccountsDto;
import Microservice.accounts.dto.CustomerDto;
import Microservice.accounts.entity.Accounts;
import Microservice.accounts.entity.Customer;
import Microservice.accounts.exception.CustomerAlreadyExistsException;
import Microservice.accounts.exception.ResourceNotFoundException;
import Microservice.accounts.mapper.AccountsMapper;
import Microservice.accounts.mapper.CustomerMapper;
import Microservice.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService{
	
	private AccountsRepository accountsRepository;
	private CustomerRepository customerRepository;

	@Override
	public void createAccount(CustomerDto customerDto) {
		
		Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
	   Optional<Customer>optionalcustomer =  customerRepository.findByMobileNumber(customerDto.getMobileNumber());
		if(optionalcustomer.isPresent()) {
			
			throw new CustomerAlreadyExistsException("Customer already registered with the given mobile no" +customerDto.getMobileNumber());
		}
		/*
		 * customer.setCreatedAt(LocalDateTime.now());
		 * customer.setCreatedBy("Anonymous");
		 */
		Customer savedCustomer = customerRepository.save(customer);
		accountsRepository.save(createNewAccount(savedCustomer));
		
		
	}
	
	private Accounts createNewAccount(Customer customer) {
		
		Accounts newAccount = new Accounts();
		
		newAccount.setCustomerId(customer.getCustomerId());
		long accountNo = 10000000L + new Random().nextInt(9999999);
		
		newAccount.setAccountNumber(accountNo);
		newAccount.setAccountType(AccountsCostants.SAVINGS);
		newAccount.setBranchAddress(AccountsCostants.ADDRESS);
		/*
		 * newAccount.setCreatedAt(LocalDateTime.now());
		 * newAccount.setCreatedBy("Anonymous");
		 */
		return newAccount;
		
	}

	@Override
	public CustomerDto fetchAccount(String mobileNumber) {
		
		Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
				()-> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
		
		Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
				()-> new ResourceNotFoundException("Account", "customerId", String.valueOf(customer.getCustomerId())));
		
		CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
		customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));
		return customerDto;
	}

	@Override
	public boolean updateAccount(CustomerDto customerDto) {
		
		boolean isUpdated = false;
		
		AccountsDto accountsDto = customerDto.getAccountsDto();
		if(accountsDto != null) {
			Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(()->
			new ResourceNotFoundException("Account", "AccountNumber", String.valueOf(accountsDto.getAccountNumber())));
			
			AccountsMapper.mapToAccounts(accountsDto, accounts);
			accounts = accountsRepository.save(accounts);
			
			Long customerId = accounts.getCustomerId();
			
			Customer customer = customerRepository.findById(customerId).orElseThrow(()->
			new ResourceNotFoundException("Customer", "CustomerID", customerId.toString()));
			
			CustomerMapper.mapToCustomer(customerDto, customer);
			customerRepository.save(customer);
			isUpdated = true;
		}
		return isUpdated;
	}

	@Override
	public boolean deleteAccount(String mobileNumber) {
		
		Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(()->
		new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
		
		accountsRepository.deleteByCustomerId(customer.getCustomerId());
		customerRepository.deleteById(customer.getCustomerId());
		return true;
	}

}
