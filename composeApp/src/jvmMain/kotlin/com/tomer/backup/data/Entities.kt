package com.tomer.backup.data

import org.jetbrains.exposed.sql.Table
import java.time.Instant

object SourcePathTable : Table("source_path") {
    val absolutePath = text("absolute_path")
    val name = varchar("file_name", 255)
    val isFile = bool("is_file").default(false)
    val dateAdded = long("time_stamp_seconds").default(Instant.now().epochSecond)

    override val primaryKey: PrimaryKey = PrimaryKey(absolutePath)
}

object BackupLogTable : Table("backup_log") {
    val id = integer("id").autoIncrement()
    val name = varchar("file_name", 255)
    val absolutePath = text("absolute_path")
    val timeLogged = long("time_stamp_seconds").default(Instant.now().epochSecond)
    val type = enumerationByName<LogType>("log_type", 8)

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}

object FileMetaDataTable : Table("file_meta_data") {
    val absolutePath = text("absolute_path")
    val fileSize = long("last_size")
    val lastModified = long("file_last_modified_millis")
    val shaDigest = varchar("sha_digest", 64)

    override val primaryKey: PrimaryKey = PrimaryKey(absolutePath)
}


