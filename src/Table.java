import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Represents a table read from a tab-delimited file.
 * Provides methods to validate tickets, check if a code exists,
 * display the table, look up values, search for values,
 * change cell values, save the table, and get/set cell values.
 *
 * @author Kunal Yadav
 *
 */
public class Table {
    private String tablename; // Name of the file representing the table
    private int numRows; // Number of rows in the table
    private int numCols; // Number of columns in the table
    private String[][] grid; // 2D array to store the table data

    /**
     * Constructs a Table object by reading data from the specified file.
     *
     * @param filename The name of the file to read from.
     */
    public Table(String filename) {
        tablename = filename;
        numRows = 0;
        numCols = 0;
        String s;
        String[] item;

        // First pass: Determine the number of rows and columns
        try {
            Scanner theFile = new Scanner(new FileInputStream(new File(tablename)));
            while (theFile.hasNextLine()) {
                s = theFile.nextLine();
                item = s.split("\t", -1);

                if (item.length > numCols)
                    numCols = item.length;

                numRows++;
            }
            theFile.close();
        } catch (FileNotFoundException e) {
            System.out.println("Table class Error 1: file not found.");
        }

        grid = new String[numRows][numCols];

        // Second pass: Read data into the grid
        try {
            Scanner theFile = new Scanner(new FileInputStream(new File(tablename)));
            int r = 0;
            while (theFile.hasNextLine()) {
                s = theFile.nextLine();
                item = s.split("\t", -1);

                // Fill the grid with data from the file, padding with empty strings if necessary
                for (int c = 0; c < numCols; c++) {
                    if (c < item.length) {
                        grid[r][c] = item[c];
                    } else {
                        grid[r][c] = "";
                    }
                }
                r++;
            }
            theFile.close();
        } catch (Exception e) {
            System.out.println("Table class Error 2: file not found.");
        }
    }

    /**
     * Validates a ticket code and updates its status if valid.
     *
     * @param code The ticket code to validate.
     * @return A string indicating the ticket status: "VALID", "NOT_PURCHASED", "DUPLICATE", "INVALID".
     */
    public String checkTicketStatus(String code) {
        for (int r = 0; r < numRows; r++) {
            if (grid[r][0].equals(code)) {
                if (grid[r][1].equals("N")) {
                    grid[r][1] = "Y"; // Mark as purchased
                    save(); // Save the updated status
                    return "NOT_PURCHASED";
                } else if (grid[r][2].equals("N")) {
                    grid[r][2] = "Y"; // Mark as entered
                    save(); // Save the updated status
                    return "VALID";
                } else {
                    return "DUPLICATE"; // Already redeemed
                }
            }
        }
        return "INVALID"; // Ticket not found
    }
    /**
     * Resets the status for all tickets.
     */
    public void resetStatuses() {
        for (int r = 0; r < numRows; r++) {
            if (grid[r][1].equals("Y")) {
                grid[r][1] = "N"; // Reset purchase status
                if (grid[r][2].equals("Y")) {
                    grid[r][2] = "N"; // Reset entry status if it was Y
                }
            }
        }
        save(); // Save the updated status
    }

    /**
     * Returns a string representation of the table including its name, number of rows, and number of columns.
     *
     * @return A string representation of the table.
     */
    public String toString() {
        return ("Table: " + tablename + "  rows = " + numRows + "  cols = " + numCols);
    }


    /**
     * Displays the entire table in the console.
     */
    public void display() {
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                System.out.print(grid[r][c] + "\t");
            }
            System.out.println();
        }
    }

    /**
     * Looks up and displays a row in the table that matches the given key.
     *
     * @param key The key to look up.
     */
    public void lookup(String key) {
        for (int r = 0; r < numRows; r++) {
            if (grid[r][0].equals(key)) {
                // Print the entire row
                for (int c = 0; c < numCols; c++) {
                    System.out.print(grid[r][c] + "\t");
                }
                System.out.println();
                return;
            }
        }
        System.out.println("Key not found.");
    }

    /**
     * Searches for a value in the table and prints the position if found.
     *
     * @param value The value to search for.
     */
    public void search(String value) {
        boolean found = false;
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                if (grid[r][c].equals(value)) {
                    System.out.println("Found at row " + r + " column " + c);
                    found = true;
                }
            }
        }
        if (!found) {
            System.out.println("Value not found.");
        }
    }

    /**
     * Changes the value of a specific cell in the table.
     *
     * @param row The row index of the cell to change.
     * @param col The column index of the cell to change.
     * @param newValue The new value to set.
     */
    public void change(int row, int col, String newValue) {
        if (row < numRows && col < numCols) {
            grid[row][col] = newValue;
        } else {
            System.out.println("Invalid row or column.");
        }
    }

    /**
     * Saves the current state of the table back to the file.
     */
    public void save() {
        try {
            FileWriter writer = new FileWriter(tablename);
            for (int r = 0; r < numRows; r++) {
                for (int c = 0; c < numCols; c++) {
                    writer.write(grid[r][c]);
                    if (c < numCols - 1) writer.write("\t"); // Write tab delimiter
                }
                writer.write("\n"); // Write new line at the end of each row
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving file.");
        }
    }

    /**
     * Gets the value of a specific cell in the table.
     *
     * @param row The row index of the cell.
     * @param col The column index of the cell.
     * @return The value of the cell, or an error message if the indices are invalid.
     */
    public String getCell(int row, int col) {
        if (row < numRows && col < numCols) {
            return grid[row][col];
        } else {
            return "Invalid row or column.";
        }
    }

    /**
     * Sets the value of a specific cell in the table.
     *
     * @param row The row index of the cell.
     * @param col The column index of the cell.
     * @param newValue The new value to set.
     */
    public void setCell(int row, int col, String newValue) {
        if (row < numRows && col < numCols) {
            grid[row][col] = newValue;
        } else {
            System.out.println("Invalid row or column.");
        }
    }
}
