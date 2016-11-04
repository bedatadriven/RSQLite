package org.renjin.cran.RSQLite;

import org.renjin.JDBC.columns.*;
import org.renjin.eval.EvalException;
import org.renjin.sexp.*;

import java.io.File;
import java.nio.file.Files;
import java.sql.*;
import java.sql.Date;
import java.util.*;

import org.renjin.sexp.Vector;
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
        File dbFile = new File(cleanPath);
        String fullPath;
        if (dbFile.isDirectory()) {
            fullPath = prefix.concat("memory:");
        } else {
            fullPath = path.startsWith(prefix) ? path : prefix.concat(cleanPath);
        }

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
        String sqlDetachDb = "DETACH DATABASE fromDB";
        to.prepareStatement(sqlDetachDb).execute();
    }

    public static boolean RSQLite_rsqlite_connection_valid(String conn) {
        return JDBC.isValidURL(conn);
    }

    public static boolean RSQLite_rsqlite_import_file(SEXP con, String name, SEXP value, SEXP sep, SEXP eol, boolean skip) {
        throw new EvalException("TODO: RSQLite_rsqlite_import_file");
    }

    public static SEXP RSQLite_rsqlite_send_query(Connection connection, String sql) throws SQLException, ClassNotFoundException {
        if (sql.contains("?") || sql.contains(":")) {
            // Use preparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ExternalPtr result = new ExternalPtr(preparedStatement);
            return result;
        } else {
            // use Statement
            Statement statement = connection.createStatement();
            ResultSet resultSet = null;
            if (statement.execute(sql)) {
                resultSet = statement.executeQuery(sql);
            } else {
                resultSet = new EmptyResultSet();
            }
            ExternalPtr result = new ExternalPtr(resultSet);
            return result;
        }
    }
            int index = n.getElementAsInt(0);
            ListVector result = fetch(emptyResultSet, index);
            return result;
        }

    }

    public static IntVector RSQLite_rsqlite_find_params(ResultSet resultSet, Vector param_names) throws SQLException {
        int paramLength = param_names.length();
        IntArrayVector.Builder params = new IntArrayVector.Builder();
        for (int j = 0; j < paramLength; j++) {
            int position = resultSet.findColumn(param_names.getElementAsString(j));
            int fixedPosition = position == 0 ? IntVector.NA : position;
            params.add(fixedPosition);
        }
        return params.build();
    }

  public static void RSQLite_rsqlite_bind_rows(PreparedStatement preparedStatement, ListVector params) throws SQLException {
        if (params.length() >= 0) {
            for (int i = 0; i < params.length(); ++i) {
                if(params.getElementAsObject(i) instanceof String) {
                    preparedStatement.setString(i+1, params.getElementAsString(i));
                } else if(params.getElementAsObject(i) instanceof Integer) {
                    preparedStatement.setInt(i+1, params.getElementAsInt(i));
                } else if(params.getElementAsObject(i) instanceof Double) {
                    preparedStatement.setDouble(i+1, params.getElementAsDouble(i));
                } else if(params.getElementAsObject(i) instanceof Logical) {
                    preparedStatement.setObject(i+1, params.getElementAsLogical(i));
                } else {
                    preparedStatement.setObject(i+1, params.getElementAsObject(i));
                }
            }
        }
    }

    public static boolean RSQLite_rsqlite_has_completed(PreparedStatement preparedStatement) throws SQLException {
        if (preparedStatement.execute()) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return hasCompleted(resultSet);
        } else {
            return true;
        }
    }

    public static int RSQLite_rsqlite_row_count(PreparedStatement preparedStatement) throws SQLException {
        if (preparedStatement.execute()) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.getFetchSize();
        } else {
            return 0;
        }
    }

    public static int RSQLite_rsqlite_rows_affected(PreparedStatement preparedStatement) throws SQLException {
        Statement statement;
        if (preparedStatement.execute()) {
            ResultSet resultSet = preparedStatement.executeQuery();
            statement = resultSet.getStatement();
            int result = statement.getUpdateCount();
            return result;
        } else {
            return 0;
        }
    }

    public static ListVector RSQLite_rsqlite_column_info(PreparedStatement preparedStatement) throws SQLException {
        if (preparedStatement.execute()) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return columnInfo(resultSet);
        } else {
            return ListVector.EMPTY;
        }
    }

    public static boolean RSQLite_rsqlite_result_valid(PreparedStatement preparedStatement) throws SQLException {
        if (preparedStatement.execute()) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet != null;
        } else {
            return true;
        }
    }

    public static String RSQLite_rsqliteVersion() {
        return  SQLiteJDBCLoader.getVersion();
    }

    public static void RSQLite_init_logging( SEXP log_level) {
        throw new EvalException("TODO: RSQLite_init_logging");
    }
}
