package com.lambdaschool.piggybank.controllers;

import com.lambdaschool.piggybank.models.Coin;
import com.lambdaschool.piggybank.repositories.CoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
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

    @GetMapping(value = "/money/{amount}", produces = {"application/json"})
    public ResponseEntity<?> removeAmt(@PathVariable double amount){
        DecimalFormat hund = new DecimalFormat("#.00");
        List<Coin> coinList = new ArrayList<>();
        coinrepos.findAll().iterator().forEachRemaining(coinList::add);
        double total = 0;
        for (Coin c : coinList){
            total += c.getValue()*c.getQuantity();
        }

        int i = 0;
        do {
            if (amount > total) {
            System.out.println("Money not available");
            break;
            } else if (coinList.get(i).getQuantity() > 0 && ((amount*100) - (coinList.get(i).getValue()*100))/100 >= 0){
                amount = amount - coinList.get(i).getValue();
                total -= coinList.get(i).getValue();
                coinList.get(i).rmCoin();
            } else {
                i++;
            }
        }while (amount != 0.00 && i < 6);


        for (Coin c: coinList){
            if (c.getQuantity() > 1){
                System.out.println(c.getQuantity() + " " + c.getNameplural());
            } else {
                System.out.println(c.getQuantity() + " " + c.getName());
            }
        }
        System.out.println("The piggybank holds " + hund.format(total) + " amount= " + hund.format(amount));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
