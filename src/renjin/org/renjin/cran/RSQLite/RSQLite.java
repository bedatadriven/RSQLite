package org.renjin.cran.RSQLite;

import org.renjin.eval.EvalException;
import org.renjin.sexp.*;
import org.sqlite.JDBC;
import org.sqlite.SQLiteJDBCLoader;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

import static org.renjin.JDBC.JDBCUtils.*;

/**
 *
 */
public class RSQLite {


    public static Connection RSQLite_rsqlite_connect(final String path, final boolean allowExt, final int flags, final String vfs) throws SQLException, ClassNotFoundException {

        String prefix = "jdbc:sqlite:";
        String fullPath;
        String filePref = "file://";
        String cleanPath = path.startsWith(filePref) ? path.substring(7) : path;
        File dbFile = new File(cleanPath);
        if (path.equals(":memory:") || path.equals("") || path.equals("jdbc:sqlite:memory") || path.equals("jdbc:sqlite::memory") || dbFile.isDirectory()) {
            fullPath = prefix;
        } else {
            if (dbFile.isDirectory()) {
                throw new EvalException("Incorrect path to database.");
            } else {
                fullPath = path.startsWith(prefix) ? path : prefix.concat(cleanPath);
            }
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
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ExternalPtr result = new ExternalPtr(preparedStatement);
            return result;
    }

    public static void RSQLite_rsqlite_clear_result(Object obj) throws SQLException {
        if (obj instanceof EmptyResultSet) {
            ((EmptyResultSet)obj).close();
        } else if (obj instanceof ResultSet) {
            ((ResultSet)obj).close();
        }
    }

    public static ListVector RSQLite_rsqlite_fetch(Object object, Object n) throws SQLException, EvalException {
        long index;
        if (n instanceof Double) {
          index = ((Double) n).longValue();
        } else if (n instanceof Integer) {
            index = ((Integer) n).longValue();
        } else {
            index = (Long) n;
        }
        if (object instanceof PreparedStatement) {
            PreparedStatement preparedStatement = (PreparedStatement) object;
            if (preparedStatement.execute()) {
                ResultSet resultSet = preparedStatement.executeQuery();
                ListVector result = fetch(resultSet, index);
                return result;
            } else {
                return ListVector.EMPTY;
            }
        } else if (object instanceof EmptyResultSet) {
            return ListVector.EMPTY;
        } else if (object instanceof ResultSet) {
            ResultSet resultSet = (ResultSet) object;
            ListVector result = fetch(resultSet, index);
            return result;
        } else {
            return ListVector.EMPTY;
        }
    }

    public static IntVector RSQLite_rsqlite_find_params(PreparedStatement preparedStatement, ListVector param_names) throws SQLException {
        throw new EvalException("TODO: RSQLite_rsqlite_find_params");
    }

  public static void RSQLite_rsqlite_bind_rows(Object object, ListVector allParams) throws SQLException {
      if (object instanceof PreparedStatement) {

          PreparedStatement preparedStatement = (PreparedStatement) object;
          ParameterMetaData parameterMetaData = null;
          parameterMetaData = preparedStatement.getParameterMetaData();
          int statementParamCount = parameterMetaData.getParameterCount();
          int inputParamCount = allParams == null ? 0 : allParams.length();
          if (statementParamCount != inputParamCount) {
              throw new SQLException("query param count and update param count dont match");
          }
          if (allParams == ListVector.EMPTY || allParams == null) {
              return;
          }

          for (int i = 0; i < allParams.length();i++) {
              Object param = allParams.getElementAsObject(i);
              if (allParams.getElementAsObject(i) != null) {
                  if (param instanceof String) {
                      preparedStatement.setString(i + 1, allParams.getElementAsString(i));
                  } else if (param instanceof Double) {
                      preparedStatement.setDouble(i + 1, allParams.getElementAsDouble(i));
                  } else if (param instanceof Integer) {
                      preparedStatement.setInt(i + 1, allParams.getElementAsInt(i));
                  } else {
                      preparedStatement.setObject(i + 1, param);
                  }
              } else {
                  int sqlType = Types.VARCHAR;
                  try {
                      sqlType = parameterMetaData.getParameterType(i + 1);
                  } catch (SQLException e) {
                  }
                  preparedStatement.setNull(i + 1, sqlType);
              }
          }

      }
  }

    public static boolean RSQLite_rsqlite_has_completed(Object obj) throws SQLException {
        if (obj instanceof EmptyResultSet) {
            return hasCompleted((EmptyResultSet) obj );
        } else if (obj instanceof ResultSet) {
            return hasCompleted((ResultSet) obj);
        } else {
            return true;
        }
    }

    public static int RSQLite_rsqlite_row_count(Object object) throws SQLException {
        if (object instanceof PreparedStatement) {PreparedStatement preparedStatement = (PreparedStatement) object;
            if (preparedStatement.execute()) {
                ResultSet resultSet = preparedStatement.executeQuery();
                int result = resultSet.getFetchSize();
                return result;
            } else {
                return 0;
            }
        } else if (object instanceof ResultSet) {
            int result = ((ResultSet) object).getFetchSize();
            return result;
        } else {
            return 0;
        }
    }

    public static int RSQLite_rsqlite_rows_affected(Object obj) throws SQLException {
        if (obj instanceof PreparedStatement) {
            PreparedStatement preparedStatement = (PreparedStatement) obj;
            Statement statement;
            if (preparedStatement.execute()) {
                ResultSet resultSet = preparedStatement.executeQuery();
                statement = resultSet.getStatement();
                int result = statement.getUpdateCount();
                return result;
            } else {
                return 0;
            }
        } else if (obj instanceof EmptyResultSet) {
            return 0;
        } else if (obj instanceof ResultSet) {
            ResultSet resultSet = (ResultSet) obj;
            int result = resultSet.getStatement().getUpdateCount();
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

    public static boolean RSQLite_rsqlite_result_valid(Object obj) throws SQLException {
        if (obj != null) {
            return true;
        } else {
            return false;
        }
    }

    public static String RSQLite_rsqliteVersion() {
        return  SQLiteJDBCLoader.getVersion();
    }

    public static void RSQLite_init_logging( SEXP log_level) {
        throw new EvalException("TODO: RSQLite_init_logging");
    }
}