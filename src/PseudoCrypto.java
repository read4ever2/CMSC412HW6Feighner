/*
 * Filename: PseudoCrypto.java
 * Author: Will Feighner
 * Date: 2021 11 28
 * Purpose: This program simulates a file system with basic directories
 * and pseudo encryption
 * */

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class PseudoCrypto {
  public static void XORFile(File inputFile, File outputFile, byte[] passwordBytes) throws IOException {
    InputStream inputStream = null;
    OutputStream outputStream = null;

    try {
      inputStream = new FileInputStream(inputFile);
      outputStream = new BufferedOutputStream(new FileOutputStream(outputFile));

      int workingByte = -1;
      long counter = 0;

      while ((workingByte = inputStream.read()) != -1) {
        byte outputByte = (byte) (workingByte ^ passwordBytes[(int) (counter % passwordBytes.length)]);
        outputStream.write(outputByte);
        counter++;
      }
      outputStream.flush();

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      assert inputStream != null;
      inputStream.close();
      assert outputStream != null;
      outputStream.close();
    }
  }



  public void EncryptXOR(Path filepath, String directory, String fileName, boolean encrypt) {

    Scanner scanner = new Scanner(System.in);

    filepath = Paths.get(directory + "/" + fileName);

    Path resultPath = Paths.get(directory + "/enc_" + fileName);

    System.out.print("Enter encryption password: ");
    String password = scanner.next();

    byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);

    try {
      if (Files.exists(resultPath)) {
        System.out.println("File previously decrypted. Deleting and re-decrypting");
        Files.delete(resultPath);
      } else if (Files.notExists(filepath)) {
        throw new IllegalArgumentException("File not found! " + filepath);
      }

      String inputDirectory = directory + "/" + fileName;
      String outputDirectory;

      if (encrypt) {
        outputDirectory = directory + "/enc_" + fileName;
      } else {
        outputDirectory = directory + "/dec_" + fileName;
      }
      File outputFile = new File(outputDirectory);
      File inputFile = new File(inputDirectory);
      XORFile(inputFile, outputFile, passwordBytes);


    } catch (IOException ioException) {
      System.out.println("Invalid File");
    } catch (IllegalArgumentException illegalArgumentException) {
      System.out.println("File not found! " + filepath);
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }
}
