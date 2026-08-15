package Microservice.accounts.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Microservice.accounts.dto.CardsDto;

@FeignClient(name = "cards")
public interface CardsFeignClient {
	
	@GetMapping(value = "/api/fetch",consumes="application/json")
	public ResponseEntity<CardsDto> fetchCard(@RequestParam String mobileNumber);

}
