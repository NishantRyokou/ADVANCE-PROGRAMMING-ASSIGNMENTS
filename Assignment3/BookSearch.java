import java.util.ArrayList;
import java.util.List;

public class BookSearch {
    public static void main(String[] args) {
        List<String> books = new ArrayList<>();

        books.add("The Great Gatsby");
        books.add("To Kill a Mockingbird");
        books.add("1984");
        books.add("The Catcher in the Rye");
        books.add("The Hobbit");
        books.add("The Lord of the Rings");

        String searchWord = "The";
        System.out.println("All Books:");
        for (String book : books) {
            System.out.println("- " + book);
        }

        System.out.println("\nSearch word: \"" + searchWord + "\"");
        System.out.println("Matching Books:");
        boolean found = false;
        for (String book : books) {
            if (book.toLowerCase().contains(searchWord.toLowerCase())) {
                System.out.println("- " + book);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No books found matching the search word.");
        }
    }
}
