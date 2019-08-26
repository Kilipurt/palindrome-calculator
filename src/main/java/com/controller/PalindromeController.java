package com.controller;

import com.service.PalindromeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PalindromeController {

    private PalindromeService palindromeService;

    @Autowired
    public PalindromeController(PalindromeService palindromeService) {
        this.palindromeService = palindromeService;
    }

    @GetMapping(path = "/")
    public String home() {
        return "index";
    }

    @GetMapping(path = "/history")
    public String history(Model model) throws Exception {
        model.addAttribute("numbers", palindromeService.getNumbers());
        return "history";
    }

    @GetMapping(path = "/select-result")
    public String selectResult(Model model, @RequestParam String numberId) throws Exception {
        model.addAttribute("palindromes", palindromeService.findById(Long.parseLong(numberId)).getPalindromes());
        return "result";
    }
}
