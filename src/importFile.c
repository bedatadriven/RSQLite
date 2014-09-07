/*
 * Copyright (C) 1999-2003 The Omega Project for Statistical Computing.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

#include "rsqlite.h"

SEXP     /* returns TRUE/FALSE */
RS_SQLite_importFile(
    SEXP conHandle,
    SEXP s_tablename,
    SEXP s_filename,
    SEXP s_separator,
    SEXP s_eol,
    SEXP s_skip
    )
{
    RS_DBI_connection *con = RS_DBI_getConnection(conHandle);
    sqlite3           *db_connection = (sqlite3 *) con->drvConnection;
    char              *zFile, *zTable, *zSep, *zEol;
    const char *s, *s1;
    int              rc, skip;
    SEXP output;

    s = CHR_EL(s_tablename, 0);
    zTable = (char *) malloc( strlen(s)+1);
    if(!zTable){
        error("could not allocate memory");
    }
    (void) strcpy(zTable, s);

    s = CHR_EL(s_filename, 0);
    zFile = (char *) malloc( strlen(s)+1);
    if(!zFile){
        free(zTable);
        error("could not allocate memory");
    }
    (void) strcpy(zFile, s);

    s = CHR_EL(s_separator, 0);
    s1 = CHR_EL(s_eol, 0);
    zSep = (char *) malloc( strlen(s)+1);
    zEol = (char *) malloc(strlen(s1)+1);
    if(!zSep || !zEol){
        free(zTable);
        free(zFile);
        if(zSep) free(zSep);
        if(zEol) free(zEol);
        error("could not allocate memory");
    }
    (void) strcpy(zSep, s);
    (void) strcpy(zEol, s1);
    skip = INT_EL(s_skip, 0);

    rc = RS_sqlite_import(db_connection, zTable, zFile, zSep, zEol, skip);

    free(zTable);
    free(zFile);
    free(zSep);

    PROTECT(output = NEW_LOGICAL(1));
    LOGICAL_POINTER(output)[0] = rc;
    UNPROTECT(1);
    return output;
}

/* The following code comes directly from SQLite's shell.c, with
 * obvious minor changes.
 */
int
RS_sqlite_import(
    sqlite3 *db,
    const char *zTable,          /* table must already exist */
    const char *zFile,
    const char *separator,
    const char *eol,
    int skip
    )
{
    sqlite3_stmt *pStmt;        /* A statement */
    int rc;                     /* Result code */
    int nCol;                   /* Number of columns in the table */
    int nByte;                  /* Number of bytes in an SQL string */
    int i, j;                   /* Loop counters */
    int nSep;                   /* Number of bytes in separator[] */
    char *zSql;                 /* An SQL statement */
    char *zLine = NULL;         /* A single line of input from the file */
    char **azCol;               /* zLine[] broken up into columns */
    FILE *in;                   /* The input file */
    int lineno = 0;             /* Line number of input file */
    char *z;

    nSep = strlen(separator);
    if( nSep==0 ){
        error("RS_sqlite_import: non-null separator required for import");
    }
    zSql = sqlite3_mprintf("SELECT * FROM '%q'", zTable);
    if( zSql==0 ) return 0;
    nByte = strlen(zSql);
    rc = sqlite3_prepare_v2(db, zSql, -1, &pStmt, 0);
    sqlite3_free(zSql);
    if (rc != SQLITE_OK) {
        sqlite3_finalize(pStmt);
        error("RS_sqlite_import: %s", sqlite3_errmsg(db));
        nCol = 0;
    }else{
        nCol = sqlite3_column_count(pStmt);
    }
    sqlite3_finalize(pStmt);
    if( nCol==0 ) return 0;
    zSql = malloc( nByte + 20 + nCol*2 );
    if( zSql==0 ) return 0;
    sqlite3_snprintf(nByte+20, zSql, "INSERT INTO '%q' VALUES(?", zTable);
    j = strlen(zSql);
    for(i=1; i<nCol; i++){
        zSql[j++] = ',';
        zSql[j++] = '?';
    }
    zSql[j++] = ')';
    zSql[j] = 0;
    rc = sqlite3_prepare_v2(db, zSql, -1, &pStmt, 0);
    free(zSql);
    if (rc != SQLITE_OK) {
        sqlite3_finalize(pStmt);
        error("RS_sqlite_import: %s", sqlite3_errmsg(db));
    }
    in = fopen(zFile, "rb");
    if( in==0 ){
        error("RS_sqlite_import: cannot open file %s", zFile);
        sqlite3_finalize(pStmt);
    }
    azCol = malloc( sizeof(azCol[0])*(nCol+1) );
    if( azCol==0 ) return 0;

    while( (zLine = RS_sqlite_getline(in, eol)) != NULL){
        lineno++;
        if(lineno <= skip) continue;
        i = 0;
        azCol[0] = zLine;
        for(i=0, z=zLine; *z && *z!='\n' && *z!='\r'; z++){
            if( *z==separator[0] && strncmp(z, separator, nSep)==0 ){
                *z = 0;
                i++;
                if( i<nCol ){
                    azCol[i] = &z[nSep];
                    z += nSep-1;
                }
            }
        }
        if( i+1!=nCol ){
            error("RS_sqlite_import: %s line %d expected %d columns of data but found %d",
              zFile, lineno, nCol, i+1);
        }

        for(i=0; i<nCol; i++){
            if(azCol[i][0]=='\\' && azCol[i][1]=='N'){   /* insert NULL for NA */
                sqlite3_bind_null(pStmt, i+1);
            }
            else {
                sqlite3_bind_text(pStmt, i+1, azCol[i], -1, SQLITE_STATIC);
            }
        }

        rc = sqlite3_step(pStmt);
        if (rc != SQLITE_DONE && rc != SQLITE_SCHEMA) {
            sqlite3_finalize(pStmt);
            error("RS_sqlite_import: %s", sqlite3_errmsg(db));
        }
        rc = sqlite3_reset(pStmt);
        free(zLine);
        zLine = NULL;
        if (rc != SQLITE_OK) {
            sqlite3_finalize(pStmt);
            error("RS_sqlite_import: %s", sqlite3_errmsg(db));
        }
    }
    free(azCol);
    fclose(in);
    sqlite3_finalize(pStmt);
    return 1;
}

/* the following is only needed (?) on windows (getline is a GNU extension
 * and it gave me problems with minGW).  Note that we drop the (UNIX)
 * new line character.  The R function safe.write() explicitly uses
 * eol = '\n' even on Windows.
 */

char *
RS_sqlite_getline(FILE *in, const char *eol)
{
    /* caller must free memory */
    char *buf, ceol;
    size_t nc, i;
    int c, j, neol;
    int found_eol = 0;

    nc = 1024; i = 0;
    buf = (char *) malloc(nc);
    if(!buf) error("RS_sqlite_getline could not malloc");

    neol = strlen(eol);  /* num of eol chars */
    ceol = eol[neol-1];  /* last char in eol */
    while(TRUE){
        c=fgetc(in);
        if(i==nc){
            nc = 2 * nc;
            buf = (char *) realloc((void *) buf, nc);
            if(!buf)
                error("RS_sqlite_getline could not realloc");
        }
        if(c==EOF)
            break;
        buf[i++] = c;
        if (c == ceol) {
            /* see if we've got eol */
            found_eol = 1;
            for (j = neol - 1; j > 0; j--) {
                if (buf[(i - 1) - j] != eol[neol - 1 - j]) {
                    found_eol = 0;
                    break;
                }
            }
            if (found_eol) {
                buf[i-neol] = '\0';   /* drop the newline char(s) */
                break;
            }
        }
    }

    if (i == 0 || strlen(buf) == 0) {    /* empty line */
        free(buf);
        buf = (char *) NULL;
    }

    return buf;
}
