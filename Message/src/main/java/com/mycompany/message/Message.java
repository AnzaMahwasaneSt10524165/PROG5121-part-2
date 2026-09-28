/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.message;

/**
 *
 * @author Student
 */
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

    public class Message {

        static String registeredUsername = "";
        static String registeredPassword = "";
        static String registeredPhone = "";
        static String firstName = "";
        static String lastName = "";

        public static void main(String[] args) {
            Scanner input = new Scanner(System.in);

            System.out.println("=== REGISTRATION ===");
            System.out.print("Enter your First Name:");
            firstName = input.nextLine();
            System.out.print("Enter your Last Name:");
            lastName = input.nextLine();
            System.out.print("Enter username:");
            String username = input.nextLine();

            if (checkUserName(username)) {
                System.out.println("Username successfully captured");
            } else {
                System.out.println("Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length");
                input.close();
                return;
            }

            // Password
            System.out.print("Enter password:");
            String password = input.nextLine();

            if (checkPasswordComplexity(password)) {
                System.out.println("Password successfully captured");
            } else {
                System.out.println("Password is not correctly formatted please ensure that the password contains at least eight characters, capital letter, number, and a special character");
                input.close();
                return;
            }

            System.out.print("Enter cell phone number:");
            String phone = input.nextLine();

            if (checkCellPhoneNumber(phone)) {
                System.out.println("Cell phone number successfully captured");
            } else {
                System.out.println("Telephone number phone number incorrectly formatted or does not contain an international code, please correct the number and try again");
                input.close();
                return;
            }

            // Store details
            registeredUsername = username;
            registeredPassword = password;
            registeredPhone = phone;

            System.out.println("Registration successful");

            // LOGIN
            System.out.println("\n=== LOGIN ===");

            System.out.print("Enter username:");
            String loginUser = input.nextLine();

            System.out.print("Enter password:");
            String loginPass = input.nextLine();

            boolean loginSuccess = loginUser(loginUser, loginPass);

            System.out.println(returnLoginStatus(loginSuccess));

            if (loginSuccess) {
                Message myMessageApp = new Message();
                myMessageApp.showMenu(input); // Pass scanner here
            }

            input.close();
        }

        // Username validation
        public static boolean checkUserName(String username) {
            return username != null && username.contains("_") && username.length() <= 5 && username.length() > 0;
        }

        // Password validation
        public static boolean checkPasswordComplexity(String password) {
            if (password == null) return false;

            boolean length = password.length() >= 8;
            boolean capital = false;
            boolean number = false;
            boolean special = false;

            for (int i = 0; i < password.length(); i++) {
                char c = password.charAt(i);

                if (Character.isUpperCase(c)) capital = true;
                else if (Character.isDigit(c)) number = true;
                else if ("!@#$%^&*".indexOf(c) >= 0) special = true;
            }

            return length && capital && number && special;
        }

        // Phonenumber validation
        public static boolean checkCellPhoneNumber(String number) {
            if (number == null) return false;
            return number.matches("^\\+27\\d{9}$");
        }

        public static boolean loginUser(String username, String password) {
            return username != null &&
                    password != null &&
                    username.equals(registeredUsername) &&
                    password.equals(registeredPassword);
        }

        // display login message
        public static String returnLoginStatus(boolean success) {
            if (success) {
                return "Welcome " + firstName + " " + lastName + " it is great to see you";
            } else {
                return "Username or password is incorrect please try again";
            }
        }

        // Part 2
        static class Message {

            static int totalMessagesSent = 0;
            static String allMessagesData = "";

            public void showMenu(Scanner input) {
                boolean running = true;

                System.out.println("\nWelcome to QuickChat.");

                while (running) {
                    System.out.println("\nPlease choose an option:");
                    System.out.println("1) Send Messages");
                    System.out.println("2) Show recently sent messages");
                    System.out.println("3) Quit");
                    System.out.print("Choice: ");

                    String choice = input.nextLine();

                    if (choice.equals("1")) {
                        System.out.print("How many messages do you wish to enter? ");
                        int numMessages = input.nextInt();
                        input.nextLine();

                        for (int i = 0; i < numMessages; i++) {
                            System.out.println("\n--- Message " + (i + 1) + " ---");

                            String recipient = "";
                            while (true) {
                                System.out.print("Enter recipient cell number (starting with +27, followed by 9 digits): ");
                                recipient = input.nextLine();
                                String validRecipient = checkRecipientCell(recipient);
                                if (validRecipient.equals("Valid")) {
                                    break;
                                } else {
                                    System.out.println("Invalid number. Must start with +27 followed by exactly 9 digits.");
                                }
                            }

                            //Message limit code here
                            String messageText = "";
                            while (true) {
                                System.out.print("Enter your message (max 250 characters): ");
                                messageText = input.nextLine();
                                if (messageText.length() <= 250 && messageText.length() > 0) {
                                    System.out.println("Message sent");
                                    break;
                                } else {
                                    System.out.println("Please enter a message of less than 250 characters.");
                                }
                            }

                            String messageID = generateRandomID();
                            while (!checkMessageID(messageID)) {
                                messageID = generateRandomID();
                            }

                            String messageHash = createMessageHash(messageID, totalMessagesSent, messageText);
                            String actionResult = SentMessage(input);
                            System.out.println(actionResult);

                            if (actionResult.equals("Message successfully stored")) {
                                storeMessage(messageID, messageHash, recipient, messageText);
                            }

                            if (!actionResult.equals("Press 0 to delete the message")) {
                                totalMessagesSent++;
                                System.out.println("\n--- Sent Message Details ---");
                                System.out.println("Message ID: " + messageID);
                                System.out.println("Message Hash: " + messageHash);
                                System.out.println("Recipient: " + recipient);
                                System.out.println("Message: " + messageText);
                                System.out.println("----------------------------");

                                allMessagesData += "Message ID: " + messageID + "\n"
                                        + "Message Hash: " + messageHash + "\n"
                                        + "Recipient: " + recipient + "\n"
                                        + "Message: " + messageText + "\n\n";
                            }
                        }

                        //Generate message
                        System.out.println("\n--- All Sent Messages Summary ---");
                        System.out.println(printMessages());
                        System.out.println("Total messages sent so far: " + returnTotalMessagess());

                    } else if (choice.equals("2")) {
                        System.out.println("Coming Soon.");
                    } else if (choice.equals("3")) {
                        running = false;
                        System.out.println("Goodbye!");
                    } else {
                        System.out.println("Invalid option. Please try again.");
                    }
                }
            }

            public String generateRandomID() {
                Random rand = new Random();
                StringBuilder id = new StringBuilder();
                for (int i = 0; i < 10; i++) {
                    id.append(rand.nextInt(10));
                }
                return id.toString();
            }

            public boolean checkMessageID(String messageID) {
                return messageID != null && messageID.length() <= 10;
            }

            // Fixed the backslash escaping issue
            public String checkRecipientCell(String cellNumber) {
                if (cellNumber != null && cellNumber.matches("^\\+27\\d{9}$")) {
                    return "Valid";
                }
                return "Invalid";
            }

            public String createMessageHash(String messageID, int messageNumber, String message) {
                String firstTwoID = messageID.substring(0, 2);

                String[] words = message.split(" ");
                String firstWord = words[0];
                String lastWord = words[words.length - 1];

                String hash = firstTwoID + ":" + messageNumber + ":" + firstWord + lastWord;
                return hash.toUpperCase();
            }

            public String SentMessage(Scanner input) {
                System.out.println("\nWhat would you like to do with this message?");
                System.out.println("1) Send Message");
                System.out.println("2) Disregard Message");
                System.out.println("3) Store Message to send later");
                System.out.print("Choice: ");

                String choice = input.nextLine();

                if (choice.equals("1")) {
                    return "Message successfully sent";
                } else if (choice.equals("2")) {
                    return "Press 0 to delete the message";
                } else if (choice.equals("3")) {
                    return "Message successfully stored";
                } else {
                    return "Invalid choice, defaulting to disregard. Press 0 to delete the message";
                }
            }

            public String printMessages() {
                if (allMessagesData.equals("")) {
                    return "No messages sent yet.";
                }
                return allMessagesData;
            }

            public int returnTotalMessagess() {
                return totalMessagesSent;
            }

            // JSON file
            public void storeMessage(String id, String hash, String recipient, String message) {
                try {
                    FileWriter writer = new FileWriter("stored_messages.json", true);

                    String jsonFormat = "{\n" +
                            "\"MessageID\": \"" + id + "\",\n" +
                            "\"MessageHash\": \"" + hash + "\",\n" +
                            "\"Recipient\": \"" + recipient + "\",\n" +
                            "\"Message\": \"" + message + "\"\n" +
                            "}\n";

                    writer.write(jsonFormat);
                    writer.close();

                } catch (IOException e) {
                    System.out.println("An error occurred while saving to JSON.");
                    e.printStackTrace();
                }
            }
        }
    }
