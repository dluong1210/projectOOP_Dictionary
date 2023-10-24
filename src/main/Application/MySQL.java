package Application;

import java.sql.*;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.sql.Connection;

/** Singleton class. */
public class MySQL {
    private static final String url = "jdbc:mysql://127.0.0.1:3307/en-vi";
    private static final String user = "root";
    private static final String password = "a12102004";
    private static Connection connection;

    private static MySQL instance;

    private MySQL() {
    }

    public static MySQL getInstance() throws SQLException {
        if (instance == null) {
            instance = new MySQL();
            connection = DriverManager.getConnection(url, user, password);
        }
        return instance;
    }

    public static String selectFromDB(String word) {
        String text = null;
        try {
            getInstance();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT definition FROM dictionary WHERE target = \"" + word + "\"");

            if (rs.next()) {
                String html = rs.getString("definition");
                Document doc = Jsoup.parse(html);

                Element q = doc.select("Q").first();
                if (q != null) {
                    text = q.html().replace("<br>=", "\n\t")
                            .replace("<br>", "\n")
                            .replace("+", " --->");
                }
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return text;
    }

    public static void deleteFromDB(String word) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("DELETE FROM dictionary WHERE word = \"" + word + "\"");

        statement.close();
        System.out.println("Delete successfully word: " + word);
    }

    public static void insertIntoDB(String word, String definition) {
    }

    public static void updateDB(String word, String newDefinition){
    }

    public static void main(String[] args) {
        // Thông tin kết nối cơ sở dữ liệu MySQL
        System.out.println("Connecting to database...");
        try {
            // Tạo kết nối
            getInstance();
            System.out.println("Connected database successfully...");

            // Thực hiện các truy vấn SQL ở đây
            System.out.println(selectFromDB("a"));

            // Đóng kết nối khi hoàn thành
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
