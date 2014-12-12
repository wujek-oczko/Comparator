import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by marcinja on 03/12/2014.
 */
public class DbConnector {
    private final static String DB_URL = "jdbc:mysql://localhost:3306/mydb";
    private final static String USER = "root";
    private final static String PASS = "root";

    public static void main (String args[]){
        selectFromModelImages();
    }

    public static void insertSqlStatement(String statement) {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            System.out.println("Connection established");

            stmt = conn.createStatement();
            Integer rs = stmt.executeUpdate(statement);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    // ignore
                }
            }
        }
    }

    public static List<Jpeg> selectFromModelImages(){
        List<Jpeg> jpegs = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            System.out.println("Connection established");

            stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery("SELECT image_name, full_path FROM model_images");
            ResultSet rs = stmt.executeQuery("SELECT m.image_name, m.class_name, m.test_name, m.user_login, br.browser_shortcut, m.timestamp, m.status, m.full_path, m.build_id, e.env_shortcut, r.run_number " +
                    "FROM run r JOIN model_images m ON r.id_run = m.run_id_run \n" +
                               "JOIN environment e ON m.environment_id_environment = e.id_environment\n" +
                               "JOIN browser br ON m.browser_id = br.id_browser");
            while (rs.next()){

                System.out.println(rs.getString("image_name"));
                System.out.println(rs.getString("class_name"));
                System.out.println(rs.getString("test_name"));
                System.out.println(rs.getString("user_login"));
                System.out.println(rs.getString("browser_shortcut"));
                System.out.println(rs.getString("timestamp"));
                System.out.println(rs.getString("status"));
                System.out.println(rs.getString("full_path"));
                System.out.println(rs.getString("build_id"));
                System.out.println(rs.getString("env_shortcut"));
                System.out.println(rs.getString("run_number"));

                Jpeg helper = new Jpeg();
                helper.setImage_name(rs.getString("image_name"));
                helper.setClass_name(rs.getString("class_name"));
                helper.setTest_name(rs.getString("test_name"));
                helper.setUser_login(rs.getString("user_login"));
                helper.setBrowser_shortcut(rs.getString("browser_shortcut"));
                helper.setTimestamp(rs.getString("timestamp"));
                helper.setStatus(rs.getString("status"));
                helper.setFull_path(rs.getString("full_path"));
                helper.setBuild_ID(rs.getString("build_id"));
                helper.setEnv_ID_Env(rs.getString("env_shortcut"));
                helper.setRun_ID_Run(rs.getString("run_number"));

//                FileReader.ImageAndPath helper = new FileReader.ImageAndPath(rs.getString("image_name"), rs.getString("full_path"));
                jpegs.add(helper);
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    // ignore
                }
            }
        }

        return jpegs;
    }

    public static void insertSqlStatements(List<String> statements) {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            System.out.println("Connection established");

            stmt = conn.createStatement();
            for (String statement : statements){
                Integer rs = stmt.executeUpdate(statement);
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    // ignore
                }
            }
        }
    }

}
