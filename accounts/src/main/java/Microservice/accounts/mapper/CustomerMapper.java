package Microservice.accounts.mapper;

import Microservice.accounts.dto.CustomerDto;
import Microservice.accounts.entity.Customer;

public class CustomerMapper {
	
	public static CustomerDto mapToCustomerDto(Customer customer, CustomerDto customerDto) {
		
		customerDto.setEmail(customer.getEmail());
		customerDto.setName(customer.getName());
		customerDto.setMobileNumber(customer.getMobileNumber());
		return customerDto;
	}

	public static Customer mapToCustomer(CustomerDto customerDto, Customer customer) {
		
		customer.setName(customerDto.getName());
		customer.setMobileNumber(customerDto.getMobileNumber());
		customer.setEmail(customerDto.getEmail());
		return customer;
	}
}
