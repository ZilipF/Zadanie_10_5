package Zadanie_10_5;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class FinancialInstitutionIdentifier {
    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);
        System.out.print("Enter the first three digits of the account number: ");
        String bankCodeInput = inputScanner.nextLine();

        if (!bankCodeInput.matches("\\d{3}")) {
            System.out.println("Error: Please enter exactly three digits.");
            return;
        }

        String dataUrl = "https://ewib.nbp.pl/plewibnra?dokNazwa=plewibnra.txt";
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(new URL(dataUrl).openStream()))) {
            String lineContent;
            boolean found = false;

            System.out.println("Processing file...");
            while ((lineContent = fileReader.readLine()) != null) {
                String[] lineParts = lineContent.trim().split("\\s+");

                if (lineParts.length >= 2 && lineParts[0].equals(bankCodeInput)) {
                    System.out.println("Bank code: " + lineParts[0]);
                    System.out.println("Bank name: " + String.join(" ", java.util.Arrays.copyOfRange(lineParts, 1, lineParts.length)));
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("No bank found for the provided code.");
            }
        } catch (IOException e) {
            System.err.println("Error fetching the file: " + e.getMessage());
        }
    }
}