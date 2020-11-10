import java.util.Scanner;

public class PhoneBook {
    public static void main(String[] args) {
        String name, phoneNumber;
        String[][] phoneBook = new String[1][2];
        int cntRecord = 0;
        Scanner scanner = new Scanner(System.in);
        boolean stopEntry = false;
        while (!stopEntry) {
            // Проверка корректности ввода ФИО
            System.out.println("Введите ФИО:");
            boolean isCorrectName = false;
            boolean isCorrectPhoneNumber = false;
            while (!isCorrectName) {
                name = scanner.nextLine();
                // Оцениваем корректность введенных данных
                isCorrectName = checkName(name);
                if (!isCorrectName) {
                    System.out.println("Введите ФИО в формате: Фамилия Имя Отчество");
                } else {
                    // Приводим ФИО к нужному нам формату
                    String formatName = formatName(name);
                    // Проверка наличия имени в телефонной книге
                    boolean isNameInPhoneBook = checkNameInPhoneBook(formatName, phoneBook, cntRecord);
                    // Добавляем ФИО в телефонную книгу
                    if (isNameInPhoneBook) {
                        System.out.println("Введите тел.номер:");
                        while (!isCorrectPhoneNumber) {
                            phoneNumber = scanner.nextLine();
                            // Оцениваем корректность введенных данных
                            isCorrectPhoneNumber = checkPhoneNumber(phoneNumber);
                            if (!isCorrectPhoneNumber) {
                                System.out.println("Введите тел.номер в формате: X (XXX) XXX-XX-XX");
                            } else {
                                // Приводим тел.номер к нужному нам формату
                                String formatPhoneNumber = formatPhoneNumber(phoneNumber);
                                // Добавление в телефонную книгу данных
                                phoneBook = addInPhoneBook(phoneBook, formatName, formatPhoneNumber, cntRecord);
                                // Увеличиваем счетчик записей
                                cntRecord++;
                            }
                        }
                    } else {
                        // Если такое имя в тел.книге есть, то получаем его телефонный номер
                        phoneNumber = getPhoneNumber(formatName, phoneBook);
                        System.out.println(formatName + ":" + phoneNumber);
                    }
                }
            }
            // Вариант заверешения заполнения тел.книги
            System.out.println("Введите \"stop\", чтобы остановить заполнение тел.книги. Введите \"list\", чтобы показать тел.книгу и продолжить");
            String command = scanner.nextLine();
            if (command.equals("list")) {
                list(phoneBook);
            } else if (command.equals("stop")) {
                stopEntry = true;
            }
        }
    }

    // Проверка корректности ФИО
    public static boolean checkName(String name) {
        String[] formatName = name.trim().split(" ");
        return formatName.length == 3;
    }

    // Форматирование ФИО
    public static String formatName(String name) {
        // Получение массива ФИО
        String[] arrayName = name.trim().split(" ");
        // Сортировка ФИО по длине (специально, чтобы избежать дублирования)
        sortByLength(arrayName);
        // Заглавные буквы для каждой составляющей имени
        String formatName = "";
        for (String temp : arrayName) {
            if (Character.isUpperCase(temp.charAt(0))) {
                formatName += temp;
            } else {
                formatName += Character.toUpperCase(temp.charAt(0)) + temp.substring(1);
            }
            formatName += " ";
        }
        return formatName;
    }

    // Проверка корректности телефонного номера
    public static boolean checkPhoneNumber(String phoneNumber) {
        String formatPhoneNumber = phoneNumber.replaceAll("[^0-9]", "");
        return formatPhoneNumber.length() == 11;
    }

    // Форматирование телефонного номера
    public static String formatPhoneNumber(String phoneNumber) {
        String cleanPhoneNumber = phoneNumber.replaceAll("[^0-9]", "");
        return "+7 " + cleanPhoneNumber.substring(1, 4) + " " + cleanPhoneNumber.substring(4, 7)
                + " " + cleanPhoneNumber.substring(7, 9) + " " + cleanPhoneNumber.substring(9);
    }

    // Добавление записи в телефонную книгу
    public static String[][] addInPhoneBook(String[][] phoneBook, String name, String number, int cntRecord) {
        // Создаем новую телефонную книгу, если в текущей закончилось место
        if (cntRecord >= phoneBook.length) {
            String[][] temp = new String[phoneBook.length + 1][2];
            for (int i = 0; i < phoneBook.length; i++) {
                temp[i] = phoneBook[i];
            }
            //temp = Arrays.copyOf(phoneBook, phoneBook.length);
            phoneBook = temp;
            cntRecord = phoneBook.length - 1;
        }
        // Добавляем новое имя и тел. номер в книгу
        phoneBook[cntRecord][0] = name;
        phoneBook[cntRecord][1] = number;
        return phoneBook;
    }

    // Отображение записей телефонной книги
    public static void list(String[][] phoneBook) {
        for (String[] numberLine : phoneBook) {
            System.out.println(numberLine[0] + ": " + numberLine[1]);
        }
    }

    // Сортировка слов по длине (от короткого к длинному) 
    public static void sortByLength(String[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - 1; j++) {
                if (array[j].length() > array[j + 1].length()) {
                    String temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

    // Проверка наличия имени в телефонной книге
    public static boolean checkNameInPhoneBook(String name, String[][] phoneBook, int cntRecord) {
        boolean isNameInPhoneBook = true;
        if (cntRecord == 0) {
            isNameInPhoneBook = true;
        } else {
            for (String[] numberLine : phoneBook) {
                if (numberLine[0].equals(name)) {
                    isNameInPhoneBook = false;
                    break;
                }
            }
        }
        return isNameInPhoneBook;
    }

    // Получение телефонного номера по ФИО
    public static String getPhoneNumber(String name, String[][] phoneBook) {
        String phoneNumber = "";
        for (String[] numberLine : phoneBook) {
            if (numberLine[0].equals(name)) {
                phoneNumber = numberLine[1];
            }
        }
        return phoneNumber;
    }
}
