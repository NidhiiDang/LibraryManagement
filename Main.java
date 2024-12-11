import java.util.ArrayList;
import java.util.Scanner;

class Book {
    private int id;
    private String title;
    private String author;
    private String genre;
    private int publicationYear;
    private boolean isAvailable;

    public Book(int id, String title, String author, String genre, int publicationYear) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publicationYear = publicationYear;
        this.isAvailable = true;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}

class Member {
    private int id;
    private String name;
    private ArrayList<Book> issuedBooks;

    public Member(int id, String name) {
        this.id = id;
        this.name = name;
        this.issuedBooks = new ArrayList<>();
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Book> getIssuedBooks() {
        return issuedBooks;
    }

    public void addIssuedBook(Book book) {
        issuedBooks.add(book);
    }

    public void returnBook(Book book) {
        issuedBooks.remove(book);
    }
}

class Library {
    private ArrayList<Book> books = new ArrayList<>();
    private ArrayList<Member> members = new ArrayList<>();
    private static int nextBookId = 1;
    private static int nextMemberId = 1;

    public void addBook(String title, String author, String genre, int publicationYear) {
        Book book = new Book(nextBookId++, title, author, genre, publicationYear);
        books.add(book);
        System.out.println("Book added successfully.");
    }

    public void removeBook(int bookId) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId() == bookId) {
                books.remove(i);
                System.out.println("Book removed successfully.");
                return;
            }
        }
        System.out.println("Book not found.");
    }

    public void displayAllBooks() {
        System.out.println("All Books:");
        System.out.println("--------------------------------------------------------------------------------------------------");
        System.out.printf("%-5s | %-30s | %-20s | %-15s | %-15s%n", "ID", "Title", "Author", "Genre", "Publication Year");
        System.out.println("--------------------------------------------------------------------------------------------------");
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            System.out.printf("%-5d | %-30s | %-20s | %-15s | %-15d%n", book.getId(), book.getTitle(), book.getAuthor(), book.getGenre(), book.getPublicationYear());
        }
        System.out.println("--------------------------------------------------------------------------------------------------");
    }

    public void searchBooks(String keyword) {
        System.out.println("Search Results:");
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            if (book.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                    book.getAuthor().toLowerCase().contains(keyword.toLowerCase()) ||
                    book.getGenre().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println("ID: " + book.getId() + ", Title: " + book.getTitle() + ", Author: " + book.getAuthor() +
                        ", Genre: " + book.getGenre() + ", Publication Year: " + book.getPublicationYear() +
                        ", Available: " + (book.isAvailable() ? "Yes" : "No"));
            }
        }
    }

    public void issueBook(int bookId, int memberId) {
        Book book = getBookById(bookId);
        if (book == null) {
            System.out.println("Book not found.");
            return;
        }
        if (!book.isAvailable()) {
            System.out.println("Book is already issued.");
            return;
        }
        Member member = getMemberById(memberId);
        if (member == null) {
            System.out.println("Member not found.");
            return;
        }
        book.setAvailable(false);
        member.addIssuedBook(book);
        System.out.println("Book issued successfully.");
    }

    public void returnBook(int bookId, int memberId) {
        Book book = getBookById(bookId);
        if (book == null) {
            System.out.println("Book not found.");
            return;
        }
        Member member = getMemberById(memberId);
        if (member == null) {
            System.out.println("Member not found.");
            return;
        }
        if (!member.getIssuedBooks().contains(book)) {
            System.out.println("Book is not issued to this member.");
            return;
        }
        book.setAvailable(true);
        member.returnBook(book);
        System.out.println("Book returned successfully.");
    }

    public void displayIssuedBooks() {
        System.out.println("Issued Books:");
        System.out.println("--------------------------------------------------------------------------------------------------");
        System.out.printf("%-5s | %-30s | %-20s | %-15s%n",
                "ID", "Title", "Author", "Genre");
        System.out.println("--------------------------------------------------------------------------------------------------");
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            if (!book.isAvailable()) {
                System.out.printf("%-5d | %-30s | %-20s | %-15s%n",
                        book.getId(), book.getTitle(), book.getAuthor(), book.getGenre());
            }
        }
        System.out.println("--------------------------------------------------------------------------------------------------");
    }

    public void addMember(String name) {
        Member member = new Member(nextMemberId++, name);
        members.add(member);
        System.out.println("Member added successfully.");
    }

    public void displayMembers() {
        System.out.println("All Members:");
        System.out.println("------------");
        System.out.printf("%-5s | %-20s%n", "ID", "Name");
        System.out.println("------------");
        for (int i = 0; i < members.size(); i++) {
            Member member = members.get(i);
            System.out.printf("%-5d | %-20s%n", member.getId(), member.getName());
        }
        System.out.println("------------");
    }

    private Book getBookById(int id) {
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }

    private Member getMemberById(int id) {
        for (int i = 0; i < members.size(); i++) {
            Member member = members.get(i);
            if (member.getId() == id) {
                return member;
            }
        }
        return null;
    }
}

public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Add a Book");
            System.out.println("2. Remove a Book");
            System.out.println("3. Display All Books");
            System.out.println("4. Search for a Book");
            System.out.println("5. Issue a Book");
            System.out.println("6. Return a Book");
            System.out.println("7. Display Issued Books");
            System.out.println("8. Add a Member");
            System.out.println("9. Display All Members");
            System.out.println("10. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter author: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter genre: ");
                    String genre = scanner.nextLine();
                    System.out.print("Enter publication year: ");
                    int publicationYear = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    library.addBook(title, author, genre, publicationYear);
                    break;
                case 2:
                    System.out.print("Enter book ID to remove: ");
                    int bookIdToRemove = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    library.removeBook(bookIdToRemove);
                    break;
                case 3:
                    library.displayAllBooks();
                    break;
                case 4:
                    System.out.print("Enter keyword to search: ");
                    String keyword = scanner.nextLine();
                    library.searchBooks(keyword);
                    break;
                case 5:
                    System.out.print("Enter book ID to issue: ");
                    int bookIdToIssue = scanner.nextInt();
                    System.out.print("Enter member ID to issue to: ");
                    int memberIdToIssueTo = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    library.issueBook(bookIdToIssue, memberIdToIssueTo);
                    break;
                case 6:
                    System.out.print("Enter book ID to return: ");
                    int bookIdToReturn = scanner.nextInt();
                    System.out.print("Enter member ID who is returning: ");
                    int memberIdReturning = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    library.returnBook(bookIdToReturn, memberIdReturning);
                    break;
                case 7:
                    library.displayIssuedBooks();
                    break;
                case 8:
                    System.out.print("Enter member name: ");
                    String memberName = scanner.nextLine();
                    library.addMember(memberName);
                    break;
                case 9:
                    library.displayMembers();
                    break;
                case 10:
                    System.out.println("Exiting program.");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
