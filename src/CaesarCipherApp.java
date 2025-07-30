import cipher.CaesarCipher;
import cipher.CaesarCipherFileProcessor;
import cipher.CipherAlphabet;
import ui.CaeserCipherMenu;

public class CaesarCipherApp {
    public static void main(String[] args) {
        CaeserCipherMenu menu = new CaeserCipherMenu(new CaesarCipherFileProcessor(new CaesarCipher(new CipherAlphabet())));
        menu.showMenu();
    }
}