package Microservice.cards.mapper;

import Microservice.cards.dto.CardsDto;
import Microservice.cards.entity.Cards;

public class CardsMapper {
	
	public static CardsDto mapToCardsDto(Cards cards, CardsDto cardsDto) {
		
		cardsDto.setCardNumber(cards.getCardNumber());
		cardsDto.setCardType(cards.getCardType());
		cardsDto.setMobileNumber(cards.getMobileNumber());
		cardsDto.setAmountUsed(cards.getAmountUsed());
		cardsDto.setAvailableAmount(cards.getAvailableAmount());
		cardsDto.setTotalLimit(cards.getTotalLimit());
		
		return cardsDto;
	}
	
	public static Cards mapToCards(CardsDto cardsDto, Cards cards) {
		
		cards.setCardNumber(cardsDto.getCardNumber());
		cards.setCardType(cardsDto.getCardType());
		cards.setMobileNumber(cardsDto.getMobileNumber());
		cards.setAmountUsed(cardsDto.getAmountUsed());
		cards.setAvailableAmount(cardsDto.getAvailableAmount());
		cards.setTotalLimit(cardsDto.getTotalLimit());
		
		return cards;
	}

}
