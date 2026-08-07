package Microservice.loans.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class LoansDto {
	
	
	@NotEmpty(message = "Mobile number can't be null or empty")
	@Pattern(regexp="(^$|[0-9]{10})",message = "Mobile Number must be 10 digits")
	private String mobileNumber;
	
	
	@NotEmpty(message = "Loan number can't be null or empty")
	@Pattern(regexp="(^$|[0-9]{10})",message = "Loan Number must be 9 digits")
	private String loanNumber;
	
	@NotEmpty(message = "Loan type can't be null or empty")
	private String loanType;
	
	@Positive(message = "Total Loan should be greater than zero")
	private int totalLoan;
	
	@PositiveOrZero(message = "Amount Paid should be equal or greater than zero")
	private int amountPaid;
	
	
	@PositiveOrZero(message = "OutStanding amount should be equal or greater than zero")
	private int outstandingAmount;

}
