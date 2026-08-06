package Microservice.accounts.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDto {
	
	
	@NotEmpty(message = "Name can not be a null value")
	@Size(min = 5, max = 30, message = "The length of the customer name should be between 5 and 30")
	private String name;
	
	
	@NotEmpty(message = "Email can not be null value")
	@Email(message = "Email address should be valid")
	private String email;
	
	
	@Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile num must be 10 digits")
	private String mobileNumber;
	
	private AccountsDto accountsDto;

	
}
