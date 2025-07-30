package cipher;

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
     * @param sourceFile шифруемый файл
     * @param destFile зашифрованный файл
     * @param shift значение сдвига
     */
    private void applyShift(String sourceFile, String destFile, int shift) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(sourceFile)/*, Charset.forName("Cp1251")*/));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(destFile)/*, Charset.forName("Cp1251")*/))) {
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
     * @param sourceFile шифруемый файл
     * @param destFile зашифрованный файл
     * @param shift значение сдвига
     */
    public void encryptFile(String sourceFile, String destFile, int shift) {
        applyShift(sourceFile, destFile, shift);
    }

    /***
     * Расшифровка файла
     * @param sourceFile зашифрованный файл
     * @param destFile файл после расшифровки
     * @param shift значение сдвига
     */
    public void decryptFile(String sourceFile, String destFile, int shift) {
        applyShift(sourceFile, destFile, -1 * shift);
    }

    /***
     * Расшифровка файла методом взлома (перебора сдвига)
     * @param sourceFile зашифрованный файл
     * @return значение сдвига
     */
    public int bruteForce(String sourceFile) {
        String text;
        try {
            Path path = Paths.get(sourceFile);
            text = Files.readString(path);
        } catch (MalformedInputException e) {
            throw new RuntimeException("Содержимое файла не соответствует кодировке " + Charset.defaultCharset().name());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return caesarCipher.bruteForce(text);
    }

    public void validateFiles(String sourceFile, String destFile) {
        if (sourceFile == null || sourceFile.isEmpty()) {
            throw new RuntimeException("Путь к исходному файлу не заполнен");
        }

        if (destFile == null || destFile.isEmpty()) {
            throw new RuntimeException("Путь к итоговому файлу не заполнен");
        }

        if (!Files.exists(Path.of(sourceFile))) {
            throw new RuntimeException("Исходный файл " + sourceFile + " не найден");
        }

        if (sourceFile.equals(destFile)) {
            throw new RuntimeException("Исходный файл совпадает с итоговым");
        }
    }
}