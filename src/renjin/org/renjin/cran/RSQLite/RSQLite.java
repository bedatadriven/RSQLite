package org.renjin.cran.RSQLite;

import org.renjin.eval.EvalException;
import org.renjin.sexp.IntVector;
import org.renjin.sexp.SEXP;

import java.io.File;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.sqlite.*;

/**
 *
 */
public class RSQLite {


    public static Connection RSQLite_rsqlite_connect(String path, boolean allowExt, int flags, String vfs) throws SQLException, ClassNotFoundException {
        String prefix = "jdbc:sqlite:";
        String filePref = "file://";
        String cleanPath = path.startsWith(filePref) ? path.substring(7) : path;
        String fullPath = path.startsWith(prefix) ? path : prefix.concat(cleanPath);

        System.out.println(fullPath);
        System.out.println(vfs);

        Properties pragmaTable = new Properties();
        pragmaTable.setProperty("open_mode", String.valueOf(flags));
        pragmaTable.setProperty("enable_load_extension", String.valueOf(allowExt));
        pragmaTable.setProperty("temp_store_directory", String.format("\'%s\'", new Object[]{vfs}));
        pragmaTable.setProperty("date_string_format", "yyyy-MM-dd HH:mm:ss.SSS");
        pragmaTable.setProperty("busy_timeout", "3000");
        pragmaTable.setProperty("transaction_mode", "DEFFERED");
        pragmaTable.setProperty("date_class","INTEGER");
        pragmaTable.setProperty("date_precision", "MILLISECONDS");
        pragmaTable.setProperty("busy_timeout", "3000");

        Class.forName("org.sqlite.JDBC");
        Connection connection = DriverManager.getConnection(fullPath, pragmaTable);
        return connection;
    }

    public static void RSQLite_rsqlite_disconnect(Connection connection) throws SQLException {
        connection.close();
    }

    public static void RSQLite_rsqlite_copy_database(String from, String to) {
        new ExtendedCommand.BackupCommand(from, to);
    }

    public static boolean RSQLite_rsqlite_connection_valid(String connection) {
        return JDBC.isValidURL(connection);
    }

    public static boolean RSQLite_rsqlite_import_file(SEXP con, String name, SEXP value, SEXP sep, SEXP eol, boolean skip) {
        throw new EvalException("TODO: rsqlite_import_file");
    }

    public static ResultSet RSQLite_rsqlite_send_query(Connection connection, String sql) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        return result;
    }

    public static void RSQLite_rsqlite_clear_result(ResultSet result) throws SQLException {
        try {
            result.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet RSQLite_rsqlite_fetch(ResultSet result, int n) throws SQLException {
        Statement statement = result.getStatement();
        statement.setFetchSize(n);
        ResultSet updatedResult = statement.getResultSet();
        return updatedResult;
    }

    public static IntVector RSQLite_rsqlite_find_params(ResultSet result, SEXP param_names) {
        throw new EvalException("TODO: rsqlite_find_params");
    }

    public static void RSQLite_rsqlite_bind_rows(SEXP res, SEXP params) {
        throw new EvalException("TODO: rsqlite_bind_rows");
    }

    public static boolean RSQLite_rsqlite_has_completed(SEXP res) {
        throw new EvalException("TODO: rsqlite_has_completed");
    }

    public static int RSQLite_rsqlite_row_count(ResultSet result) throws SQLException {
        Statement statement = result.getStatement();
        return statement.getMaxRows();
    }

    public static int RSQLite_rsqlite_rows_affected(SEXP res) {
        throw new EvalException("TODO: ____");
    }

    public static SEXP RSQLite_rsqlite_column_info(SEXP res) {
        throw new EvalException("TODO: rsqlite_column_info");
    }

    public static boolean RSQLite_rsqlite_result_valid(ResultSet result) {
        throw new EvalException("TODO: rsqlite_result_valid");
    }

    public static String RSQLite_rsqliteVersion() {
        return  SQLiteJDBCLoader.getVersion();
    }

    public static void RSQLite_init_logging( SEXP log_level) {
        throw new EvalException("TODO: init_logging");
    }
}
