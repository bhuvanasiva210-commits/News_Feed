package newsfeed;

import java.sql.*;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.Scanner;

public class NewsFeedMain {

    public static void main(String[] args) {
        // Load database configuration from db.properties
        Properties props = new Properties();
        try (InputStream input = NewsFeedMain.class.getResourceAsStream("/db.properties")) {
            if (input == null) {
                throw new RuntimeException("db.properties not found in resources folder!");
            }
            props.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load db.properties", e);
        }

        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String pass = props.getProperty("db.pass");

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             Scanner sc = new Scanner(System.in)) {

            System.out.println("✅ Connected to MySQL successfully!\n");

            while (true) {
                System.out.println("==== NEWS FEED MENU ====");
                System.out.println("1. Add New Article");
                System.out.println("2. Update Article Content");
                System.out.println("3. Delete Article");
                System.out.println("4. View All Articles");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                sc.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                        insertArticle(conn, sc);
                        break;
                    case 2:
                        updateArticle(conn, sc);
                        break;
                    case 3:
                        deleteArticle(conn, sc);
                        break;
                    case 4:
                        viewArticles(conn);
                        break;
                    case 5:
                        System.out.println("Exiting... 👋");
                        return;
                    default:
                        System.out.println("❌ Invalid choice. Try again.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ---------- INSERT ----------
    private static void insertArticle(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter Title: ");
        String title = sc.nextLine();
        System.out.print("Enter Category (e.g., Tech, Sports): ");
        String category = sc.nextLine();
        System.out.print("Enter Date (YYYY-MM-DD): ");
        String date = sc.nextLine();
        System.out.print("Enter Content: ");
        String content = sc.nextLine();

        String sql = "INSERT INTO articles (title, category, content, date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.setString(2, category);
            ps.setString(3, content);
            ps.setDate(4, Date.valueOf(date));
            int rows = ps.executeUpdate();
            System.out.println("✅ Article added successfully (" + rows + " row).");
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("⚠️ Article with the same title already exists!");
        }
    }

    // ---------- UPDATE ----------
    private static void updateArticle(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter Article Title to update: ");
        String title = sc.nextLine();
        System.out.print("Enter New Content: ");
        String newContent = sc.nextLine();

        String sql = "UPDATE articles SET content = ? WHERE title = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newContent);
            ps.setString(2, title);
            int rows = ps.executeUpdate();
            if (rows > 0)
                System.out.println("✅ Article updated successfully.");
            else
                System.out.println("⚠️ No article found with that title.");
        }
    }

    // ---------- DELETE ----------
    private static void deleteArticle(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter Article Title to delete: ");
        String title = sc.nextLine();

        String sql = "DELETE FROM articles WHERE title = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, title);
            int rows = ps.executeUpdate();
            if (rows > 0)
                System.out.println("✅ Article deleted successfully.");
            else
                System.out.println("⚠️ No article found with that title.");
        }
    }

    // ---------- SELECT ----------
    private static void viewArticles(Connection conn) throws SQLException {
        String sql = "SELECT id, title, category, date, content FROM articles ORDER BY date DESC";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n------ NEWS FEED ------");
            while (rs.next()) {
                System.out.printf("[%d] %s | %s | %s\n%s\n\n",
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("category"),
                        rs.getDate("date"),
                        rs.getString("content"));
            }
            System.out.println("------------------------\n");
        }
    }
}
