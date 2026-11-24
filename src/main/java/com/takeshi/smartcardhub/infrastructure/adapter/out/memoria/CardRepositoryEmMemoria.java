package com.takeshi.smartcardhub.infrastructure.adapter.out.memoria;

import com.takeshi.smartcardhub.application.port.out.CardRepository;
import com.takeshi.smartcardhub.domain.Card;

import java.util.*;

public class CardRepositoryEmMemoria implements CardRepository {

    private final Map<UUID, Card> cards = new HashMap<>();

    @Override
    public void salvar(Card card) {
        cards.put(card.getId(), card);
    }

    @Override
    public Optional<Card> buscarPorId(UUID id) {
        return Optional.ofNullable(cards.get(id));
    }
}
