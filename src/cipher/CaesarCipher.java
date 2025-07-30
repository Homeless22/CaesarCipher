package cipher;

import java.util.HashMap;
import java.util.Map;

public class CaesarCipher {
    private final CipherAlphabet cipherAlphabet;

    public CaesarCipher(CipherAlphabet cipherAlphabet) {
        this.cipherAlphabet = cipherAlphabet;
    }

    /***
     * Применение сдвига к тексту
     * @param text исходный текст
     * @param shift значение сдвига
     * @return результат применения сдвига к тексту
     */
    private String applyShift(String text, int shift) {
        int size = cipherAlphabet.getSize();
        StringBuilder shiftedText = new StringBuilder();
        for (char c : text.toCharArray()) {
            int pos = cipherAlphabet.getIndexByChar(Character.toLowerCase(c));
            if (pos >= 0) {
                int newPos = ((pos + shift) >= 0 ? (pos + shift) : size + (pos + shift)) % size;
                char newChar = cipherAlphabet.getCharByIndex(newPos);
                shiftedText.append(Character.isUpperCase(c) ? Character.toUpperCase(newChar) : newChar);
            } else {
                shiftedText.append(c);
            }
        }
        return shiftedText.toString();
    }

    /***
     * Шифрование текста
     * @param text исходный текст
     * @param shift значение сдвига
     * @return зашифрованная строка
     */
    public String encrypt(String text, int shift) {
        return applyShift(text, shift);
    }

    /***
     * Расшифровка текста
     * @param text зашифрованный текст
     * @param shift значение сдвига
     * @return возвращает расшированную строку
     */
    public String decrypt(String text, int shift) {
        return applyShift(text, -1 * shift);
    }

    /***
     * Получение ключа шифра (сдвига) методом перебора
     * @param text зашифрованный текст
     * @return значение сдвига
     */
    public int bruteForce(String text) {
        Double maxScore = 0.0;
        int shift = 0;
        for (int i = 1; i <= cipherAlphabet.getSize(); i++) {
            String decryptedText = decrypt(text, i);
            Double score = calculateTextScore(decryptedText);
            if (Double.compare(score, maxScore) > 0) {
                maxScore = score;
                shift = i;
            }
        }
        return shift;
    }

    /***
     * Метод оценки правильности расшифровки текста на основе частотного анализа
     * @param text проверяемый текст
     * @return рассчитанная оценка правильности расшифровки текста
     */
    private double calculateTextScore(String text) {
        Map<Character, Integer> letterCounts = new HashMap<>();
        int totalLetters = 0;
        for (Character c : text.toLowerCase().toCharArray()) {
            letterCounts.put(c, letterCounts.getOrDefault(c, 0) + 1);
            totalLetters++;
        }
        Double score = 0.0;
        for (Map.Entry<Character, Integer> entry : letterCounts.entrySet()) {
            Character letter = entry.getKey();
            double actualFrequency = (double) letterCounts.get(letter) / (double) totalLetters;
            double expectedFrequency = cipherAlphabet.getLetterFrequency(letter);
            score += Math.abs(expectedFrequency - actualFrequency);
        }
        return 1.0 / (1.0 + score);
    }
}
