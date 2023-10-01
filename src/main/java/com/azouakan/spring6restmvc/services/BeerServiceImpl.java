package com.azouakan.spring6restmvc.services;

import com.azouakan.spring6restmvc.model.Beer;
import com.azouakan.spring6restmvc.model.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

    @Override
    public Beer getBeerById(UUID id) {

        log.debug("Get beer by Id - in service. Id : {} ", id.toString());

        return Beer.builder()
                .id(id)
                .version(1)
                .beerName("Galaxy Cat")
                .upc("12356")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(122)
                .beerStyle(BeerStyle.PALE_ALE)
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
    }
}
