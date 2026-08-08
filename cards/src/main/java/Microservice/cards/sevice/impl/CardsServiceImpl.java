package Microservice.cards.sevice.impl;

import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import Microservice.cards.constants.CardConstants;
import Microservice.cards.dto.CardsDto;
import Microservice.cards.entity.Cards;
import Microservice.cards.exception.CardAlreadyPresentException;
import Microservice.cards.exception.ResourceNotFoundException;
import Microservice.cards.mapper.CardsMapper;
import Microservice.cards.repository.CardsRepository;
import Microservice.cards.sevice.ICardsService;
import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class CardsServiceImpl implements ICardsService{
	
	private CardsRepository cardsRepository;

	
	  @Override public void CreateCard(String mobileNumber) {
	  
			Optional<Cards> optionalCards = cardsRepository.findByMobileNumber(mobileNumber);
			if (optionalCards.isPresent()) {
				throw new CardAlreadyPresentException("Cards already present with given mobile number" + mobileNumber);
			}
			cardsRepository.save(createNewCard(mobileNumber));

		}

	private Cards createNewCard(String mobileNumber) {
		
		Cards newCard = new Cards();
		long randomCardNumber = 1000000000 + new Random().nextInt(900000000);
		newCard.setCardNumber(Long.toString(randomCardNumber));
		newCard.setMobileNumber(mobileNumber);
		newCard.setCardType(CardConstants.CREDIT_CARD);
		newCard.setTotalLimit(CardConstants.NEW_CARD_LIMIT);
		newCard.setAmountUsed(0);
		newCard.setAvailableAmount(CardConstants.NEW_CARD_LIMIT);
		
		return newCard;
	}

	@Override
	public CardsDto fetchCard(String mobileNumber) {
		
		Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
			    () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber));
		return CardsMapper.mapToCardsDto(cards, new CardsDto());
	}

	@Override
	public boolean updateCard(CardsDto cardsDto) {
		
		
		Cards cards = cardsRepository.findByCardNumber(cardsDto.getCardNumber()).orElseThrow(()->
		new ResourceNotFoundException("Card", "CardNumber", cardsDto.getCardNumber()));
		CardsMapper.mapToCards(cardsDto, cards);
		cardsRepository.save(cards);
		return true;
	}

	@Override
	public boolean deleteCard(String mobileNumber) {
		Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(()->
		new ResourceNotFoundException("Card", "mobileNumber", mobileNumber));
		cardsRepository.deleteById(cards.getCardId());
		return true;
	}

}
