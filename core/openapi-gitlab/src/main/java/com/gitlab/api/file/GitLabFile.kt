package com.gitlab.api.file

import androidx.annotation.Keep

@Keep
data class GitLabFile(
    val blob_id: String,
    val commit_id: String,
    val content: String,
    val content_sha256: String,
    val encoding: String,
    val execute_filemode: Boolean,
    val file_name: String,
    val file_path: String,
    val last_commit_id: String,
    val ref: String,
    val size: Int
)