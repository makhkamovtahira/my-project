// Base class for all reading materials
class ReadingMaterial {
    protected String title;
    protected int numberOfPages;

    // Constructor
    public ReadingMaterial(String title, int numberOfPages) {
        this.title = title;
        this.numberOfPages = numberOfPages;
    }

    // Method to display information about the material
    public void displayInfo() {
        System.out.println("Title: " + title);
        System.out.println("Number of Pages: " + numberOfPages);
    }
}

// Subclass for Books
class Book extends ReadingMaterial {
    protected String author;

    // Constructor
    public Book(String title, int numberOfPages, String author) {
        super(title, numberOfPages);
        this.author = author;
    }

    // Display information about the book
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Author: " + author);
    }
}

// Subclass for Novels
class Novel extends Book {
    private String mainCharacter;

    // Constructor
    public Novel(String title, int numberOfPages, String author, String mainCharacter) {
        super(title, numberOfPages, author);
        this.mainCharacter = mainCharacter;
    }

    // Display information about the novel
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Main Character: " + mainCharacter);
    }
}

// Subclass for Magazines
class Magazine extends ReadingMaterial {
    private String issue;
    private String publisher;

    // Constructor
    public Magazine(String title, int numberOfPages, String issue, String publisher) {
        super(title, numberOfPages);
        this.issue = issue;
        this.publisher = publisher;
    }

    // Display information about the magazine
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Issue: " + issue);
        System.out.println("Publisher: " + publisher);
    }
}

// Subclass for Technical Journals
class TechnicalJournal extends ReadingMaterial {
    private String scope;
    private double impactFactor;

    // Constructor
    public TechnicalJournal(String title, int numberOfPages, String scope, double impactFactor) {
        super(title, numberOfPages);
        this.scope = scope;
        this.impactFactor = impactFactor;
    }

    // Display information about the technical journal
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Scope: " + scope);
        System.out.println("Impact Factor: " + impactFactor);
    }
}

// Subclass for Textbooks
class Textbook extends ReadingMaterial {
    private String subject;
    private String edition;

    // Constructor
    public Textbook(String title, int numberOfPages, String subject, String edition) {
        super(title, numberOfPages);
        this.subject = subject;
        this.edition = edition;
    }

    // Display information about the textbook
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Subject: " + subject);
        System.out.println("Edition: " + edition);
    }
}