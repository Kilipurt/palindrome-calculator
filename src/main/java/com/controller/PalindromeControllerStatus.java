package com.controller;

import com.model.Number;
import com.service.PalindromeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PalindromeControllerStatus {

    private PalindromeService palindromeService;

    @Autowired
    public PalindromeControllerStatus(PalindromeService palindromeService) {
        this.palindromeService = palindromeService;
    }

    @PostMapping(path = "/calculate")
    public ResponseEntity<String> calculatePalindromes(@RequestParam(name = "number") String value,
                                                       @RequestParam String amount) throws Exception {
        Number number = new Number();
        number.setNumberValue(value);
        number.setAmount(Long.parseLong(amount));
        palindromeService.calculatePalindromes(number);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/select-min")
    public ResponseEntity<String> selectMin(@RequestParam String numberId) throws Exception {
        return new ResponseEntity<>(palindromeService.selectMinPalindrome(Long.parseLong(numberId)), HttpStatus.OK);
    }

    @GetMapping(path = "/select-max")
    public ResponseEntity<String> selectMax(@RequestParam String numberId) throws Exception {
        return new ResponseEntity<>(palindromeService.selectMaxPalindrome(Long.parseLong(numberId)), HttpStatus.OK);
    }
}
