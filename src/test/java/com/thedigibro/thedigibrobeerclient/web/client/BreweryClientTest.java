package com.thedigibro.thedigibrobeerclient.web.client;

import com.thedigibro.thedigibrobeerclient.web.model.BeerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.net.URI;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BreweryClientTest {

    @Autowired
    BreweryClient client;
    @Test
    void getBeerById() {
        BeerDto dto=client.getBeerById(UUID.randomUUID());
        assertNotNull(dto);
    }

    @Test
    void testSavedNewBeer(){
        BeerDto beerDto=BeerDto.builder().beerName("New Beer").build();
        URI uri=client.saveNewBeer(beerDto);
        assertNotNull(uri);
        System.out.println(uri.toString());
    }
}