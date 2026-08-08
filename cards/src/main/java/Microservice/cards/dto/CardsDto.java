package Microservice.cards.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class CardsDto {
	
	@NotEmpty(message = "Mobile number can't be null")
	private String mobileNumber;
	
	
	@NotEmpty(message = "Card number can't be null")
	private String cardNumber;
	
	@NotEmpty(message = "Card Type can't be null")
	private String cardType;
	
	@Positive(message = "Total card limit should be greater than 0")
	private int totalLimit;
	
	@PositiveOrZero(message = "Amount used limit should be 0 or more than 0")
	private int amountUsed;
	
	@PositiveOrZero(message = "Amount used limit should be 0 or more than 0")
	private int availableAmount;

}
