package Microservice.accounts.controller.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import Microservice.accounts.entity.Accounts;
import Microservice.accounts.entity.Customer;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Long>{
	
	Optional<Accounts> findByCustomerId(Long customerId);
	
	@Transactional
	@Modifying
	void deleteByCustomerId(Long customerId);

}
