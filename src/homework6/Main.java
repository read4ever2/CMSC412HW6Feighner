/*
 * Filename: Main.java
 * Author: Will Feighner
 * Date: 2021 11 28
 * Purpose: This program simulates a file system with basic directories
 * and pseudo encryption
 * */

package homework6;

import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    mainMenu();
  }

  public static boolean flag = true;
  static int menuChoice = 0;

  public static void mainMenu() {
    FileWorker fileSystem = new FileWorker();
    Scanner scanner = new Scanner(System.in);

    while (flag) {
      System.out.println("""
          *****************************************
                    
          0 - Exit
          1 – Select directory
          2 – List directory content (first level)
          3 – List directory content (all levels)
          4 – Delete file
          5 – Display file (hexadecimal view)
          6 – Encrypt file (XOR with password)
          7 – Decrypt file (XOR with password)
          Select Option:""");

      try {
        menuChoice = Integer.parseInt(scanner.next());

        switch (menuChoice) {
          case 0 -> {
            System.out.println("Goodbye");
            flag = false;
          }
          case 1 -> {
            System.out.println("1 entered. Please choose directory");
            fileSystem.pickDirectory("C:/" + scanner.next());
          }
          case 2 -> {
            System.out.println("2 entered. Listing current directory");
            fileSystem.listFileLevel();
          }
          case 3 -> {
            System.out.println("3 entered. Listing all directories");
            fileSystem.listAllFileLevels();
          }
          case 4 -> {
            System.out.println("4 entered. Delete File");
            fileSystem.deleteFile();
          }
          case 5 -> {
            System.out.println("5 entered. Display in Hex");
            System.out.println(fileSystem.convertToHex());
          }
          case 6 -> {
            System.out.println("6 entered. Encryption");
            fileSystem.XOREncrypt();
          }
          case 7->{
            System.out.println("7 entered. Decryption");
            fileSystem.XORDecrypt();
          }
          default -> System.out.println("Invalid selection. Please select 0-7");
        }
      } catch (NumberFormatException e) {
        System.out.println("Please enter a number between 0-7");
      }
    }
  }
}
