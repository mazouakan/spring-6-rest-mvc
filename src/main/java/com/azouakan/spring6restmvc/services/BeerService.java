package com.azouakan.spring6restmvc.services;

import com.azouakan.spring6restmvc.model.Beer;

import java.util.List;
import java.util.UUID;

public interface BeerService {

    void updateBeerById(UUID beerId, Beer beer);

    Beer getBeerById(UUID id);
    List<Beer> listBeers();

    Beer saveNewBeer(Beer beer);

    void deleteBeerById(UUID beerId);
}
