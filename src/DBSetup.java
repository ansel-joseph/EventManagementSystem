import java.sql.Connection;
import java.sql.Statement;

class DBSetup {
    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS participants (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                     "name TEXT NOT NULL," +
                     "email TEXT NOT NULL," +
                     "event TEXT NOT NULL," +
                     "ticketId INTEGER NOT NULL)";

        try (Connection conn = DBConnection.connect();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);

        } catch (Exception e) {
            System.out.println("Table error: " + e.getMessage());
        }
    }
}
