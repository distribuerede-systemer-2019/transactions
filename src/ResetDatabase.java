import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ResetDatabase {

    private static String getWorkingDir(){
        return System.getProperty("user.dir");
    }

    private static Connection getConnection(Boolean useDefaultDb) {
        try {
            if(useDefaultDb){
                return DriverManager.getConnection("jdbc:mysql://localhost:3306/dis?useSSL=false&serverTimezone=GMT", "root", "root");
            } else {
                return DriverManager.getConnection("jdbc:mysql://localhost:3306/?useSSL=false&serverTimezone=GMT", "root", "root");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static InputStreamReader getFile(String path) {
        try(InputStreamReader isr = new InputStreamReader(new FileInputStream(path))){
            return isr;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void executeSql(String sqlFilePath) {
        try(InputStreamReader file = new InputStreamReader(new FileInputStream(sqlFilePath))){
            ScriptRunner runner = new ScriptRunner(getConnection(true));
            runner.runScript(file);
            runner.closeConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void deleteDatabase(){
        try {
            PreparedStatement deleteStatement = getConnection(false).prepareStatement("DROP database dis");
            deleteStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createDatabase(){
        try {
            PreparedStatement createStatement = getConnection(false).prepareStatement("CREATE database dis");
            createStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
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
        System.out.println("Sql script done!");
    }
}
