package Microservice.loans.service.impl;

import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import Microservice.loans.constants.LoansConstants;
import Microservice.loans.dto.LoansDto;
import Microservice.loans.entity.Loans;
import Microservice.loans.exception.LoanAlreadyExistsException;
import Microservice.loans.exception.ResourceNotFoundException;
import Microservice.loans.mapper.LoansMapper;
import Microservice.loans.repository.LoansRepository;
import Microservice.loans.service.ILoansService;
import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class LoansServiceImpl implements ILoansService{
	
	
	private LoansRepository loansRepository;

	@Override
	public void createLoan(String mobileNumber) {
		Optional<Loans> optionalLoans = loansRepository.findByMobileNumber(mobileNumber);
		if(optionalLoans.isPresent()) {
			throw new LoanAlreadyExistsException("Loan already exists with this ph number" +mobileNumber);
		}
		loansRepository.save(createNewLoan(mobileNumber));
		
	}

	private Loans createNewLoan(String mobileNumber) {
		
		Loans newloan = new Loans();
		long randomLoanLoanNumber = 100000000L+ new Random().nextInt(90000000);
		newloan.setLoanNumber(Long.toString(randomLoanLoanNumber));
		newloan.setMobileNumber(mobileNumber);
		newloan.setLoanType(LoansConstants.HOME_LOAN);
		newloan.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
		newloan.setAmountPaid(0);
		newloan.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);
		return newloan;
	}

	@Override
	public LoansDto fetchLoan(String mobileNumber) {
		
		Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
				()-> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber));
		
		return LoansMapper.mapToLoansDto(loans, new LoansDto());
	}

	@Override
	public boolean updateLoan(LoansDto loansDto) {
		
		Loans loans = loansRepository.findByLoanNumber(loansDto.getLoanNumber()).orElseThrow(
				()-> new ResourceNotFoundException("Loan", "LoanNumber", loansDto.getLoanNumber()));
		
		LoansMapper.mapToLoans(loansDto, loans);
		loansRepository.save(loans);
	        return  true;
	    }

	@Override
	public boolean deleteLoan(String mobileNumber) {
		
		Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(()-> 
		new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber));
		loansRepository.deleteById(loans.getLoanId());
		return true;
	}

}
