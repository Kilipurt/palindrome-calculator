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

    public void saveNumber(String number) throws BadRequestException, InternalServerError {
        validateNumber(number);
        palindromeDAO.saveNumber(number);
    }

    public List<Number> selectNumbers() throws InternalServerError {
        return palindromeDAO.selectNumbers();
    }

    public List<String> selectPalindromes(String number) throws InternalServerError {
        return palindromeDAO.selectPalindromes(number);
    }

    public String selectMinPalindrome(String number) throws InternalServerError {
        List<String> palindromes = palindromeDAO.selectPalindromes(number);

        String minPalindrome = "";
        for(String palindrome : palindromes) {
            if (minPalindrome.compareTo(palindrome) > 0) {
                minPalindrome = palindrome;
            }
        }

        return minPalindrome;
    }

    public String selectMaxPalindrome(String number) throws InternalServerError {
        List<String> palindromes = palindromeDAO.selectPalindromes(number);

        String maxPalindrome = "";
        for(String palindrome : palindromes) {
            if (maxPalindrome.compareTo(palindrome) < 0) {
                maxPalindrome = palindrome;
            }
        }

        return maxPalindrome;
    }

    public void calculatePalindromes(String inputNumber, int amount) throws Exception {
        String startInputNumber = inputNumber;

        if (amount <= 0) {
            throw new BadRequestException("Wrong enter amount");
        }

        validateNumber(inputNumber);

        List<String> palindromes = new ArrayList<>();

        if (isPalindrome(inputNumber)) {
            palindromes.add(inputNumber);

            inputNumber = increaseByOne(inputNumber);
        }

        while (palindromes.size() != amount) {
            String palindrome = calculateNextPalindrome(new StringBuilder(inputNumber));
            palindromes.add(palindrome);
            inputNumber = increaseByOne(palindrome);
        }

        System.out.println(startInputNumber);

        palindromeDAO.savePalindromes(startInputNumber, palindromes);
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
