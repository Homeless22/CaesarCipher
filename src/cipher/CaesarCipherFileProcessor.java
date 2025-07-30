package cipher;

import exception.InvalidFilePathException;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.MalformedInputException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CaesarCipherFileProcessor {
    private final CaesarCipher caesarCipher;

    public CaesarCipherFileProcessor(CaesarCipher caesarCipher) {
          this.caesarCipher = caesarCipher;
    }

    /***
     * Применение сдвига к тексту из исходного файла с сохранением в зашифрованный
     * @param sourceFilePath шифруемый файл
     * @param destinationFilePath зашифрованный файл
     * @param shift значение сдвига
     */
    private void applyShift(String sourceFilePath, String destinationFilePath, int shift) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(sourceFilePath)/*, Charset.forName("Cp1251")*/));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(destinationFilePath)/*, Charset.forName("Cp1251")*/))) {
            String line = reader.readLine();
            while (line != null) {
                String encryptLine = caesarCipher.encrypt(line, shift);
                writer.write(encryptLine);
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /***
     * Шифрование файла
     * @param sourceFilePath шифруемый файл
     * @param destinationFilePath зашифрованный файл
     * @param shift значение сдвига
     */
    public void encryptFile(String sourceFilePath, String destinationFilePath, int shift) {
        applyShift(sourceFilePath, destinationFilePath, shift);
    }

    /***
     * Расшифровка файла
     * @param sourceFilePath зашифрованный файл
     * @param destinationFilePath файл после расшифровки
     * @param shift значение сдвига
     */
    public void decryptFile(String sourceFilePath, String destinationFilePath, int shift) {
        applyShift(sourceFilePath, destinationFilePath, -1 * shift);
    }

    /***
     * Расшифровка файла методом взлома (перебора сдвига)
     * @param sourceFilePath зашифрованный файл
     * @return значение сдвига
     */
    public int bruteForce(String sourceFilePath) {
        String text;
        try {
            Path path = Paths.get(sourceFilePath);
            text = Files.readString(path);
        } catch (MalformedInputException e) {
            throw new RuntimeException("Содержимое файла не соответствует кодировке " + Charset.defaultCharset().name());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return caesarCipher.bruteForce(text);
    }

    /***
     * Проверка файлов
     * @param sourceFilePath исходный файл
     * @param destinationFilePath итоговый файл
     */
    public void validateFiles(String sourceFilePath, String destinationFilePath) {
        if (sourceFilePath == null || sourceFilePath.isEmpty()) {
            throw new InvalidFilePathException("Путь к исходному файлу не заполнен");
        }

        if (destinationFilePath == null || destinationFilePath.isEmpty()) {
            throw new InvalidFilePathException("Путь к итоговому файлу не заполнен");
        }

        if (!Files.exists(Path.of(sourceFilePath))) {
            throw new InvalidFilePathException("Исходный файл " + sourceFilePath + " не найден");
        }

        if (sourceFilePath.equals(destinationFilePath)) {
            throw new InvalidFilePathException("Исходный файл совпадает с итоговым");
        }
    }
}