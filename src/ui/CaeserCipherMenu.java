package ui;

import cipher.CaesarCipherFileProcessor;

import java.util.Scanner;

public class CaeserCipherMenu {
    private final Scanner scanner;
    private final CaesarCipherFileProcessor cipherFileProcessor;

    public CaeserCipherMenu(CaesarCipherFileProcessor cipherFileProcessor) {
        this.scanner = new Scanner(System.in);
        this.cipherFileProcessor = cipherFileProcessor;
    }

    /***
     * Вывод в консоль основного меню
     *
     */
    public void showMenu() {
        while (true) {
            System.out.println("\n===== Меню =====");
            System.out.println("1. Шифрование файла");
            System.out.println("2. Расшифровка файла");
            System.out.println("3. Взлом шифра");
            System.out.println("4. Выход");
            System.out.print("Выберите пункт меню: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите число от 1 до 4");
                continue;
            }

            switch (choice) {
                case 1 -> encryptFile();
                case 2 -> decryptFile();
                case 3 -> bruteForce();
                case 4 -> {
                    System.out.println("Выход из программы...");
                    return;
                }
                default -> System.out.println("Ошибка: неверный пункт меню");
            }
        }
    }

    private String inputFilePath(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    /***
     * обработка выбора команды "Шифрование файла"
     *
     */
    private void encryptFile() {
        System.out.println("\nШифрование файла");
        String sourceFilePath = inputFilePath("Введите путь к исходному файлу: ");
        String destinationFilePath = inputFilePath("Введите путь для зашифрованного файла: ");
        try {
            cipherFileProcessor.validateFiles(sourceFilePath, destinationFilePath);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.print("Введите ключ шифрования: ");
        int shift;
        try {
            shift = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: ключ должен быть целым числом");
            return;
        }

        try {
            cipherFileProcessor.encryptFile(sourceFilePath, destinationFilePath, shift);
            System.out.println("Файл успешно зашифрован");
        } catch (Exception e) {
            System.out.println("Ошибка при шифровании: " + e.getMessage());
        }
    }

    /***
     * обработка выбора команды "Расшифровка файла"
     *
     */
    private void decryptFile() {
        System.out.println("\nРасшифровка файла");
        String sourceFilePath = inputFilePath("Введите путь к зашифрованному файлу: ");
        String destinationFilePath = inputFilePath("Введите путь для расшифрованного файла: ");
        try {
            cipherFileProcessor.validateFiles(sourceFilePath, destinationFilePath);
        } catch (Exception e) {
            System.out.println("Введены ");
            return;
        }

        System.out.print("Введите ключ шифрования: ");
        int shift;
        try {
            shift = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: ключ должен быть целым числом");
            return;
        }

        try {
            cipherFileProcessor.decryptFile(sourceFilePath, destinationFilePath, shift);
            System.out.println("Файл успешно расшифрован");
        } catch (Exception e) {
            System.out.println("Ошибка при расшифровке: " + e.getMessage());
        }
    }

    /***
     * обработка выбора команды "Взлом шифра"
     *
     */
    private void bruteForce() {
        System.out.println("\nВзлом шифра");
        String sourceFilePath = inputFilePath("Введите путь к зашифрованному файлу: ");
        String destinationFilePath = inputFilePath("Введите путь для взломанного файла: ");
        cipherFileProcessor.validateFiles(sourceFilePath, destinationFilePath);

        try {
            int shift = cipherFileProcessor.bruteForce(sourceFilePath);
            System.out.println("Найденный ключ: " + shift);
            cipherFileProcessor.decryptFile(sourceFilePath, destinationFilePath, shift);
            System.out.println("Файл успешно расшифрован");
        } catch (Exception e) {
            System.out.println("Ошибка при взломе шифра: " + e.getMessage());
        }
    }
}

