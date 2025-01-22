import java.util.Scanner;

/**
 * A test program for managing a Table object.
 * Provides a menu-driven interface to perform various operations on a table.
 * Operations include displaying data, looking up values, searching for values,
 * changing cell content, saving data, and getting or setting cell values.
 *
 * @author Kunal Yadav
 *
 */
public class TableTestProgram {
    /**
     * The main method which runs the TableTestProgram.
     * It handles user input and invokes the corresponding Table methods
     * based on user choices.
     *
     * @param args Command-line arguments (not used).
     * @throws Exception if an input error occurs or if the Table class throws an exception.
     */
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in); // Scanner for user input
        String tablename = ""; // To store the name of the file
        String choice = ""; // To store the user's menu choice
        int row = -1; // To store the row number for operations
        int colNum = -1; // To store the column number for operations
        String key = ""; // To store the key for lookup/search
        String newValue = ""; // To store the new value for changing cell content

        // Prompt user to enter the file name
        System.out.print("Enter the name of the tab delimited text file you wish to manage (e.g. codes.txt) > ");
        tablename = in.nextLine(); // Read the file name from user input
        Table t = new Table(tablename); // Create a Table object with the given filename
        System.out.println("Successfully loaded: " + t);

        while (true) {
            // Display the menu
            System.out.println("\n\nTable Testing Menu\n");
            System.out.println("1. Display all data");
            System.out.println("2. Lookup");
            System.out.println("3. Search");
            System.out.println("4. Change");
            System.out.println("5. Save data to " + tablename);
            System.out.println("6. Get Single Cell Value");
            System.out.println("7. Set Single Cell Value");
            System.out.println("9. Quit");
            System.out.print("Select > ");
            choice = in.nextLine(); // Read user choice

            if (choice.equals("9")) break; // Exit the loop if the user chooses to quit

            // Handle user choice with a switch statement
            switch (choice) {
                case "1":
                    t.display(); // Display all data from the table
                    break;
                case "2":
                    System.out.print("Enter the key to lookup > ");
                    key = in.nextLine(); // Read the key for lookup
                    t.lookup(key); // Lookup and display the row for the key
                    break;
                case "3":
                    System.out.print("Enter the value to search > ");
                    key = in.nextLine(); // Read the value to search
                    t.search(key); // Search and display the position of the value
                    break;
                case "4":
                    System.out.print("Enter the row number > ");
                    row = Integer.parseInt(in.nextLine()); // Read and parse the row number
                    System.out.print("Enter the column number > ");
                    colNum = Integer.parseInt(in.nextLine()); // Read and parse the column number
                    System.out.print("Enter the new value > ");
                    newValue = in.nextLine(); // Read the new value
                    t.change(row, colNum, newValue); // Change the cell value at specified row and column
                    break;
                case "5":
                    t.save(); // Save the current table data to the file
                    break;
                case "6":
                    System.out.print("Enter the row number > ");
                    row = Integer.parseInt(in.nextLine()); // Read and parse the row number
                    System.out.print("Enter the column number > ");
                    colNum = Integer.parseInt(in.nextLine()); // Read and parse the column number
                    System.out.println("Cell value: " + t.getCell(row, colNum)); // Get and display the value of the specified cell
                    break;
                case "7":
                    System.out.print("Enter the row number > ");
                    row = Integer.parseInt(in.nextLine()); // Read and parse the row number
                    System.out.print("Enter the column number > ");
                    colNum = Integer.parseInt(in.nextLine()); // Read and parse the column number
                    System.out.print("Enter the new value > ");
                    newValue = in.nextLine(); // Read the new value
                    t.setCell(row, colNum, newValue); // Set the new value for the specified cell
                    break;
                default:
                    System.out.println("Invalid choice."); // Handle invalid menu choice
            }
        }
        System.out.println("Thank-you, goodbye!"); // Farewell message
        in.close(); // Close the scanner
    }
}
