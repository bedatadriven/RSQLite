package org.renjin.RSQLite;

import org.renjin.eval.EvalException;
import org.renjin.sexp.IntVector;
import org.renjin.sexp.Null;
import org.renjin.sexp.SEXP;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 */
public class RSQLite {


    public static SEXP RSQLite_rsqlite_connect(String path, boolean allowExt, int flags, String vfs) {
        String prefix = "jdbc:sqlite:";
        String fullPath;
        fullPath = path.startsWith(prefix) ? path : prefix.concat(path);

        // SQLiteConfig config = new SQLiteConfig();
        // config.setReadOnly(true);
        // config.setSharedCache(true);
        // config.recursiveTriggers(true);

        Connection connection = DriverManager.getConnection(fullPath);
        return (SEXP) connection;
    }

    public static void RSQLite_rsqlite_disconnect(SEXP con) {
        throw new EvalException("TODO: RSQLite_rsqlite_disconnect");
    }

    public static void RSQLite_rsqlite_copy_database(String from, String to) {
        throw new EvalException("TODO: RSQLite_rsqlite_copy_database");
    }

    public static boolean rsqlite_connection_valid(SEXP con) {
        throw new EvalException("TODO: rsqlite_connection_valid");
    }

    public static boolean rsqlite_import_file(SEXP con, String name, SEXP value, SEXP sep, SEXP eol, boolean skip) {
        throw new EvalException("TODO: rsqlite_import_file");
    }

    public static SEXP rsqlite_send_query(SEXP con, SEXP sql) {
        throw new EvalException("TODO: rsqlite_send_query");
    }

    public static void rsqlite_clear_result(SEXP res) {
        throw new EvalException("TODO: rsqlite_clear_result");
    }

    public static SEXP rsqlite_fetch(SEXP res, int n) {
        throw new EvalException("TODO: rsqlite_fetch");
    }

    public static IntVector rsqlite_find_params(SEXP res, SEXP param_names) {
        throw new EvalException("TODO: rsqlite_find_params");
    }

    public static void rsqlite_bind_rows(SEXP res, SEXP params) {
        throw new EvalException("TODO: rsqlite_bind_rows");
    }

    public static boolean rsqlite_has_completed(SEXP res) {
        throw new EvalException("TODO: rsqlite_has_completed");
    }

    public static int rsqlite_row_count(SEXP res) {
        throw new EvalException("TODO: rsqlite_row_count");
    }

    public static int rsqlite_rows_affected(SEXP res) {
        throw new EvalException("TODO: ____");
    }

    public static SEXP rsqlite_column_info(SEXP res) {
        throw new EvalException("TODO: rsqlite_column_info");
    }

    public static boolean rsqlite_result_valid(SEXP res) {
        throw new EvalException("TODO: rsqlite_result_valid");
    }

    public static SEXP rsqliteVersion() {
//        #' RSQLite version
//        #'
//        #' @return A character vector containing header and library versions of
//        #'   RSQLite.
//        #' @export
        throw new EvalException("TODO: rsqliteVersion");
    }

    public static void init_logging( SEXP log_level) {
        throw new EvalException("TODO: init_logging");
    }
}
