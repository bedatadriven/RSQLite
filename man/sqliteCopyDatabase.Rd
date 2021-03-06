% Generated by roxygen2: do not edit by hand
% Please edit documentation in R/copy.R
\name{sqliteCopyDatabase}
\alias{sqliteCopyDatabase}
\title{Copy a SQLite database}
\usage{
sqliteCopyDatabase(from, to)
}
\arguments{
\item{from}{A \code{SQLiteConnection} object. The main database in
\code{from} will be copied to \code{to}.}

\item{to}{A \code{SQLiteConnection} object pointing to an empty database.}
}
\value{
Returns \code{NULL} (invisibly).
}
\description{
This function copies a database connection to a file or to another database
connection.  It can be used to save an in-memory database (created using
\code{dbname = ":memory:"} or
\code{dbname = "file::memory:"}) to a file or to create an in-memory database
a copy of anothe database.
}
\examples{
library(datasets)
library(utils)
# Copy the built in databaseDb() to an in memory dataset
db <- dbConnect(RSQLite::SQLite(), ":memory:")
sqliteCopyDatabase(datasetsDb(), db)
dbListTables(db)
}
\author{
Seth Falcon
}
\references{
\url{http://www.sqlite.org/backup.html}
}

library(RSQLite)
library(datasets)
library(utils)
db <- dbConnect(RSQLite::SQLite(), ":memory:")
sqliteCopyDatabase(datasetsDb(), db)
dbListTables(db)