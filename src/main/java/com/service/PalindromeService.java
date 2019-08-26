package com.service;

import com.dao.PalindromeDAO;
import com.exception.BadRequestException;
import com.exception.InternalServerError;
import com.model.Number;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PalindromeService {

    private PalindromeDAO palindromeDAO;

    @Autowired
    public PalindromeService(PalindromeDAO palindromeDAO) {
        this.palindromeDAO = palindromeDAO;
    }

    public Number findById(long id) throws BadRequestException, InternalServerError {
        if (id <= 0) {
            throw new BadRequestException("Wrong id");
        }

        return palindromeDAO.findById(id);
    }

    public List<Number> getNumbers() throws InternalServerError {
        return palindromeDAO.getNumbers();
    }

    public String selectMinPalindrome(long id) throws InternalServerError, BadRequestException {
        Number number = findById(id);

        if (number.getPalindromes() == null || number.getPalindromes().isEmpty()) {
            throw new BadRequestException("Please, wait for calculation");
        }

        return Collections.min(number.getPalindromes(), new StringComparator());
    }

    public String selectMaxPalindrome(long id) throws InternalServerError, BadRequestException {
        Number number = findById(id);

        if (number.getPalindromes() == null || number.getPalindromes().isEmpty()) {
            throw new BadRequestException("Please, wait for calculation");
        }

        return Collections.max(number.getPalindromes(), new StringComparator());
    }

    public void calculatePalindromes(Number inputNumber) throws Exception {
        inputNumber.setId(saveNumber(inputNumber));

        String startInputNumber = inputNumber.getNumberValue();

        if (inputNumber.getAmount() <= 0) {
            throw new BadRequestException("Wrong enter amount");
        }

        validateNumber(startInputNumber);

        List<String> palindromes = new ArrayList<>();

        if (isPalindrome(startInputNumber)) {
            palindromes.add(startInputNumber);

            startInputNumber = increaseByOne(startInputNumber);
        }

        while (palindromes.size() != inputNumber.getAmount()) {
            String palindrome = calculateNextPalindrome(new StringBuilder(startInputNumber));
            palindromes.add(palindrome);
            startInputNumber = increaseByOne(palindrome);
        }

        inputNumber.setPalindromes(palindromes);
        palindromeDAO.updateNumber(inputNumber);
    }

    private long saveNumber(Number number) throws BadRequestException, InternalServerError {
        validateNumber(number.getNumberValue());
        return palindromeDAO.saveNumber(number);
    }

    private String calculateNextPalindrome(StringBuilder number) {
        int i = 0;
        int j = number.length() - 1;

        int middle = number.length() / 2;

        for (; i <= middle && j >= middle; i++, j--) {
            if (number.charAt(i) > number.charAt(j)) {
                number.setCharAt(j, number.charAt(i));
            }

            if (number.charAt(i) < number.charAt(j)) {
                String partOfNumber = number.substring(0, j);
                partOfNumber = increaseByOne(partOfNumber);
                number.replace(0, j, partOfNumber);

                StringBuilder trueLastNumbers = new StringBuilder(number.substring(0, number.length() - j)).reverse();

                number.replace(j, number.length(), trueLastNumbers.toString());
            }
        }

        return number.toString();
    }

    private String increaseByOne(String number) {
        char[] numberSymbols = number.toCharArray();

        int nineAmount = 0;
        int lastIndex = number.length() - 1;

        for (int i = lastIndex; i >= 0; i--) {
            if (numberSymbols[i] != '9') {
                break;
            }

            nineAmount++;
        }

        if (nineAmount == number.length()) {
            StringBuilder result = new StringBuilder("1");

            for (int i = 0; i <= lastIndex; i++) {
                result.append("0");
            }

            return new String(result);
        } else {
            numberSymbols[lastIndex - nineAmount]++;

            for (int i = lastIndex; i > lastIndex - nineAmount; i--) {
                numberSymbols[i] = '0';
            }

            return new String(numberSymbols);
        }
    }

    private boolean isPalindrome(String number) {
        char[] chars = number.toCharArray();

        int middle = chars.length / 2;
        int i = 0;
        int j = chars.length - 1;

        while (i <= middle && j >= middle) {
            if (chars[i] != chars[j]) {
                return false;
            }

            i++;
            j--;
        }

        return true;
    }

    private void validateNumber(String number) throws BadRequestException {
        if (number == null || number.isEmpty()) {
            throw new BadRequestException("Number is empty");
        }


        char[] numberSymbols = number.toCharArray();

        for (char symbol : numberSymbols) {
            if (!Character.isDigit(symbol)) {
                throw new BadRequestException("Number is wrong");
            }
        }
    }
}
