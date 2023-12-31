package com.azouakan.spring6restmvc.controller;

import com.azouakan.spring6restmvc.entities.Beer;
import com.azouakan.spring6restmvc.mappers.BeerMapper;
import com.azouakan.spring6restmvc.model.BeerDTO;
import com.azouakan.spring6restmvc.repositories.BeerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class BeerControllerIT {
    @Autowired
    BeerController beerController;
    
    @Autowired
    BeerRepository beerRepository;

    @Autowired
    BeerMapper beerMapper;

    @Test
    void testPatchByIdNotFound() {
        assertThrows(NotFoundException.class, () -> beerController.updateBeerPatchById(UUID.randomUUID(), BeerDTO.builder().build()));
    }

    @Rollback
    @Transactional
    @Test
    void testPatchByIdFound() {
        Beer beer = beerRepository.findAll().get(0);
        BeerDTO beerDTO = beerMapper.beerToBeerDto(beer);
        final String beerName = "UPDATED";
        beerDTO.setBeerName(beerName);
        ResponseEntity<BeerDTO> responseEntity = beerController.updateBeerPatchById(beer.getId(), beerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        Beer updatedBeer = beerRepository.findById(beer.getId()).get();
        assertThat(updatedBeer.getBeerName()).isEqualTo(beerName);


    }

    @Test
    void testDeleteByIdNotFound() {

        assertThrows(NotFoundException.class, ()-> beerController.deleteById(UUID.randomUUID()));
    }

    @Rollback
    @Transactional
    @Test
    void testDeleteByIdFound() {

        Beer beer = beerRepository.findAll().get(0);
        ResponseEntity<BeerDTO> responseEntity = beerController.deleteById(beer.getId());
        assertThat(beerRepository.findById(beer.getId())).isEmpty();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
    }

    @Test
    void testUpdateNotFound() {
        assertThrows(NotFoundException.class, () -> beerController.updateById(UUID.randomUUID(), BeerDTO.builder().build()));
    }

    @Rollback
    @Transactional
    @Test
    void updateExistingBeerTest() {
        Beer beer = beerRepository.findAll().get(0);
        BeerDTO beerDTO = beerMapper.beerToBeerDto(beer);
        beerDTO.setVersion(null);
        beerDTO.setId(null);
        final String beerName = "UPDATED";
        beerDTO.setBeerName(beerName);
        ResponseEntity<BeerDTO> responseEntity = beerController.updateById(beer.getId(), beerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        Beer updatedBeer = beerRepository.findById(beer.getId()).get();
        assertThat(updatedBeer.getBeerName()).isEqualTo(beerName);


    }

    @Rollback
    @Transactional
    @Test
    void saveNewBeerTest() {
        BeerDTO beerDTO = BeerDTO.builder()
                .beerName("New Beer")
                .build();
        ResponseEntity<BeerDTO> responseEntity = beerController.handlePost(beerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();
        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[4]);
        Beer beer = beerRepository.findById(savedUUID).get();
         assertThat(beer).isNotNull();

    }

    @Test
    void testBeerIdNotFound() {
        assertThrows(NotFoundException.class, ()-> beerController.getBeerById(UUID.randomUUID()));
    }

    @Test
    void testGetById() {
        Beer beer = beerRepository.findAll().get(0);
        BeerDTO dto = beerController.getBeerById(beer.getId());
        assertThat(dto).isNotNull();
    }

    @Test
    void teslListBeers() {
        List<BeerDTO> dtos = beerController.listBeers();
        assertThat(dtos.size()).isEqualTo( 3);
    }

    @Rollback
    @Transactional
    @Test
    void testEmptyList() {
        beerRepository.deleteAll();
        List<BeerDTO> dtos = beerController.listBeers();
        assertThat(dtos.size()).isEqualTo(0);
    }
}
