package com.gitlab.api.file

import com.blankj.utilcode.util.EncodeUtils
import kotlinx.coroutines.CoroutineScope

suspend fun GitLabFileRepository.getContent(
    scope: CoroutineScope,
    filePath: String,
    ref: String = "main"
): Result<String> {
    return get(scope, filePath, ref)
        .let {
            if (it.isFailure) Result.failure(it.exceptionOrNull()!!)
            else Result.success(String(EncodeUtils.base64Decode(it.getOrThrow().content)))
        }
}

suspend fun GitLabFileRepository.updateOrCreate(
    scope: CoroutineScope,
    filePath: String,
    content: String,
    branch: String = "main"
): Result<String> {
    return if (isFileExists(scope, filePath))
        update(scope, filePath, content, branch)
    else
        create(scope, filePath, content, branch)
}