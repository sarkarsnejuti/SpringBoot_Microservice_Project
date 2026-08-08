package Microservice.cards.sevice;

import Microservice.cards.dto.CardsDto;

public interface ICardsService {
	
	void CreateCard(String mobileNumber);
	
	CardsDto fetchCard(String mobileNumber);
	
	boolean updateCard(CardsDto cardsDto);
	
	boolean deleteCard(String mobileNumber);

}
