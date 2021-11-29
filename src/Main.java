/*
 * Filename: Main.java
 * Author: Will Feighner
 * Date: 2021 11 28
 * Purpose: This program simulates a file system with basic directories
 * and pseudo encryption
 * */

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
      System.out.println("*****************************************\n" +
                         "\n" +
                         "0 - Exit\n" +
                         "1 – Select directory\n" +
                         "2 – List directory content (first level)\n" +
                         "3 – List directory content (all levels)\n" +
                         "4 – Delete file\n" +
                         "5 – Display file (hexadecimal view)\n" +
                         "6 – Encrypt file (XOR with password)\n" +
                         "7 – Decrypt file (XOR with password)\n" +
                         "Select Option:");

      try {
        menuChoice = Integer.parseInt(scanner.next());

        switch (menuChoice) {
          case 0:
            System.out.println("Goodbye");
            flag = false;
            break;
          case 1:
            System.out.println("1 entered. Please choose directory");
            fileSystem.pickDirectory(scanner.next());
            break;
          case 2:
            System.out.println("2 entered. Listing current directory");
            fileSystem.listFileLevel();
            break;
          case 3:
            System.out.println("3 entered. Listing all directories");
            fileSystem.listAllFileLevels();
            break;
          case 4:
            System.out.println("4 entered. Delete File");
            fileSystem.deleteFile();
            break;
          case 5:
            System.out.println("5 entered. Display in Hex");
            System.out.println(fileSystem.convertToHex());
            break;
          case 6:
            System.out.println("6 entered. Encryption");
            fileSystem.XOREncrypt();
            break;
          case 7:
            System.out.println("7 entered. Decryption");
            fileSystem.XORDecrypt();
            break;
          default:
            System.out.println("Invalid selection. Please select 0-7");
            break;
        }
      } catch (NumberFormatException e) {
        System.out.println("Please enter a number between 0-7");
      }
    }
  }
}
