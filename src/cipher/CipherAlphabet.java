package cipher;

import java.util.HashMap;
import java.util.Map;

public class CipherAlphabet {
    private static final String ALPHABET = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя.,\":-!? ";

    private static final Map<Character, Double> LETTER_FREQUENCIES = new HashMap<>() {{
        put('о', 0.09);
        put('е', 0.072); //е,ё
        put('а', 0.062);
        put('и', 0.062);
        put('н', 0.053);
        put('т', 0.053);
        put('с', 0.045);
        put('р', 0.04);
        put('в', 0.038);
        put('л', 0.035);
        put('к', 0.028);
        put('м', 0.026);
        put('д', 0.025);
        put('п', 0.023);
        put('у', 0.021);
        put('я', 0.018);
        put('ы', 0.016);
        put('з', 0.016);
        put('б', 0.014);
        put('ь', 0.014); //ь, ъ
        put('г', 0.013);
        put('ч', 0.012);
        put('й', 0.01);
        put('х', 0.009);
        put('ж', 0.007);
        put('ю', 0.006);
        put('ш', 0.006);
        put('ц', 0.004);
        put('щ', 0.003);
        put('э', 0.003);
        put('ф', 0.002);
    }};

    /***
     * Возвращает размер алфавита
     * @return размер алфавита
     */
    public int getSize() {
        return ALPHABET.length();
    }

    /***
     * Возвращает индекс символа алфавита
     * @param char символ алфавита
     * @return индекс символа
     */
    public int getIndexByChar(char symbol) {
        return ALPHABET.indexOf(symbol);
    }

    /***
     * Возвращает символ алфавита по индексу
     * @param int индекс символа
     * @return символ алфавита
     */
    public char getCharByIndex(int index) {
        return ALPHABET.charAt(index);
    }

    /***
     * Возвращает ожидаемую частоту использования символа алфавита
     * @param char символ алфавита
     * @return ожидаемая частота использования символа алфавита
     */
    public Double getLetterFrequency(Character letter) {
        return LETTER_FREQUENCIES.getOrDefault(letter, 0.0);
    }
}
