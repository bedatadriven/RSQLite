package org.renjin.cran.RSQLite;

import org.renjin.eval.EvalException;
import org.renjin.sexp.AtomicVector;
import org.renjin.sexp.IntVector;
import org.renjin.sexp.ListVector;
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

import static org.renjin.JDBC.JDBCUtils.columnInfo;
import static org.renjin.JDBC.JDBCUtils.fetch;
import static org.renjin.JDBC.JDBCUtils.hasCompleted;

/**
 *
 */
public class RSQLite {


    public static Connection RSQLite_rsqlite_connect(final String path, final boolean allowExt, final int flags, final String vfs) throws SQLException, ClassNotFoundException {
        String prefix = "jdbc:sqlite:";
        String filePref = "file://";
        String cleanPath = path.startsWith(filePref) ? path.substring(7) : path;
        final String fullPath = path.startsWith(prefix) ? path : prefix.concat(cleanPath);

        Properties pragmaTable = new Properties();
        pragmaTable.setProperty("enable_load_extension", String.valueOf(allowExt));
        pragmaTable.setProperty("open_mode", String.valueOf(flags));
        pragmaTable.setProperty("temp_store_directory", String.format("\'%s\'", new Object[]{vfs}));
        pragmaTable.setProperty("date_string_format", "yyyy-MM-dd HH:mm:ss.SSS");
        pragmaTable.setProperty("transaction_mode", "DEFFERED");
        pragmaTable.setProperty("date_class","INTEGER");
        pragmaTable.setProperty("date_precision", "MILLISECONDS");
        pragmaTable.setProperty("busy_timeout", "3000");
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(fullPath, pragmaTable);
        } catch (Exception e) {
            throw new EvalException(e);
        }
        return connection;
    }

    public static void RSQLite_rsqlite_disconnect(Connection connection) throws SQLException {
        connection.close();
    }

    private static ArrayList<String> listTables (Connection from) {
        ArrayList<String> dbTables = new ArrayList<>();
        ResultSet resultSet = null;
        try {
            DatabaseMetaData dbMetaData = from.getMetaData();
            String types[] = { "TABLE" };
            resultSet = dbMetaData.getTables(null, null, "", types);
            while (resultSet.next()) {
                dbTables.add(resultSet.getString("TABLE_NAME"));
            }
        } catch (SQLException e) {
            throw new EvalException(e);
        }
        finally {
            if( resultSet!=null ) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    throw new EvalException(e);
                }
            }
        }
        return dbTables;
    }

    public static void RSQLite_rsqlite_copy_database(Connection from, Connection to) throws SQLException {
        ArrayList<String> dbTables = listTables(from);
        String fromPath = from.getMetaData().getURL();
        String prefix = "jdbc:sqlite:";
        String cleanPath = fromPath.startsWith(prefix) ? fromPath.substring(12) : fromPath;
        // attach new database so you can copy columns over
        String sqlAttachDb = "ATTACH DATABASE \"" + cleanPath + "\" AS fromDB";
        to.prepareStatement(sqlAttachDb).execute();
        for (String table : dbTables) {
            String sqlInsertTable = "INSERT INTO " + table + " SELECT * FROM fromDB." + table;
            to.prepareStatement(sqlInsertTable).execute();
        }
    }

    public static boolean RSQLite_rsqlite_connection_valid(String conn) {
        return JDBC.isValidURL(conn);
    }

    public static boolean RSQLite_rsqlite_import_file(SEXP con, String name, SEXP value, SEXP sep, SEXP eol, boolean skip) {
        throw new EvalException("TODO: RSQLite_rsqlite_import_file");
    }

    public static ResultSet RSQLite_rsqlite_send_query(String conn, String sql) throws SQLException, ClassNotFoundException {
        ResultSet result;
        Connection connection = RSQLite_rsqlite_connect(conn, true, 70, "");
        Statement statement = connection.createStatement();
        try {
            result = statement.executeQuery(sql);
            return result;
        } catch (SQLException e) {
        }
        return null;
    }

    public static SEXP RSQLite_rsqlite_send_query(Connection connection, String sql) throws SQLException, ClassNotFoundException {
        ResultSet result;
        Statement statement = connection.createStatement();
        if (statement.execute(sql)) {
            // This means that excute returns a ResultSet object
            result = statement.getResultSet();
            return new ExternalPtr(result);
        } else {
            EmptyResultSet emptyResultSet = new EmptyResultSet();
            return new ExternalPtr(emptyResultSet);
        }
    }

    public static void RSQLite_rsqlite_clear_result(ResultSet result) throws SQLException {
        try {
            result.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        throw new EvalException("TODO: RSQLite_rsqlite_clear_result");
    }

    public static ListVector RSQLite_rsqlite_fetch(ResultSet result, AtomicVector n) throws SQLException {
        return fetch(result, n.getElementAsInt(0));
    }

    public static IntVector RSQLite_rsqlite_find_params(ResultSet result, SEXP param_names) {
        throw new EvalException("TODO: RSQLite_rsqlite_find_params");
    }

    public static void RSQLite_rsqlite_bind_rows(SEXP res, SEXP params) {
        throw new EvalException("TODO: RSQLite_rsqlite_bind_rows");
    }

    public static boolean RSQLite_rsqlite_has_completed(ResultSet res) {
        return hasCompleted(res);
    }

    public static int RSQLite_rsqlite_row_count(ResultSet result) throws SQLException {
//        Statement statement = result.getStatement();
//        return statement.getMaxRows();
        throw new EvalException("TODO: RSQLite_rsqlite_row_count");
    }

    public static int RSQLite_rsqlite_rows_affected(ResultSet resultSet) throws SQLException {
        Statement statement = resultSet.getStatement();
        if (statement == null) {
            return 0;
        } else {
            return statement.getUpdateCount();
        }
    }

    public static ListVector RSQLite_rsqlite_column_info(ResultSet res) {
        return columnInfo(res);
    }

    public static boolean RSQLite_rsqlite_result_valid(ResultSet result) {
        throw new EvalException("TODO: RSQLite_rsqlite_result_valid");
    }

    public static String RSQLite_rsqliteVersion() {
        return  SQLiteJDBCLoader.getVersion();
    }

    public static void RSQLite_init_logging( SEXP log_level) {
        throw new EvalException("TODO: RSQLite_init_logging");
    }
}
