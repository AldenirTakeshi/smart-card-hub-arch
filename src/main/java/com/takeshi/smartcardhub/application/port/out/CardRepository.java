package com.takeshi.smartcardhub.application.port.out;

import com.takeshi.smartcardhub.domain.Card;

import java.util.Optional;
import java.util.UUID;

public interface CardRepository {

    void salvar(Card card);
    Optional<Card> buscarPorId(UUID id);
}
