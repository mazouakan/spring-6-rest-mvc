package com.azouakan.spring6restmvc.services;

import com.azouakan.spring6restmvc.model.BeerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface BeerService {

    Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beer);

    Optional<BeerDTO> getBeerById(UUID id);
    List<BeerDTO> listBeers();

    BeerDTO saveNewBeer(BeerDTO beer);

    Boolean deleteBeerById(UUID beerId);

    Optional<BeerDTO> updateBeerPatchById(UUID beerId, BeerDTO beer);
}
