package Microservice.accounts.service;

import Microservice.accounts.dto.CustomerDetailsDto;

public interface ICustomersService {
	
	CustomerDetailsDto fetchCustomerDetails(String mobileNumber);

}
