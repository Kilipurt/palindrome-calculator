package com.controller;

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

    @PostMapping(path = "/save-number")
    public ResponseEntity<String> saveNumber(@RequestParam String number) throws Exception {
        palindromeService.saveNumber(number);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/calculate")
    public ResponseEntity<String> calculatePalindromes(@RequestParam String number, @RequestParam String amount)
            throws Exception {
        palindromeService.calculatePalindromes(number, Integer.parseInt(amount));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/select-min")
    public ResponseEntity<String> selectMin(@RequestParam String number) throws Exception {
        System.out.println(palindromeService.selectMinPalindrome(number));
        return new ResponseEntity<>(palindromeService.selectMinPalindrome(number), HttpStatus.OK);
    }

    @GetMapping(path = "/select-max")
    public ResponseEntity<String> selectMax(@RequestParam String number) throws Exception {
        return new ResponseEntity<>(palindromeService.selectMaxPalindrome(number), HttpStatus.OK);
    }
}
