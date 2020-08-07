package com.lambdaschool.piggybank.controllers;

import com.lambdaschool.piggybank.models.Coin;
import com.lambdaschool.piggybank.repositories.CoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CoinController {
    @Autowired
    CoinRepository coinrepos;

    @GetMapping(value = "/total", produces = {"application/json"})
    public ResponseEntity<?> listCoinTotal(){
        List<Coin> coinList = new ArrayList<>();
        coinrepos.findAll().iterator().forEachRemaining(coinList::add);
        double total = 0;
        for (Coin c: coinList){
            if (c.getQuantity() > 1){
                System.out.println(c.getQuantity() + " " + c.getNameplural());
                total += c.getValue() * c.getQuantity();
            } else {
                System.out.println(c.getQuantity() + " " + c.getName());
                total += c.getValue() * c.getQuantity();
            }
        }
        System.out.println("The piggybank holds " + total);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    
}
