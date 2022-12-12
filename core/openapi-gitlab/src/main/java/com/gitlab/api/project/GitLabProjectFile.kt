package com.gitlab.api.project

import androidx.annotation.Keep

@Keep
data class GitLabProjectFile(
    val id: String,
    val name: String,
    val type: String,
    val path: String,
    val mode: String
)
