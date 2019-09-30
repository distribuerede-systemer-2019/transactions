import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;

public class ResetDatabase {

    private static String getWorkingDir() {
        return System.getProperty("user.dir");
    }

    private static Connection getConnection(Boolean useDefaultDb) {
        try {
            if (useDefaultDb) {
                return DriverManager.getConnection(
                        "jdbc:mysql://"
                                + System.getenv("DATABASE_HOST") + ":"
                                + System.getenv("DATABASE_PORT") + "/"
                                + System.getenv("DATABASE_NAME") + "?useSSL=false&serverTimezone=GMT",
                        System.getenv("DATABASE_USER"),
                        System.getenv("DATABASE_PASSWORD"));
            } else {
                return DriverManager.getConnection(
                        "jdbc:mysql://"
                                + System.getenv("DATABASE_HOST") + ":"
                                + System.getenv("DATABASE_PORT") + "/?useSSL=false&serverTimezone=GMT",
                        System.getenv("DATABASE_USER"),
                        System.getenv("DATABASE_PASSWORD"));


            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void executeSql(String sqlFilePath) {
        try (InputStreamReader file = new InputStreamReader(new FileInputStream(sqlFilePath))) {
            ScriptRunner runner = new ScriptRunner(getConnection(false));
            runner.runScript(file);
            runner.closeConnection();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void deleteDatabase() {
        try {
            PreparedStatement deleteStatement = getConnection(false)
                    .prepareStatement("DROP database " + System.getenv("DATABASE_NAME"));
            deleteStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createDatabase() {
        try {
            PreparedStatement createStatement = getConnection(false)
                    .prepareStatement("CREATE database " + System.getenv("DATABASE_NAME"));
            createStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //Exercise with transaction
    private static void insertBooks() {
        try {

            PreparedStatement psInsert = getConnection(true).prepareStatement(SQL_INSERT);
            PreparedStatement psUpdate = getConnection(true).prepareStatement(SQL_UPDATE);

            //In exercise, all code related to transaction will not be shown.
            //Disable auto commit (per default 'true')

            getConnection(true).setAutoCommit(false);

            psInsert.setString(1, "Fundamentals of Corporate Finance");
            psInsert.setBigDecimal(2, new BigDecimal(100));
            psInsert.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            psInsert.execute();

            psInsert.setString(1, "Distributed System");
            psInsert.setBigDecimal(2, new BigDecimal(200));
            psInsert.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            psInsert.execute();

            //This block throws the exception.
            psUpdate.setBigDecimal(1, new BigDecimal(999.99));
            psUpdate.setString(2, "The Great Book of IT Project Management");
            psUpdate.execute();


            //End transaction and commit changes.
            getConnection(true).commit();

            //Enable auto commit again.
            getConnection(true).setAutoCommit(true);
        }
        catch (SQLException e){
            try {
                getConnection(true).rollback();
            } catch (SQLException x) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        //Wipe database
        deleteDatabase();
        System.out.println("Deleted database ...");

        //Create new
        createDatabase();
        System.out.println("Create new database ...");


        //Run script
        String sqlFilePath = getWorkingDir() + "/src/sql.sql";
        executeSql(sqlFilePath);


        insertBooks();
        System.out.println("Sql script done!");
    }

    private static final String SQL_INSERT = "INSERT INTO books (name, price, created_date) VALUES (?,?,?)";

    private static final String SQL_UPDATE = "UPDATE books SET price=? WHERE name=?";

    // Testing elements for SQL database
    private static final String SQL_TABLE_CREATE = "CREATE TABLE employee"
            + "("
            + " id serial,"
            + " name varchar(100) NOT NULL,"
            + " price numeric(15, 2) NOT NULL,"
            + " created_date TIMESTAMP NOT NULL,"
            + " PRIMARY KEY (ID)"
            + ")";
    // <//>
    private static final String SQL_TABLE_DROP = "DROP TABLE EMPLOYEE";

}


