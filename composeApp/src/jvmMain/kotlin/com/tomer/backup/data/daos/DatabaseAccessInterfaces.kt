package com.tomer.backup.data.daos

import com.tomer.backup.data.BackupLog
import com.tomer.backup.data.FileMetaData
import com.tomer.backup.data.SourcePath

interface SourcePathRepository {
    fun saveSourcePath(sourcePath: SourcePath)
    fun getSourcePathById(id: String): SourcePath?
    fun updateSourcePath(sourcePath: SourcePath)
    fun deleteSourcePath(id: String)
    fun getAllSourcePaths(): List<SourcePath>
}

interface BackupLogRepository {
    fun saveBackupLog(backupLog: BackupLog)
    fun getBackupLogById(id: Int): BackupLog?
    fun getAllBackupLogs(): List<BackupLog>
    fun deleteBackupLog(id: Int)
}

interface FileMetaDataRepository {
    fun saveFileMetaData(fileMetaData: FileMetaData)
    fun getFileMetaDataByPath(absolutePath: String): FileMetaData?
    fun updateFileMetaData(fileMetaData: FileMetaData)
    fun deleteFileMetaData(absolutePath: String)
}
