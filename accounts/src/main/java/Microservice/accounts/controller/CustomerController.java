package Microservice.accounts.controller;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Microservice.accounts.dto.CustomerDetailsDto;
import Microservice.accounts.service.ICustomersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;

@RestController
@RequestMapping("/api")
@Validated
@Tag(name = "REST API in Eazy Bank to Fetch Customer details")
public class CustomerController {
	
	private final ICustomersService iCustomersService;
	
	public CustomerController(ICustomersService iCustomersService) {
		this.iCustomersService = iCustomersService;
	}
	
	
	@Operation(summary = "Fetch Customer Details Rest Api")
	@GetMapping("/fetchCustomerDetails")
	public ResponseEntity<CustomerDetailsDto>fetchCustomerDeatails(@RequestParam
			@Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile num must be 10 digits") String mobileNumber){
		
		
		CustomerDetailsDto customerDetailsDto = iCustomersService.fetchCustomerDetails(mobileNumber);		
		return ResponseEntity.status(HttpStatus.SC_OK).body(customerDetailsDto);
		
	}

}
