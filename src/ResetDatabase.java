import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;

public class ResetDatabase {
    private static Connection dbCon;

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


//    // Exercise with transaction
//    private static void insertBooks() {
//        try {
//            if (dbCon == null) {
//                dbCon = getConnection(true);
//            }
//
//            PreparedStatement psInsert = dbCon.prepareStatement(SQL_INSERT);
//            PreparedStatement psUpdate = dbCon.prepareStatement(SQL_UPDATE);
//
//            //In exercise, all code related to transaction will not be shown.
//            //Disable auto commit (per default 'true')
//
//            dbCon.setAutoCommit(false);
//
//            psInsert.setString(1, "Fundamentals of Corporate Finance");
//            psInsert.setBigDecimal(2, new BigDecimal(100));
//            psInsert.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
//            psInsert.execute();
//
//            psInsert.setString(1, "Distributed System");
//            psInsert.setBigDecimal(2, new BigDecimal(200));
//            psInsert.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
//            psInsert.execute();
//
//            //This block throws the exception.
//            psUpdate.setBigDecimal(1, new BigDecimal(999.99));
//            psUpdate.setString(2, "Fundamentals of Corporate Finance");
//            psUpdate.execute();
//
//
//            //End transaction and commit changes.
//            dbCon.commit();
//
//            //Enable auto commit again.
//            dbCon.setAutoCommit(true);
//        }
//        catch (SQLException e){
//            try {
//                System.out.println("rollback");
//                dbCon.rollback();
//            } catch (SQLException x) {
//                e.printStackTrace();
//            }
//        }
//    }

    private static void assignCourse() {
        try {
            if (dbCon == null) {
                dbCon = getConnection(true);
            }

            PreparedStatement psInsertCourse = dbCon.prepareStatement(SQL_INSERT_COURSE);
            PreparedStatement psInsertStudent = dbCon.prepareStatement(SQL_INSERT_STUDENT);
            PreparedStatement psInsertStudentCourse = dbCon.prepareStatement(SQL_INSERT_STUDENTCOURSE);

            String studentName = "larslarsen@student.cbs.dk";

            dbCon.setAutoCommit(false);
            psInsertStudent.setString(1, studentName);
            psInsertStudent.setBoolean(2, true);
            psInsertStudent.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            psInsertStudent.setString(4, "1234abcd");
            psInsertStudent.execute();

            psInsertStudentCourse.setInt(1, 7);
            psInsertStudentCourse.setInt(2, 7);
            psInsertStudentCourse.execute();

            psInsertCourse.setString(1, null);
            psInsertCourse.execute();

            // If exercises where made correctly last time, 7 should be the student ID of larslarsen@student.cbs.dk
            // and 7 is the course ID

            dbCon.commit();
            dbCon.setAutoCommit(true);


        } catch (SQLException e) {
            try {
                dbCon.rollback();
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

        //Method for transaction example
        assignCourse();

        System.out.println("Sql script done!");
    }

    private static final String SQL_INSERT_COURSE = "INSERT INTO courses (name) VALUES (?)";

    private static final String SQL_INSERT_STUDENT = "INSERT INTO students (email, active, created, password) VALUES (?, ?, ?, ?)";

    private static final String SQL_INSERT_STUDENTCOURSE = "INSERT INTO studentCourses (courseid, studentid) VALUES (?, ?)";

}


