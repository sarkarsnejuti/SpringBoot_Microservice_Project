package Microservice.accounts.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AccountsDto {

	@NotEmpty(message = "Please put valid account number")
	@Pattern(regexp = "(^$|[0-9]{10})", message = "account num must be 12 digits")
	private long accountNumber;
	
	@NotEmpty(message = "Account type can not be null")
	private String accountType;
	
	@NotEmpty(message = "Branch Address can not be null")
	private String branchAddress;

}
