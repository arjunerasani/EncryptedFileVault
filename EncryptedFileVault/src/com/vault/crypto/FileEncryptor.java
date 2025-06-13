package com.vault.crypto;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class FileEncryptor {
    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 256;

    public static void encryptFile(String filePath, String password, String encryptedFilePath) throws IOException {

        // first convert the file into bytes by using the java.nio library

        Path path = Paths.get(filePath);
        Path encryptedPath = Paths.get(encryptedFilePath);

        try {
            byte[] fileBytes = Files.readAllBytes(path);

            // now generate a random salt from the users password using the secure random library

            SecureRandom secureRandom = new SecureRandom();

            byte[] salt = new byte[16];
            secureRandom.nextBytes(salt);

            // next derive a secret key from the password + salt using the secret key factory and PBEKeySpec classes

            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            SecretKey secretKey = factory.generateSecret(spec);
            SecretKey keySpec = new SecretKeySpec(secretKey.getEncoded(), "AES");

            // now generate an initialization vector for secure AES encryption which is used to ensure randomness

            byte[] iv = new byte[16];
            secureRandom.nextBytes(iv);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv));

            byte[] encryptedBytes = cipher.doFinal(fileBytes);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            outputStream.write(salt);
            outputStream.write(iv);
            outputStream.write(encryptedBytes);

            byte[] finalOutput = outputStream.toByteArray();

            Files.write(encryptedPath, finalOutput);
        } catch (IOException e) {
            System.out.println("Issue with converting your file to bytes " + e.getMessage());
            e.printStackTrace();
            return;
        } catch (InvalidAlgorithmParameterException | InvalidKeySpecException | NoSuchAlgorithmException |
                 NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }

        System.out.println("File encrypted successfully and saved to: " + encryptedFilePath);
    }

    public static void decryptFile(String encryptedFilePath, String password, String decryptedFilePath) {
        Path path = Paths.get(encryptedFilePath);
        Path decryptedPath = Paths.get(decryptedFilePath);

        try {
            byte[] encryptedFileBytes = Files.readAllBytes(path);

            byte[] salt = Arrays.copyOfRange(encryptedFileBytes, 0, 16);
            byte[] iv = Arrays.copyOfRange(encryptedFileBytes, 16, 32);
            byte[] cipherText = Arrays.copyOfRange(encryptedFileBytes, 32, encryptedFileBytes.length);

            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            SecretKey secretKey = factory.generateSecret(spec);
            SecretKey keySpec = new SecretKeySpec(secretKey.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv));

            byte[] decryptedBytes = cipher.doFinal(cipherText);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            outputStream.write(decryptedBytes);

            byte[] finalOutput = outputStream.toByteArray();
            Files.write(decryptedPath, finalOutput);
        } catch (IOException e) {
            System.out.println("Problem reading encrypted file bytes " + e.getMessage());
            return;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException |
                 IllegalBlockSizeException | InvalidAlgorithmParameterException | BadPaddingException |
                 InvalidKeyException e) {
            throw new RuntimeException(e);
        }

        System.out.println("File decrypted successfully and saved to: " + decryptedFilePath);
    }
}
