package com.azouakan.spring6restmvc.services;

import com.azouakan.spring6restmvc.model.BeerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface BeerService {

    void updateBeerById(UUID beerId, BeerDTO beer);

    Optional<BeerDTO> getBeerById(UUID id);
    List<BeerDTO> listBeers();

    BeerDTO saveNewBeer(BeerDTO beer);

    void deleteBeerById(UUID beerId);

    void updateBeerPatchById(UUID beerId, BeerDTO beer);
}
