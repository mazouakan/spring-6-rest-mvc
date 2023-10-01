package com.azouakan.spring6restmvc.services;

import com.azouakan.spring6restmvc.model.Beer;

import java.util.UUID;

public interface BeerService {

    Beer getBeerById(UUID id);
}
