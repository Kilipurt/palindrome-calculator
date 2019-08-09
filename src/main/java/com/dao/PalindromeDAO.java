package com.dao;

import com.exception.InternalServerError;
import com.model.Number;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class PalindromeDAO {

    private String path = "D:\\numbers.txt";

    public String saveNumber(String number) throws InternalServerError {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) {
            File file = new File(path);

            if (file.length() != 0) {
                bw.newLine();
            }

            bw.append(number);
        } catch (IOException e) {
            throw new InternalServerError("Saving failed");
        }

        return number;
    }

    public  void savePalindromes(String number, List<String> palindromes) throws InternalServerError {
        List<String> numberWithPalindromes = addPalindromesToList(number, palindromes);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, false))) {
            int count = 1;

            for (String s : numberWithPalindromes) {
                bw.append(s);

                if (count != numberWithPalindromes.size()) {
                    bw.newLine();
                }

                count++;
            }

        } catch (IOException e) {
            throw new InternalServerError("Saving is failed");
        }
    }

    public List<Number> selectNumbers() throws InternalServerError {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            List<Number> numbers = new ArrayList<>();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String[] lineParts = line.split(":");

                boolean isCalculated = false;

                if (lineParts.length > 1) {
                    isCalculated = true;
                }

                Number number = new Number(lineParts[0], isCalculated);
                numbers.add(number);
            }

            return numbers;

        } catch (IOException e) {
            throw new InternalServerError("Selecting is failed");
        }
    }

    public List<String> selectPalindromes(String number) throws InternalServerError {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            List<String> palindromes = new ArrayList<>();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                if (line.split(":")[0].equals(number)) {
                    String palindromesString = line.split(":")[1];
                    palindromes = Arrays.asList(palindromesString.split(","));
                    break;
                }
            }

            return palindromes;

        } catch (IOException e) {
            throw new InternalServerError("Selecting is failed");
        }
    }

    private String palindromesToString(List<String> palindromes) {
        StringBuilder palindromesString = new StringBuilder();

        int amount = 0;

        for (String palindrome : palindromes) {
            amount++;

            if (palindromesString.toString().isEmpty()) {
                palindromesString.append(":");
            }

            palindromesString.append(palindrome);

            if (amount != palindromes.size()) {
                palindromesString.append(",");
            }
        }

        return palindromesString.toString();
    }

    private List<String> addPalindromesToList(String number, List<String> palindromes) throws InternalServerError {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            List<String> result = new ArrayList<>();
            String line;

            while ((line = bufferedReader.readLine()) != null) {

                if (line.equals(number)) {
                    line += palindromesToString(palindromes);
                }

                result.add(line);
            }

            return result;

        } catch (IOException e) {
            throw new InternalServerError("Reading is failed");
        }
    }
}