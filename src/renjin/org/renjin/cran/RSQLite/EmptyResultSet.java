package org.renjin.cran.RSQLite;

import org.renjin.eval.EvalException;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.Map;

/**
 * Empty ResultSet
 */
public class EmptyResultSet implements ResultSet {
    private boolean open = true;
    private final Statement statement = null;

    @Override
    public boolean next() throws SQLException {
        return false;
    }

    @Override
    public void close() throws SQLException {
        this.open = false;

    }

    @Override
    public boolean wasNull() throws SQLException {
        return false;
    }

    @Override
    public String getString(int columnIndex) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getString (int)String");
    }

    @Override
    public boolean getBoolean(int columnIndex) throws SQLException {
        return false;
    }

    @Override
    public byte getByte(int columnIndex) throws SQLException {
        return 0;
    }

    @Override
    public short getShort(int columnIndex) throws SQLException {
        return 0;
    }

    @Override
    public int getInt(int columnIndex) throws SQLException {
        return 0;
    }

    @Override
    public long getLong(int columnIndex) throws SQLException {
        return 0;
    }

    @Override
    public float getFloat(int columnIndex) throws SQLException {
        return 0;
    }

    @Override
    public double getDouble(int columnIndex) throws SQLException {
        return 0;
    }

    @Override
    public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getBigDecimal (int, int)BigDecimal");
    }

    @Override
    public byte[] getBytes(int columnIndex) throws SQLException {
        return new byte[0];
    }

    @Override
    public Date getDate(int columnIndex) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getDate (int)Date");
    }

    @Override
    public Time getTime(int columnIndex) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getTime (int)Time");
    }

    @Override
    public Timestamp getTimestamp(int columnIndex) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getTimestamp (int)Timestamp");
    }

    @Override
    public InputStream getAsciiStream(int columnIndex) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getAsciiStream (int)InputStream");
    }

    @Override
    public InputStream getUnicodeStream(int columnIndex) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getUnicodeStream (int)InputStream");
    }

    @Override
    public InputStream getBinaryStream(int columnIndex) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getBinaryStream (int)InputStream");
    }

    @Override
    public String getString(String columnLabel) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getString (String)String");
    }

    @Override
    public boolean getBoolean(String columnLabel) throws SQLException {
        return false;
    }

    @Override
    public byte getByte(String columnLabel) throws SQLException {
        return 0;
    }

    @Override
    public short getShort(String columnLabel) throws SQLException {
        return 0;
    }

    @Override
    public int getInt(String columnLabel) throws SQLException {
        return 0;
    }

    @Override
    public long getLong(String columnLabel) throws SQLException {
        return 0;
    }

    @Override
    public float getFloat(String columnLabel) throws SQLException {
        return 0;
    }

    @Override
    public double getDouble(String columnLabel) throws SQLException {
        return 0;
    }

    @Override
    public BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getBigDecimal ()");
    }

    @Override
    public byte[] getBytes(String columnLabel) throws SQLException {
        return new byte[0];
    }

    @Override
    public Date getDate(String columnLabel) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getDate (S)Date");
    }

    @Override
    public Time getTime(String columnLabel) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getTime (S)Time");
    }

    @Override
    public Timestamp getTimestamp(String columnLabel) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getTimestamp (S)Ts");
    }

    @Override
    public InputStream getAsciiStream(String columnLabel) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getAsciiStream ()");
    }

    @Override
    public InputStream getUnicodeStream(String columnLabel) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getUnicodeStream ()");
    }

    @Override
    public InputStream getBinaryStream(String columnLabel) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getBinaryStream ()");
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getWarnings ()");
    }

    @Override
    public void clearWarnings() throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$clearWarnings ()");
    }

    @Override
    public String getCursorName() throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getCursorName ()");
    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        ResultSetMetaData meta = new ResultSetMetaData() {
            @Override
            public int getColumnCount() throws SQLException {
                return 0;
            }

            @Override
            public boolean isAutoIncrement(int column) throws SQLException {
                return false;
            }

            @Override
            public boolean isCaseSensitive(int column) throws SQLException {
                return false;
            }

            @Override
            public boolean isSearchable(int column) throws SQLException {
                return false;
            }

            @Override
            public boolean isCurrency(int column) throws SQLException {
                return false;
            }

            @Override
            public int isNullable(int column) throws SQLException {
                return 0;
            }

            @Override
            public boolean isSigned(int column) throws SQLException {
                return false;
            }

            @Override
            public int getColumnDisplaySize(int column) throws SQLException {
                return 0;
            }

            @Override
            public String getColumnLabel(int column) throws SQLException {
                return "";
            }

            @Override
            public String getColumnName(int column) throws SQLException {
                return "";
            }

            @Override
            public String getSchemaName(int column) throws SQLException {
                return "";
            }

            @Override
            public int getPrecision(int column) throws SQLException {
                return 0;
            }

            @Override
            public int getScale(int column) throws SQLException {
                return 0;
            }

            @Override
            public String getTableName(int column) throws SQLException {
                return "";
            }

            @Override
            public String getCatalogName(int column) throws SQLException {
                return "";
            }

            @Override
            public int getColumnType(int column) throws SQLException {
                return 0;
            }

            @Override
            public String getColumnTypeName(int column) throws SQLException {
                return "";
            }

            @Override
            public boolean isReadOnly(int column) throws SQLException {
                return false;
            }

            @Override
            public boolean isWritable(int column) throws SQLException {
                return false;
            }

            @Override
            public boolean isDefinitelyWritable(int column) throws SQLException {
                return false;
            }

            @Override
            public String getColumnClassName(int column) throws SQLException {
                return "";
            }

            @Override
            public <T> T unwrap(Class<T> iface) throws SQLException {
                return null;
            }

            @Override
            public boolean isWrapperFor(Class<?> iface) throws SQLException {
                return false;
            }
        };
        return meta;
    }

    @Override
    public Object getObject(int columnIndex) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getObject (int)Object");
    }

    @Override
    public Object getObject(String columnLabel) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getObject (String)Object");
    }

    @Override
    public int findColumn(String columnLabel) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$findColumn (String)int");
    }

    @Override
    public Reader getCharacterStream(int columnIndex) throws SQLException, EvalException {
        throw new EvalException("TODO: implement EmptyResultSet$getCharacterStream (int)Reader");
    }

    @Override
    public Reader getCharacterStream(String columnLabel) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getCharacterStream (String)Reader");
    }

    @Override
    public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getBigDecimal (int)BigDecimal");
    }

    @Override
    public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getBigDecimal (String)BigDecimal");
    }

    @Override
    public boolean isBeforeFirst() throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$isBeforeFirst ()boolean");
    }

    @Override
    public boolean isAfterLast() throws SQLException {
        return true;
    }

    @Override
    public boolean isFirst() throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$isFirst ()boolean");
    }

    @Override
    public boolean isLast() throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$isLast ()boolean");
    }

    @Override
    public void beforeFirst() throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$beforeFirst ()void");
    }

    @Override
    public void afterLast() throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$afterLast ()void");
    }

    @Override
    public boolean first() throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$first ()boolean");
    }

    @Override
    public boolean last() throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$last ()boolean");
    }

    @Override
    public int getRow() throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getRow ()int");
    }

    @Override
    public boolean absolute(int row) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$absolute (int)boolean");
    }

    @Override
    public boolean relative(int rows) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$relative (int)boolean");
    }

    @Override
    public boolean previous() throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$previous ()boolean");
    }

    @Override
    public void setFetchDirection(int direction) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$setFetchDirection (int)void");
    }

    @Override
    public int getFetchDirection() throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getFetchDirection ()int");
    }

    @Override
    public void setFetchSize(int rows) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$setFetchSize (int)void");
    }

    @Override
    public int getFetchSize() throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getFetchSize ()int");
    }

    @Override
    public int getType() throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getType ()int");
    }

    @Override
    public int getConcurrency() throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getConcurrency ()int");
    }

    @Override
    public boolean rowUpdated() throws SQLException {
        return false;
    }

    @Override
    public boolean rowInserted() throws SQLException {
        return false;
    }

    @Override
    public boolean rowDeleted() throws SQLException {
        return false;
    }

    @Override
    public void updateNull(int columnIndex) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateNull ()void");
    }

    @Override
    public void updateBoolean(int columnIndex, boolean x) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateBoolean ()void");
    }

    @Override
    public void updateByte(int columnIndex, byte x) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateByte ()void");
    }

    @Override
    public void updateShort(int columnIndex, short x) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateShort ()void");
    }

    @Override
    public void updateInt(int columnIndex, int x) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateInt ()void");
    }

    @Override
    public void updateLong(int columnIndex, long x) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateLong ()void");
    }

    @Override
    public void updateFloat(int columnIndex, float x) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateFloat ()void");
    }

    @Override
    public void updateDouble(int columnIndex, double x) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateDouble ()void");
    }

    @Override
    public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateBigDecimal ()void");
    }

    @Override
    public void updateString(int columnIndex, String x) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateString ()void");
    }

    @Override
    public void updateBytes(int columnIndex, byte[] x) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateBytes ()void");
    }

    @Override
    public void updateDate(int columnIndex, Date x) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateDate ()void");
    }

    @Override
    public void updateTime(int columnIndex, Time x) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateTime ()void");
    }

    @Override
    public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateTimestamp ()void");
    }

    @Override
    public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateAsciiStream ()void");
    }

    @Override
    public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateBinaryStream ()void");
    }

    @Override
    public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateCharacterStream ()void");
    }

    @Override
    public void updateObject(int columnIndex, Object x, int scaleOrLength) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateObject ()void");
    }

    @Override
    public void updateObject(int columnIndex, Object x) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateObject ()void");
    }

    @Override
    public void updateNull(String columnLabel) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateNull ()void");
    }

    @Override
    public void updateBoolean(String columnLabel, boolean x) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateBoolean ()void");
    }

    @Override
    public void updateByte(String columnLabel, byte x) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateByte ()void");
    }

    @Override
    public void updateShort(String columnLabel, short x) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateShort ()void");
    }

    @Override
    public void updateInt(String columnLabel, int x) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateInt ()void");
    }

    @Override
    public void updateLong(String columnLabel, long x) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateLong ()void");
    }

    @Override
    public void updateFloat(String columnLabel, float x) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateFloat ()void");
    }

    @Override
    public void updateDouble(String columnLabel, double x) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateDouble ()void");
    }

    @Override
    public void updateBigDecimal(String columnLabel, BigDecimal x) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateBigDecimal ()void");

    }

    @Override
    public void updateString(String columnLabel, String x) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateString ()void");

    }

    @Override
    public void updateBytes(String columnLabel, byte[] x) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateBytes ()void");

    }

    @Override
    public void updateDate(String columnLabel, Date x) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateDate ()void");

    }

    @Override
    public void updateTime(String columnLabel, Time x) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateTime ()void");

    }

    @Override
    public void updateTimestamp(String columnLabel, Timestamp x) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateTimestamp ()void");

    }

    @Override
    public void updateAsciiStream(String columnLabel, InputStream x, int length) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateAsciiStream ()void");

    }

    @Override
    public void updateBinaryStream(String columnLabel, InputStream x, int length) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateBinaryStream ()void");
    }

    @Override
    public void updateCharacterStream(String columnLabel, Reader reader, int length) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateCharacterStream ()void");
    }

    @Override
    public void updateObject(String columnLabel, Object x, int scaleOrLength) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateObject ()void");
    }

    @Override
    public void updateObject(String columnLabel, Object x) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateObject ()void");
    }

    @Override
    public void insertRow() throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$insertRow ()void");
    }

    @Override
    public void updateRow() throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateRow ()void");
    }

    @Override
    public void deleteRow() throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$deleteRow ()void");
    }

    @Override
    public void refreshRow() throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$refreshRow ()void");
    }

    @Override
    public void cancelRowUpdates() throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$cancelRowUpdates ()void");
    }

    @Override
    public void moveToInsertRow() throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$moveToInsertRow ()void");
    }

    @Override
    public void moveToCurrentRow() throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$moveToCurrentRow ()void");
    }

    @Override
    public Statement getStatement() throws SQLException {
        return this.statement;
    }

    @Override
    public Object getObject(int columnIndex, Map<String, Class<?>> map) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getObject ()");
    }

    @Override
    public Ref getRef(int columnIndex) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getRef ()");
    }

    @Override
    public Blob getBlob(int columnIndex) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getBlob ()");
    }

    @Override
    public Clob getClob(int columnIndex) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getClob ()");
    }

    @Override
    public Array getArray(int columnIndex) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getArray ()");
    }

    @Override
    public Object getObject(String columnLabel, Map<String, Class<?>> map) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getObject ()");
    }

    @Override
    public Ref getRef(String columnLabel) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getRef ()");
    }

    @Override
    public Blob getBlob(String columnLabel) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getBlob ()");
    }

    @Override
    public Clob getClob(String columnLabel) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getClob ()");
    }

    @Override
    public Array getArray(String columnLabel) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getArray ()");
    }

    @Override
    public Date getDate(int columnIndex, Calendar cal) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getDate ()");
    }

    @Override
    public Date getDate(String columnLabel, Calendar cal) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getDate ()");
    }

    @Override
    public Time getTime(int columnIndex, Calendar cal) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getTime ()");
    }

    @Override
    public Time getTime(String columnLabel, Calendar cal) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getTime ()");
    }

    @Override
    public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getTimestamp ()");
    }

    @Override
    public Timestamp getTimestamp(String columnLabel, Calendar cal) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getTimestamp ()");
    }

    @Override
    public URL getURL(int columnIndex) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getURL ()");
    }

    @Override
    public URL getURL(String columnLabel) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getURL ()");
    }

    @Override
    public void updateRef(int columnIndex, Ref x) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateRef ()void");
    }

    @Override
    public void updateRef(String columnLabel, Ref x) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateRef ()void");
    }

    @Override
    public void updateBlob(int columnIndex, Blob x) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateBlob ()void");
    }

    @Override
    public void updateBlob(String columnLabel, Blob x) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateBlob ()void");
    }

    @Override
    public void updateClob(int columnIndex, Clob x) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateClob ()void");
    }

    @Override
    public void updateClob(String columnLabel, Clob x) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateClob ()void");
    }

    @Override
    public void updateArray(int columnIndex, Array x) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateArray ()void");
    }

    @Override
    public void updateArray(String columnLabel, Array x) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateArray ()void");
    }

    @Override
    public RowId getRowId(int columnIndex) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getRowId ()");
    }

    @Override
    public RowId getRowId(String columnLabel) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getRowId ()");
    }

    @Override
    public void updateRowId(int columnIndex, RowId x) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateRowId ()void");
    }

    @Override
    public void updateRowId(String columnLabel, RowId x) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateRowId (String, RowId)void");
    }

    @Override
    public int getHoldability() throws SQLException {
        return 0;
    }

    @Override
    public boolean isClosed() throws SQLException {
        return !this.open;
    }

    @Override
    public void updateNString(int columnIndex, String nString) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateNString (int, String)void");
    }

    @Override
    public void updateNString(String columnLabel, String nString) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateNString (String, String)void");
    }

    @Override
    public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateNClob (int, NClub)void");
    }

    @Override
    public void updateNClob(String columnLabel, NClob nClob) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateNClob (String)void");
    }

    @Override
    public NClob getNClob(int columnIndex) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getNClob (int)");
    }

    @Override
    public NClob getNClob(String columnLabel) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getNClob (String)");
    }

    @Override
    public SQLXML getSQLXML(int columnIndex) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getSQLXML ()");
    }

    @Override
    public SQLXML getSQLXML(String columnLabel) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getSQLXML ()");
    }

    @Override
    public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateSQLXML ()void");
    }

    @Override
    public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateSQLXML ()void");
    }

    @Override
    public String getNString(int columnIndex) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getNString ()");
    }

    @Override
    public String getNString(String columnLabel) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getNString ()");
    }

    @Override
    public Reader getNCharacterStream(int columnIndex) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getNCharacterStream ()");
    }

    @Override
    public Reader getNCharacterStream(String columnLabel) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getNCharacterStream ()");
    }

    @Override
    public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateNCharacterStream ()void");
    }

    @Override
    public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateNCharacterStream ()void");
    }

    @Override
    public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateAsciiStream ()void");
    }

    @Override
    public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateBinaryStream ()void");
    }

    @Override
    public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateCharacterStream ()void");
    }

    @Override
    public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateAsciiStream ()void");
    }

    @Override
    public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateBinaryStream ()void");
    }

    @Override
    public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateCharacterStream ()void");
    }

    @Override
    public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateBlob ()void");
    }

    @Override
    public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateBlob ()void");
    }

    @Override
    public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateClob ()void");
    }

    @Override
    public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateClob ()void");
    }

    @Override
    public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateNClob ()void");
    }

    @Override
    public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateNClob ()void");
    }

    @Override
    public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateNCharacterStream ()void");
    }

    @Override
    public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateNCharacterStream ()void");
    }

    @Override
    public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateAsciiStream ()void");
    }

    @Override
    public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateBinaryStream ()void");
    }

    @Override
    public void updateCharacterStream(int columnIndex, Reader x) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateCharacterStream ()void");
    }

    @Override
    public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateAsciiStream ()void");
    }

    @Override
    public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateBinaryStream ()void");
    }

    @Override
    public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateCharacterStream ()void");
    }

    @Override
    public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateBlob (int, InputStream)void");
    }

    @Override
    public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateBlob (String, InputStream)void");
    }

    @Override
    public void updateClob(int columnIndex, Reader reader) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateClob ()void");
    }

    @Override
    public void updateClob(String columnLabel, Reader reader) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateClob (String, Reader)void");
    }

    @Override
    public void updateNClob(int columnIndex, Reader reader) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateNClob (int, Reader)void");
    }

    @Override
    public void updateNClob(String columnLabel, Reader reader) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$updateNClob (String, Reader)void");
    }

    @Override
    public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getObject (int, Class<T>)<T> T");
    }

    @Override
    public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$getObject (String, Class<T>)<T> T");
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new EvalException("TODO: implement EmptyResultSet$unwrap (Class<T>)<T> T");
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }
}
