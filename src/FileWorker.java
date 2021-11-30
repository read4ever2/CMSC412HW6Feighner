/*
 * Filename: FileWorker.java
 * Author: Will Feighner
 * Date: 2021 11 28
 * Purpose: This program simulates a file system with basic directories
 * and pseudo encryption
 * */

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.Scanner;

public class FileWorker {
  private String directoryName;
  private Path path;

  public void listFileLevel() {
    if (directoryName == null) {
      System.out.println("Please Select a directory first");
      return;
    }
    try {
      Files.list(new File(directoryName).toPath()).limit(20).forEach(System.out::println);
    } catch (IOException ioException) {
      System.out.println("Invalid File path");
      ioException.printStackTrace();
    }
  }

  public void listAllFileLevels() {
    if (directoryName == null) {
      System.out.println("Please Select a directory first");
      return;
    }
    try {
      File file = new File(directoryName);

      Files.walkFileTree(file.toPath(), Collections.emptySet(), Integer.MAX_VALUE, new SimpleFileVisitor<>() {
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
          System.out.println(file);
          return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
          System.out.println(directoryName);
          return FileVisitResult.CONTINUE;
        }
      });
    } catch (IOException ioException) {
      System.out.println("Invalid File path");
      ioException.printStackTrace();
    }
  }

  public void deleteFile() {
    if (directoryName == null) {
      System.out.println("Please Select a directory first");
      return;
    }
    Scanner scanner = new Scanner(System.in);
    System.out.println("You are in " + this.directoryName + ". Please enter the file that you want to delete.");
    String fileName = scanner.next();

    Path deletePath = Paths.get(path + "/" + fileName);
    try {
      if (Files.exists(deletePath)) {
        System.out.println("Deleting Files");
        Files.delete(deletePath);
        listFileLevel();
      } else {
        System.out.println("File does not exist");
      }
    } catch (IOException ioException) {
      ioException.printStackTrace();
    }
  }

  public void pickDirectory(String directory) {
    try {
      this.path = Paths.get(directory);

      if (!Files.notExists(path)) {
        this.directoryName = directory;
        System.out.println("Current Directory: " + directory);
      } else {
        System.out.println("Directory does not exist. Select again.");
        Main.flag = true;
      }
    } catch (Exception exception) {
      System.out.println("Directory does not exist");
      exception.printStackTrace();
    }
  }

  public String convertToHex() {
    if (directoryName == null) {
      return "Please Select a directory first";
    }
    Scanner scanner = new Scanner(System.in);
    System.out.println("You are in " + this.directoryName + ". Enter the file to convert to hex.");
    String fileName = scanner.next();
    this.path = Paths.get(directoryName + "/" + fileName);

    StringBuilder hex = new StringBuilder();

    try {
      InputStream inputStream = Files.newInputStream(path);
      System.out.println(path.toString());

      int value;
      int offset = 0;
      try {
        if (!Files.notExists(path)) {
          while ((value = inputStream.read()) != -1) {
            offset++;
            hex.append(Integer.toHexString(value)).append(" ");

            if ((offset % 24) == 0) {
              hex.append("\n");
            }
          }
        } else {
          System.out.println("File does not exist");
        }
      } catch (IllegalArgumentException | NoSuchFileException illegalArgumentException) {
        System.out.println("File does not exist");
      } finally {
        inputStream.close();
      }
    } catch (IOException ioException) {
      System.out.println("File does not exist");
    }
    return hex.toString();
  }

  public void XOREncrypt() {
    if (directoryName == null) {
      System.out.println("Please Select a directory first");
      return;
    }
    Scanner scanner = new Scanner(System.in);

    System.out.println("You are in " + this.directoryName + ". Please enter the file to encrypt");
    String fileName = scanner.next();

    PseudoCrypto pseudoCrypto = new PseudoCrypto();
    pseudoCrypto.EncryptXOR(directoryName, fileName, true);
  }

  public void XORDecrypt() {
    if (directoryName == null) {
      System.out.println("Please Select a directory first");
      return;
    }
    Scanner scanner = new Scanner(System.in);

    System.out.println("You are in " + this.directoryName + ". Please enter the file to decrypt");
    String fileName = scanner.next();

    PseudoCrypto pseudoCrypto = new PseudoCrypto();
    pseudoCrypto.EncryptXOR(directoryName, fileName, false);
  }
}