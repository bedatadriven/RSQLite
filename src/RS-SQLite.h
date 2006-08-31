#ifndef _RS_SQLite_H
#define _RS_SQLite_H 1
/*  
 * $Id$
 *
 * Copyright (C) 1999-2002 The Omega Project for Statistical Computing.
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

#ifdef _cplusplus
extern  "C" {
#endif

#ifdef WIN32
#  include "windows/sqlite3.h"
#else
#  include <sqlite3.h>
#endif

#include <string.h>
#include <unistd.h>   /* needed by getlogin() -- is this portable??? */

#include "RS-DBI.h"

#define RS_SQLITE_MAX_CON 100    /* arbitrary, since  SQLite is an SQL engine*/
#define RS_SQLITE_FETCH   500    /* records per fetch */

/* SQLite connection parameters struct, allocating and freeing 
 * methods.  This is pretty simple, since SQLite does not recognise users 
 * TODO: SQLite 3.0 api allows for key/value strings.
 */
typedef struct st_sdbi_conParams {
  char *dbname;
  int  mode;                     /* unused by SQLite as of 2.0.5 used */
} RS_SQLite_conParams;

typedef struct st_sqlite_err {
   int  errorNum;
   char *errorMsg;
} RS_SQLite_exception;

RS_SQLite_conParams *RS_SQLite_allocConParams(const char *dbname, int mode);
void                RS_SQLite_freeConParams(RS_SQLite_conParams *conParams);

/* The following functions are the S/R entry into the C implementation (i.e.,
 * these are the only ones visible from R/S) we use the prefix "RS_SQLite" in
 * function names to denote this.  These functions are  built on top of the
 * underlying RS_DBI manager, connection, and resultsets structures and
 * functions (see RS-DBI.h).
 * 
 * Note: A handle is just an R/S object (see RS-DBI.h for details), i.e.,
 * Mgr_Handle, Con_Handle, Res_Handle, Db_Handle are s_object.
 */
  
/* dbManager */
Mgr_Handle *RS_SQLite_init(s_object *config_params, s_object *reload);
s_object   *RS_SQLite_close(Mgr_Handle *mgrHandle); 

/* dbConnection */
Con_Handle *RS_SQLite_newConnection(Mgr_Handle *mgrHandle, s_object *con_params);
Con_Handle *RS_SQLite_cloneConnection(Con_Handle *conHandle);
s_object   *RS_SQLite_closeConnection(Con_Handle *conHandle);
/* we simulate db exceptions ourselves */
void        RS_SQLite_setException(RS_DBI_connection *con, int errorNum, 
                                   const char *errorMsg); 
s_object   *RS_SQLite_getException(Con_Handle *conHandle);
/* err No, Msg */

/* currently we only provide a "standard" callback to sqlite_exec() -- this
 * callback collects all the rows and puts them in a cache in the results set
 * (res->drvData) to simulate a cursor so that we can fetch() like in any other
 * driver.  Other interesting callbacks should allow us to easily implement the
 * dbApply() ideas also in the RMySQL driver
 */
int       RS_SQLite_stdCallback(void *resHandle, int ncol, char **row, 
                                char **colNames);

/* dbResultSet */
Res_Handle *RS_SQLite_exec(Con_Handle *conHandle, s_object *statement, 
                           s_object *s_limit);
s_object   *RS_SQLite_fetch(Res_Handle *rsHandle, s_object *max_rec);
s_object   *RS_SQLite_closeResultSet(Res_Handle *rsHandle);
void        RS_SQLite_initFields(RS_DBI_resultSet *res, int ncol, 
                                 char **colNames);

s_object   *RS_SQLite_validHandle(Db_Handle *handle);      /* boolean */

RS_DBI_fields *RS_SQLite_createDataMappings(Res_Handle *resHandle);
/* the following funs return named lists with meta-data for 
 * the manager, connections, and  result sets, respectively.
 */
s_object *RS_SQLite_managerInfo(Mgr_Handle *mgrHandle);
s_object *RS_SQLite_connectionInfo(Con_Handle *conHandle);
s_object *RS_SQLite_resultSetInfo(Res_Handle *rsHandle);

/*  The following imports the delim-fields of a file into an existing table*/
s_object *RS_SQLite_importFile(Con_Handle *conHandle, s_object *s_tablename,
             s_object *s_filename, s_object *s_separator, s_object *s_obj,
             s_object *s_skip);

/* TODO: general connection */
char * RS_sqlite_getline(FILE *in, const char *eol); 

/* the following type names should be the  SQL-92 data types, and should
 * be moved to the RS-DBI.h
 */ 
enum SQL92_field_types {
     SQL92_TYPE_NULL,
     SQL92_TYPE_BIT,
     SQL92_TYPE_BIT_VAR,
     SQL92_TYPE_CHAR,
     SQL92_TYPE_CHAR_VAR,
     SQL92_TYPE_NCHAR,
     SQL92_TYPE_NCHAR_VAR,
     SQL92_TYPE_SMALLINT,
     SQL92_TYPE_INTEGER,
     SQL92_TYPE_DECIMAL,	
     SQL92_TYPE_FLOAT,
     SQL92_TYPE_REAL,
     SQL92_TYPE_DOUBLE,
     SQL92_TYPE_TIMESTAMP,
     SQL92_TYPE_DATE,
     SQL92_TYPE_TIME,
     SQL92_TYPE_DATETIME
};

const struct data_types RS_SQLite_fieldTypes[] = {
     { "SQL92_TYPE_NULL",       SQL92_TYPE_NULL     },
     { "SQL92_TYPE_BIT",        SQL92_TYPE_BIT      },
     { "SQL92_TYPE_BIT_VAR",    SQL92_TYPE_BIT_VAR  },
     { "SQL92_TYPE_CHAR",       SQL92_TYPE_CHAR     },
     { "SQL92_TYPE_CHAR_VAR",   SQL92_TYPE_CHAR_VAR },
     { "SQL92_TYPE_NCHAR",      SQL92_TYPE_NCHAR    },
     { "SQL92_TYPE_NCHAR_VAR",  SQL92_TYPE_NCHAR_VAR},
     { "SQL92_TYPE_SMALLINT",   SQL92_TYPE_SMALLINT },
     { "SQL92_TYPE_INTEGER",    SQL92_TYPE_INTEGER  },
     { "SQL92_TYPE_DECIMAL",    SQL92_TYPE_DECIMAL  },
     { "SQL92_TYPE_FLOAT",      SQL92_TYPE_FLOAT    },
     { "SQL92_TYPE_REAL",       SQL92_TYPE_REAL     },
     { "SQL92_TYPE_DOUBLE",     SQL92_TYPE_DOUBLE   },
     { "SQL92_TYPE_TIMESTAMP",  SQL92_TYPE_TIMESTAMP},
     { "SQL92_TYPE_DATE",       SQL92_TYPE_DATE     },
     { "SQL92_TYPE_TIME",       SQL92_TYPE_TIME     },
     { "SQL92_TYPE_DATETIME",   SQL92_TYPE_DATETIME },
     { (char *) 0,              -1                  }
};

s_object *RS_SQLite_typeNames(s_object *typeIds);

#ifdef _cplusplus
}
#endif

#endif   /* _RS_SQLite_H */