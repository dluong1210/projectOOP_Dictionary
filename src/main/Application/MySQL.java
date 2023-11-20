package Application;

import java.sql.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/** Singleton class. */
public class MySQL {
    private static final String url = "jdbc:mysql://127.0.0.1:3307/en-vi?user=admin";
//    private static final String user = "root";
//    private static final String password = "";
    private static Connection connection;
    private static MySQL instance;

    public MySQL() {
    }

    public static MySQL getInstance() {
        if (instance == null) {
            instance = new MySQL();
            try {
                connection = DriverManager.getConnection(url);
            } catch (SQLException e) {
                System.out.println(e.getErrorCode());
            }
        }
        return instance;
    }

    public static String selectFromDB(String word) {
        String text = null;
        try {
            getInstance();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT definition FROM dictionary "
                                                    + "WHERE target = \"" + word + "\"");

            if (rs.next()) {
                text = rs.getString("definition").replace("+", "  --->  ")
                                                            .replace("*", "<br>*")
                                                            .replace("=", "Ex: ")
                                                            .replace("<I><Q>","");
            }

            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return text;
    }

    public static String htmlSelectFromDB(String word) {
        String text;
        if (word.isEmpty()) text = "Search something!";
        else {
            text = selectFromDB(word);
            if (text == null) text = "<b>'" + word + "'</b> not exist in dictionary !";
        }

        return htmlization(text);
    }

    public static String htmlization(String text) {
        return  "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <style>\n" +
                "        body, html {\n" +
                "            font-size: 14px;\n" +
                "            font-family: \"Verdana\", Sans-serif;\n" +
                "            background-color: #1C1A24;\n" +
                "            color: #E8E8E8;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                text +
                "</body>\n" +
                "</html>";
    }

    public static List<String> searchFromDB(String word) {
        List<String> wordFound = new ArrayList<>();
        try {
            getInstance();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT target FROM dictionary "
                    + "WHERE target like \"" + word + "%\"");

            while (rs.next()) {
                wordFound.add(rs.getString("target"));
            }
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return wordFound;
    }

    public static void deleteFromDB(String word) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("DELETE FROM dictionary WHERE target = \"" + word + "\"");

        statement.close();
        System.out.println("Delete successfully word: " + word);

        deleteBookmark(word);
    }

    public static void insertIntoDB(String word, String definition) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO dictionary VALUES (\"" + word + "\", \"" + definition + "\")");

        statement.close();
        System.out.println("Insert successfully word: " + word);
    }

    public static void updateDB(String word, String newDefinition) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("UPDATE dictionary SET definition = \"" + newDefinition + "\" WHERE target = \"" + word + "\"");

        statement.close();
        System.out.println("Update successfully word: " + word);
    }

    public static boolean checkBookmark(String word) {
        boolean check = false;
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT word FROM bookmark WHERE word = \"" + word + "\"");
            check = rs.next();

            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return check;
    }

    public static void addBookmark(String word) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO bookmark VALUES(\"" + word + "\")");

        statement.close();
        System.out.println("Insert into bookmark successfully word: " + word);
    }

    public static void deleteBookmark(String word) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM bookmark WHERE word = \"" + word + "\"");

            statement.close();
            System.out.println("Delete from bookmark successfully word: " + word);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<String> searchFromBookmark(String word) {
        List<String> wordFound = new ArrayList<>();
        try {
            getInstance();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT word FROM bookmark WHERE word LIKE \"" + word + "%\"");

            while (rs.next()) {
                wordFound.add(rs.getString("word"));
            }
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return wordFound;
    }

    public static String htmlSelectFromBookmark(String word) {
        String text;
        if (word == null || word.isEmpty()) {
            text = "Choose a Bookmark!";
        } else if (!checkBookmark(word)) {
            text = "<b>'" + word + "'</b> has not been bookmarked yet";
        } else {
            text = selectFromDB(word);
        }

        return htmlization(text);
    }

    public static void main(String[] args) {
        // Thông tin kết nối cơ sở dữ liệu MySQL
        System.out.println("Connecting to database...");
        try {
            // Tạo kết nối
            getInstance();
            System.out.println("Connected database successfully...");

            // Thực hiện các truy vấn SQL ở đây
//            System.out.println(selectFromDB("inactive"));
//            System.out.println(selectFromDB("he"));

//            for (String s : getAllFromBookmark()) {
//                System.out.println(s);
//            }
//            deleteBookmark("abstract");
            for (String s : searchFromBookmark("h")) {
                System.out.println(s);
            }

            // Đóng kết nối khi hoàn thành
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
