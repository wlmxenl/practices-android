package com.gitlab.api.project

import com.blankj.utilcode.util.GsonUtils
import com.drake.net.Get
import kotlinx.coroutines.CoroutineScope

class GitLabProjectRepository(
    private val baseUrl: String,
    private val accessToken: String,
    private val projectId: String
) {

    /**
     * [列出仓库树](https://docs.gitlab.cn/jh/api/repositories.html#列出仓库树)
     * @param scope com.drake.net.NetCoroutine
     * @param path 仓库内的路径。用于获取子目录的内容
     * @param ref 如果没有给定默认分支，为仓库分支或标签的名称
     * @param per_page 每页展示的结果数量。如未指定，默认为 20
     */
    suspend fun getFileList(
        scope: CoroutineScope,
        path: String,
        ref: String = "main",
        per_page: Int = 20
    ): Result<List<GitLabProjectFile>> {
        val url = "$baseUrl/api/v4/projects/$projectId/repository/tree"
        return scope.Get<String>(url) {
            param("ref", ref)
            param("path", path)
            param("access_token", accessToken)
            param("per_page", per_page)
        }.runCatching {
            await()
        }.let { result ->
            if (result.isFailure) Result.failure(result.exceptionOrNull()!!)
            else Result.success(GsonUtils.fromJson(result.getOrThrow(), GsonUtils.getListType(GitLabProjectFile::class.java)))
        }
    }
}