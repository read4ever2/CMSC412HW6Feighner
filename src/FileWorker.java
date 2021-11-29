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
  private File file;

  public void listFileLevel() {
    try {
      Files.list(new File(directoryName).toPath()).limit(20).forEach(System.out::println);
    } catch (IOException ioException) {
      System.out.println("Invalid File path");
      ioException.printStackTrace();
    }
  }

  public void listAllFileLevels() {
    try {
      File file = new File(directoryName);

      Files.walkFileTree(file.toPath(), Collections.emptySet(), Integer.MAX_VALUE, new SimpleFileVisitor<>() {
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
          System.out.println(file);
          return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
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
    Scanner scanner = new Scanner(System.in);
    System.out.println("You are in " + this.directoryName + ". Enter the file to convert to hex.");
    String fileName = scanner.next();
    this.path = Paths.get(directoryName + "/" + fileName);

    if (Files.notExists(path)) {
      throw new IllegalArgumentException("File not found! " + path);
    }
    System.out.println(path.toString());
    StringBuilder result = new StringBuilder();
    StringBuilder hex = new StringBuilder();
    StringBuilder input = new StringBuilder();

    int count = 0;
    int value;
    int offset = 0;

    try (InputStream inputStream = Files.newInputStream(path)) {
      while ((value = inputStream.read()) != -1) {
        offset++;
        hex.append(Integer.toHexString(value)).append(" ");

        if ((offset % 24) == 0) {
          hex.append("\n");
        }
      }
    } catch (IOException ioException) {
      ioException.printStackTrace();
    }
    return hex.toString();
  }

  public void XOREncrypt() {
    Scanner scanner = new Scanner(System.in);

    System.out.println("You are in " + this.directoryName + ". Please enter the file to encrypt");
    String fileName = scanner.next();

    PseudoCrypto pseudoCrypto = new PseudoCrypto();
    pseudoCrypto.EncryptXOR(path, directoryName, fileName, true);
  }

  public void XORDecrypt() {
    Scanner scanner = new Scanner(System.in);

    System.out.println("You are in " + this.directoryName + ". Please enter the file to decrypt");
    String fileName = scanner.next();

    PseudoCrypto pseudoCrypto = new PseudoCrypto();
    pseudoCrypto.EncryptXOR(path, directoryName, fileName, false);
  }
}