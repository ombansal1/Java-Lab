import java.sql.*;

public class RestaurantJDBC {

    static final String URL = "jdbc:mysql://localhost:3306/restaurant_db";
    static final String USER = "root";
    static final String PASS = "sit123";

    public static Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String serverUrl = "jdbc:mysql://localhost:3306/";
        try (Connection serverCon = DriverManager.getConnection(serverUrl, USER, PASS);
             Statement st = serverCon.createStatement()) {
            st.executeUpdate("CREATE DATABASE IF NOT EXISTS restaurant_db");
        }

        return DriverManager.getConnection(URL, USER, PASS);
    }

    public static void createTables(Connection con) throws Exception {
        try (Statement st = con.createStatement()) {
            String createRestaurant = "CREATE TABLE Restaurant (" +
                    "Id INT PRIMARY KEY, " +
                    "Name VARCHAR(255), " +
                    "Address VARCHAR(255))";
            st.executeUpdate(createRestaurant);
            String createMenuItem = "CREATE TABLE MenuItem (" +
                    "Id INT PRIMARY KEY, " +
                    "Name VARCHAR(255), " +
                    "Price DOUBLE, " +
                    "ResId INT, " +
                    "FOREIGN KEY (ResId) REFERENCES Restaurant(Id))";
            st.executeUpdate(createMenuItem);
        }
    }

    public static void insertData(Connection con) throws Exception {
        String insertRestaurant = "INSERT INTO Restaurant VALUES (?, ?, ?)";
        String insertMenu = "INSERT INTO MenuItem VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps1 = con.prepareStatement(insertRestaurant);
             PreparedStatement ps2 = con.prepareStatement(insertMenu)) {

            Object[][] restaurants = {
                {1, "Spice Garden", "12 Lotus Avenue"},
                {2, "Stone Oven Pizzeria", "45 River Street"},
                {3, "Grill Hub", "88 Hill Road"},
                {4, "Pasta Port", "21 Olive Lane"},
                {5, "Taco Town", "77 Sunset Boulevard"},
                {6, "Sub Station", "9 Central Plaza"},
                {7, "Sushi Square", "63 Harbor View"},
                {8, "Prime Steak Co", "154 King's Cross"},
                {9, "Green Bowl", "37 Garden Walk"},
                {10, "Morning Table", "5 Sunrise Court"}
            };

            for (Object[] r : restaurants) {
                ps1.setInt(1, (Integer) r[0]);
                ps1.setString(2, (String) r[1]);
                ps1.setString(3, (String) r[2]);
                ps1.executeUpdate();
            }

            Object[][] menuItems = {
                {101, "Masala Chai", 65.0, 1},
                {102, "Lemon Iced Tea", 55.0, 1},
                {103, "Saffron Bun", 135.0, 1},
                {104, "Margherita Slice", 275.0, 2},
                {105, "Smoky Burger", 140.0, 3},
                {106, "Alfredo Pasta", 210.0, 4},
                {107, "Crunch Taco", 95.0, 5},
                {108, "Berry Pancake", 160.0, 10},
                {109, "Garden Salad", 145.0, 9},
                {110, "Ribeye Steak", 575.0, 8}
            };

            for (Object[] m : menuItems) {
                ps2.setInt(1, (Integer) m[0]);
                ps2.setString(2, (String) m[1]);
                ps2.setDouble(3, (Double) m[2]);
                ps2.setInt(4, (Integer) m[3]);
                ps2.executeUpdate();
            }

            System.out.println("Inserted 10 specific records in each table.");
        }
    }

    public static void displayAllRestaurants(Connection con) throws Exception {
        String query = "SELECT * FROM Restaurant";
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            System.out.println("\nAll Restaurants After Insert:");
            System.out.printf("%-5s %-20s %-30s\n", "ID", "Name", "Address");
            System.out.println("---------------------------------------------------------------");
            while (rs.next()) {
                System.out.printf("%-5d %-20s %-30s\n",
                        rs.getInt("Id"),
                        rs.getString("Name"),
                        rs.getString("Address"));
            }
        }
    }

    public static void displayAllMenuAfterInsert(Connection con) throws Exception {
        printMenuByQuery(con, "SELECT * FROM MenuItem", "\nAll Menu Items After Insert:");
    }

    public static void selectPriceLessThan100(Connection con) throws Exception {
        printMenuByQuery(con, "SELECT * FROM MenuItem WHERE Price <= 100", "\nMenu Items with Price <= 100:");
    }

    public static void selectSpiceGardenItems(Connection con) throws Exception {

        String query = "SELECT m.* FROM MenuItem m " +
                "JOIN Restaurant r ON m.ResId = r.Id " +
                "WHERE r.Name = 'Spice Garden'";

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            System.out.println("\nItems from Spice Garden:");
            printMenu(rs);
        }
    }

    public static void updatePrice(Connection con) throws Exception {

        String query = "UPDATE MenuItem SET Price = 200 WHERE Price <= 100";
        try (Statement st = con.createStatement()) {
            int rows = st.executeUpdate(query);
            System.out.println("\nUpdated rows (Price <= 100 to 200): " + rows);
        }
        
        // Display menu items after update
        String selectQuery = "SELECT * FROM MenuItem ORDER BY Id";
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(selectQuery)) {
            System.out.println("\nMenu Items After Update:");
            printMenu(rs);
        }
    }

    public static void deleteItems(Connection con) throws Exception {

        String query = "DELETE FROM MenuItem WHERE Name LIKE 'P%'";
        try (Statement st = con.createStatement()) {
            int rows = st.executeUpdate(query);
            System.out.println("\nDeleted rows (Name starting with 'P'): " + rows);
        }
        
        String selectQuery = "SELECT * FROM MenuItem ORDER BY Id";
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(selectQuery)) {
            System.out.println("\nMenu Items After Deletion:");
            printMenu(rs);
        }
    }

    public static void selectAllItems(Connection con) throws Exception {
        printMenuByQuery(con, "SELECT * FROM MenuItem", "\nFinal Menu Items Data:");
    }

    private static void printMenuByQuery(Connection con, String query, String title) throws Exception {
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            System.out.println(title);
            printMenu(rs);
        }
    }

    public static void printMenu(ResultSet rs) throws Exception {

        System.out.printf("%-5s %-15s %-10s %-10s\n", "ID", "Name", "Price", "ResId");

        boolean hasRows = false;
        while (rs.next()) {
            hasRows = true;
            System.out.printf("%-5d %-15s %-10.2f %-10d\n",
                    rs.getInt("Id"),
                    rs.getString("Name"),
                    rs.getDouble("Price"),
                    rs.getInt("ResId"));
        }
        
        if (!hasRows) {
            System.out.println("(No records found)");
        }
    }
    public static void main(String[] args) {
        System.out.println("Starting RestaurantJDBC Application...");

        try {
            System.out.println("Connecting to MySQL database...");
            try (Connection con = getConnection()) {
                System.out.println(" Connected successfully!\n");

                System.out.println("Creating tables...");
                createTables(con);
                System.out.println();

                System.out.println("Inserting data...");
                insertData(con);
                System.out.println();

                System.out.println("Displaying all restaurants...");
                displayAllRestaurants(con);
                System.out.println();

                System.out.println("Displaying all menu items after insert...");
                displayAllMenuAfterInsert(con);
                System.out.println();

                System.out.println("Selecting items with Price <= 100...");
                selectPriceLessThan100(con);
                System.out.println();

                System.out.println("Selecting items from Spice Garden...");
                selectSpiceGardenItems(con);
                System.out.println();

                System.out.println("Updating prices (Price <= 100 to 200)...");
                updatePrice(con);
                System.out.println();

                System.out.println("Deleting items starting with 'P'...");
                deleteItems(con);
                System.out.println();

                System.out.println("FINAL TABLE DATA AFTER ALL OPERATIONS");
                selectAllItems(con);
                System.out.println("\n Program completed successfully!\n");
            }

        } catch (Exception e) {
            System.err.println("ERROR: An unexpected error occurred!");
            System.err.println(e.getMessage());
        }
    }
}
