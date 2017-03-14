package helpers;

import entity.Smartphone;

import java.sql.*;
import java.util.Calendar;
import java.util.LinkedList;

import static helpers.Utils.*;

public class Connect_db {
    private static final org.slf4j.Logger Log = org.slf4j.LoggerFactory.getLogger("DB");

    public static void writeToDB(LinkedList<Smartphone> smartphoneTopSalesLinkedList) {
        Smartphone smartphone;
        Connection connection = getConnection(user, password);
        for (Smartphone aSmartphoneTopSalesLinkedList : smartphoneTopSalesLinkedList) {
            smartphone = aSmartphoneTopSalesLinkedList;
            insertData(connection, smartphone.getName(), smartphone.getPrice());
        }
        closeConnection(connection);
    }

    public static void selectDataToReport() {
        Connection connection = getConnection(user, password);
        String query = "SELECT * FROM price_top";
        Statement st;
        try {
            st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("Name");
                String price = rs.getString("Price");
                Date dateCreated = rs.getDate("Created_at");

                writeToFile(id + " " + name + " " + price + " " + dateCreated + "\n");
            }
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection(connection);
    }

    private static Connection getConnection(String user, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        Log.info("MySQL JDBC Driver Registered!");
        Connection connection;
        try {
            connection = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/amadeus_test?autoReconnect=true&useSSL=false&" +
                                    "useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                            user, password);
        } catch (SQLException e) {
            Log.error("Connection Failed! Check output console");
            e.printStackTrace();
            return null;
        }
        if (connection == null) {
            Log.error("Failed to make connection!");
        }
        return connection;
    }

    public static void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertData(Connection connection, String name, String price) {
        Calendar calendar = Calendar.getInstance();
        java.sql.Timestamp createDate = new java.sql.Timestamp(calendar.getTime().getTime());

        String query = " insert into price_top (Name, Price, Created_at) values (?, ?, ?)";

        try {
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, name);
            preparedStmt.setString(2, price);
            preparedStmt.setTimestamp(3, createDate);
            preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}