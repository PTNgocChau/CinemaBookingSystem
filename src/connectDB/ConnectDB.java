package connectDB;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectDB {

    private static Connection con = null;
    private static ConnectDB instance = new ConnectDB();

    public static ConnectDB getInstance() {
        return instance;
    }

    // =========================
    // TỰ ĐỘNG CONNECT NẾU NULL
    // =========================
    public static Connection getConnection() {

        try {
            if (con == null || con.isClosed()) {
                getInstance().connect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return con;
    }

    // =========================
    // CONNECT
    // =========================
    public void connect() {

        String url =
            "jdbc:sqlserver://localhost:1433;"
          + "databaseName=QuanLyRapPhim;"
          + "encrypt=true;"
          + "trustServerCertificate=true";

        String user = "sa";
        String pass = "sapassword";

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            con = DriverManager.getConnection(url, user, pass);

            System.out.println("Kết nối DB thành công!");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Kết nối DB thất bại!");
        }
    }

    // =========================
    // DISCONNECT
    // =========================
    public void disconnect() {

        try {
            if (con != null && !con.isClosed()) {
                con.close();
                con = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}