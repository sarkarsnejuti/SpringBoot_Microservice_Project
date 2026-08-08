package Microservice.cards.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Microservice.cards.constants.CardConstants;
import Microservice.cards.dto.CardsDto;
import Microservice.cards.dto.ResponseDto;
import Microservice.cards.sevice.ICardsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/")
@AllArgsConstructor
@Validated
@Tag(name = "CARD REST API for Eazy Bank")
public class CardsController {
	
	private ICardsService iCardsService;
	
	
	@Operation(summary = "Create new Card for User")
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createCard(@Valid @RequestParam  @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits") String mobileNumber){
			
		iCardsService.CreateCard(mobileNumber);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(CardConstants.STATUS_201, CardConstants.MESSAGE_201));
	}
	
	@Operation(summary = "Fetch Card details for User")
	@GetMapping("/fetch")
	public ResponseEntity<CardsDto> fetchCard(@RequestParam  @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits") String mobileNumber){
		
		CardsDto cardsDto = iCardsService.fetchCard(mobileNumber);
		return ResponseEntity.status(HttpStatus.OK).body(cardsDto);
	}
	
	@Operation(summary = "Update Card details for User")
	@PutMapping("/update")
	public ResponseEntity<ResponseDto>updateCardDetails(@Valid @RequestBody CardsDto cardsDto) {
		
		boolean isUpdated = iCardsService.updateCard(cardsDto);
		if(isUpdated) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseDto(CardConstants.STATUS_200, CardConstants.MESSAGE_200));
		}else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ResponseDto(CardConstants.STATUS_417, CardConstants.MESSAGE_417_UPDATE));
		}
	}
	
	@Operation(summary = "Delete Card details for User")
	@DeleteMapping("/delete")
	public ResponseEntity<ResponseDto>deleteCardDetails(@RequestParam @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits") String mobileNumber){
		
		boolean isDelete = iCardsService.deleteCard(mobileNumber);
		if(isDelete) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseDto(CardConstants.STATUS_200, CardConstants.MESSAGE_200));
		}else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ResponseDto(CardConstants.STATUS_417, CardConstants.MESSAGE_417_DELETE));
		}
	}
	
	

}
