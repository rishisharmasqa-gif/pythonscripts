#!/bin/bash

# Simple Database Backup Script for Jenkins Training
# Variables will be provided by Jenkins parameters

echo "Starting backup for $DBENGINE database: $DBNAME"

if [ "$DBENGINE" = "mysql" ]; then
    echo "Taking MySQL backup..."
    mysqldump -h $HOSTNAME -u $USERNAME -p$PASSWORD $DBNAME > $DBNAME.sql
    echo "MySQL backup completed: $DBNAME.sql"

elif [ "$DBENGINE" = "postgres" ]; then
    echo "Taking PostgreSQL backup..."
    export PGPASSWORD=$PASSWORD
    pg_dump -h $HOSTNAME -U $USERNAME $DBNAME > $DBNAME.sql
    echo "PostgreSQL backup completed: $DBNAME.sql"

elif [ "$DBENGINE" = "oracle" ]; then
    echo "Taking Oracle backup..."
    expdp $USERNAME/$PASSWORD@$HOSTNAME/$DBNAME directory=DATA_PUMP_DIR dumpfile=$DBNAME.dmp
    echo "Oracle backup completed: $DBNAME.dmp"

else
    echo "ERROR: Unknown database engine: $DBENGINE"
    echo "Supported: mysql, postgres, oracle"
    exit 1
fi

echo "Backup process finished!"