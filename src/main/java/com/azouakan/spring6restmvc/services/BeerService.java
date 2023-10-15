package com.azouakan.spring6restmvc.services;

import com.azouakan.spring6restmvc.model.Beer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface BeerService {

    void updateBeerById(UUID beerId, Beer beer);

    Optional<Beer> getBeerById(UUID id);
    List<Beer> listBeers();

    Beer saveNewBeer(Beer beer);

    void deleteBeerById(UUID beerId);

    void updateBeerPatchById(UUID beerId, Beer beer);
}
