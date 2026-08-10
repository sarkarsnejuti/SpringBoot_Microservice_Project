package Microservice.accounts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
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

import Microservice.accounts.constants.AccountsCostants;
import Microservice.accounts.dto.AccountsContactInfoDto;
import Microservice.accounts.dto.CustomerDto;
import Microservice.accounts.dto.ResponseDto;
import Microservice.accounts.service.IAccountsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;


@Tag(name = "CURD REST APIs for Accounts in EazyBank")
@RestController
@RequestMapping("/api")
/* @AllArgsConstructor */
@Validated
public class AccountsController {
	
	private IAccountsService iAccountsService;
	
	@Autowired
	public AccountsController(IAccountsService iAccountsService) {
		this.iAccountsService = iAccountsService;
	}
	
	
	@Value("${build.version}")
	private String buildVersion;
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private AccountsContactInfoDto accountsContactInfoDto;
	
	
	@Operation(summary = "Create Account for REST Api")
	@PostMapping("/create")
	public ResponseEntity<ResponseDto>createAccount(@Valid @RequestBody CustomerDto customerDto){
		
		iAccountsService.createAccount(customerDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(AccountsCostants.STATUS_201, AccountsCostants.MESSAGE_201));
		
	}
	
	@Operation(summary = "Fetch user details of EazyBank")
	@GetMapping("/fetch")
	public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile num must be 10 digits") String mobileNumber){
		
		CustomerDto customerDto = iAccountsService.fetchAccount(mobileNumber);
		return ResponseEntity.status(HttpStatus.OK).body(customerDto);
	}

	@Operation(summary = "Update the user details of EazyBank")
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto){
		
		boolean isUpdated = iAccountsService.updateAccount(customerDto);
		
		if(isUpdated) {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(AccountsCostants.STATUS_200, AccountsCostants.MESSAGE_200));
		}else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(AccountsCostants.STATUS_500, AccountsCostants.MESSAGE_500));
		}
	}
	
	@Operation(summary = "Delete the User details of EazyBank")
	@DeleteMapping("/delete")
	public ResponseEntity<ResponseDto>deleteAccountDetails(@RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile num must be 10 digits") String mobileNumber){
		boolean isDelete = iAccountsService.deleteAccount(mobileNumber);
		
		if(isDelete) {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(AccountsCostants.STATUS_200, AccountsCostants.MESSAGE_200));
		}else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(AccountsCostants.STATUS_500, AccountsCostants.MESSAGE_500));
		}
	}
	
	@GetMapping("/build-info")
	public ResponseEntity<String>getBuildInfo(){
		return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
	}
	
	@GetMapping("/maven-version")
	public ResponseEntity<String>getJavaVersion(){
		return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("MAVEN_HOME"));
	}
	
	@GetMapping("/contact-info")
	public ResponseEntity<AccountsContactInfoDto>getcontactInfo(){
		return ResponseEntity.status(HttpStatus.OK).body(accountsContactInfoDto);
	}
	
}
