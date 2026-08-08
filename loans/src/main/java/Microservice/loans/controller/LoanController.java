package Microservice.loans.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Microservice.loans.constants.LoansConstants;
import Microservice.loans.dto.LoansDto;
import Microservice.loans.dto.ResponseDto;
import Microservice.loans.service.ILoansService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Validated
@Tag(name = "LOAN REST Api for EazyBank")
public class LoanController {
	
	private ILoansService iLoansService;
	
	@Operation(summary = "Create New Loan")
	@PostMapping("/create")
	public ResponseEntity<ResponseDto>createLoan(@RequestParam  @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits") String mobileNumber){
		
		iLoansService.createLoan(mobileNumber);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(LoansConstants.STATUS_201, LoansConstants.MESSAGE_201));
	}
	
	@Operation(summary = "Fetch Loan details")
	@GetMapping("/fetch")
	public ResponseEntity<LoansDto> fetchLoan(@RequestParam
            @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")String mobileNumber){
		
		LoansDto loansDto = iLoansService.fetchLoan(mobileNumber);
		return ResponseEntity.status(HttpStatus.OK).body(loansDto);
	}
	
	@Operation(summary = "Delete Loan details")
	@DeleteMapping("/delete")
	public ResponseEntity<ResponseDto> deleteLoanDetails(@RequestParam  @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits") String mobileNumber){
		
		boolean isDelete = iLoansService.deleteLoan(mobileNumber);
		if(isDelete) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseDto(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200));
		}else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ResponseDto(LoansConstants.STATUS_417, LoansConstants.MESSAGE_417_DELETE));
		}
	}
	
	@Operation(summary = "Update Loan details")
	@PutMapping("/update")
	public ResponseEntity<ResponseDto>updateLoanDetails(@Valid @RequestBody LoansDto loansDto) {
		
		boolean isUpdate = iLoansService.updateLoan(loansDto);
		
		if(isUpdate) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseDto(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200));
		}else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ResponseDto(LoansConstants.STATUS_417, LoansConstants.MESSAGE_417_UPDATE));
		}
	}
	

}
