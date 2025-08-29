package com.tomer.backup.data

import org.h2.Driver
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun initDatabase() {
    Database.connect("jdbc:h2:file:./data/app.db", driver = Driver::class.java.name)
    transaction {
        SchemaUtils.create(BackupLogTable, FileMetaDataTable, SourcePathTable)
    }
}
