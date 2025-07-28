// Базовый класс для всех читаемых материалов
class ReadingMaterial {
    protected String title; // Название материала
    protected int numberOfPages; // Количество страниц

    // Конструктор базового класса
    public ReadingMaterial(String title, int numberOfPages) {
        this.title = title;
        this.numberOfPages = numberOfPages;
    }

    // Метод для отображения основной информации
    public void displayInfo() {
        System.out.println("Title: " + title);
        System.out.println("Number of Pages: " + numberOfPages);
    }
}

// Подкласс для книг
class Book extends ReadingMaterial {
    protected String author; // Автор книги

    // Конструктор для класса Book
    public Book(String title, int numberOfPages, String author) {
        super(title, numberOfPages); // Вызов конструктора базового класса
        this.author = author;
    }

    // Переопределение метода для отображения информации
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Author: " + author);
    }
}

// Подкласс для романов
class Novel extends Book {
    private String mainCharacter; // Главный герой романа

    // Конструктор для класса Novel
    public Novel(String title, int numberOfPages, String author, String mainCharacter) {
        super(title, numberOfPages, author);
        this.mainCharacter = mainCharacter;
    }

    // Переопределение метода для отображения информации
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Main Character: " + mainCharacter);
    }
}

// Подкласс для журналов
class Magazine extends ReadingMaterial {
    private String issue; // Номер выпуска
    private String publisher; // Издатель журнала

    // Конструктор для класса Magazine
    public Magazine(String title, int numberOfPages, String issue, String publisher) {
        super(title, numberOfPages);
        this.issue = issue;
        this.publisher = publisher;
    }

    // Переопределение метода для отображения информации
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Issue: " + issue);
        System.out.println("Publisher: " + publisher);
    }
}

// Подкласс для технических журналов
class TechnicalJournal extends ReadingMaterial {
    private String scope; // Тематика журнала
    private double impactFactor; // Импакт-фактор журнала

    // Конструктор для класса TechnicalJournal
    public TechnicalJournal(String title, int numberOfPages, String scope, double impactFactor) {
        super(title, numberOfPages);
        this.scope = scope;
        this.impactFactor = impactFactor;
    }

    // Переопределение метода для отображения информации
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Scope: " + scope);
        System.out.println("Impact Factor: " + impactFactor);
    }
}

// Подкласс для учебников
class Textbook extends ReadingMaterial {
    private String subject; // Предмет, к которому относится учебник
    private String edition; // Издание учебника

    // Конструктор для класса Textbook
    public Textbook(String title, int numberOfPages, String subject, String edition) {
        super(title, numberOfPages);
        this.subject = subject;
        this.edition = edition;
    }

    // Переопределение метода для отображения информации
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Subject: " + subject);
        System.out.println("Edition: " + edition);
    }
}

// Класс-драйвер для тестирования всех типов читаемых материалов
class ReadingMaterialDemo {
    public static void main(String[] args) {
        // Создание объектов различных типов материалов
        Novel novel = new Novel("The Great Adventure", 300, "John Doe", "Alice");
        Magazine magazine = new Magazine("Tech Monthly", 50, "November 2024", "Tech Publishers");
        TechnicalJournal journal = new TechnicalJournal("AI Innovations", 120, "Artificial Intelligence", 4.8);
        Textbook textbook = new Textbook("Introduction to Java", 500, "Computer Science", "3rd Edition");

        // Вывод информации о каждом материале
        System.out.println("Novel Info:");
        novel.displayInfo();

        System.out.println("\nMagazine Info:");
        magazine.displayInfo();

        System.out.println("\nTechnical Journal Info:");
        journal.displayInfo();

        System.out.println("\nTextbook Info:");
        textbook.displayInfo();
    }
}
