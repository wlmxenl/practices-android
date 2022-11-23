package com.gitlab.api.file

import android.text.TextUtils
import com.blankj.utilcode.util.EncodeUtils
import com.blankj.utilcode.util.GsonUtils
import com.drake.net.*
import kotlinx.coroutines.CoroutineScope
import okhttp3.Response

class GitLabFileRepository(
    private val baseUrl: String,
    private val accessToken: String,
    private val projectId: String
) {

    /**
     * [从仓库获取原始文件](https://docs.gitlab.cn/jh/api/repository_files.html#从仓库获取原始文件)
     *
     * @param filePath 文件的完整路径, eg: lib/class.rb
     * @param ref 分支、标签或提交的名称
     * @return 原始文件地址
     */
    fun getRawUrl(filePath: String, ref: String? = null): String {
        val refParam = if (TextUtils.isEmpty(ref)) "" else "&ref=$ref"
        return "${buildRequestUrl(filePath)}/raw?access_token=$accessToken".plus(refParam)
    }

    /**
     * [在仓库中删除现存文件](https://docs.gitlab.cn/jh/api/repository_files.html#在仓库中删除现存文件)
     * @param scope com.drake.net.NetCoroutine
     * @param filePath 文件的完整路径, eg: lib/class.rb
     * @param branch 分支名称, 默认 main
     * @param commitMessage 提交信息
     * @return boolean
     */
    suspend fun delete(
        scope: CoroutineScope,
        filePath: String,
        branch: String = "main",
        commitMessage: String = "delete file"
    ): Boolean {
        return scope.Delete<Response>(buildRequestUrl(filePath)) {
            addHeader("PRIVATE-TOKEN", accessToken)
            json(hashMapOf<String, Any>().apply {
                put("branch", branch)
                put("commit_message", commitMessage)
            })
        }.await().isSuccessful
    }

    /**
     * [在仓库中更新现存文件](https://docs.gitlab.cn/jh/api/repository_files.html#在仓库中更新现存文件)
     * @param scope com.drake.net.NetCoroutine
     * @param filePath 文件的完整路径, eg: lib/class.rb
     * @param content 文件内容
     * @param branch 分支名称, 默认 main
     * @param commitMessage 提交信息
     * @return Result<String>
     */
    suspend fun update(
        scope: CoroutineScope,
        filePath: String,
        content: String,
        branch: String = "main",
        commitMessage: String = "update file"
    ): Result<String> {
        return scope.Put<String>(buildRequestUrl(filePath)) {
            addHeader("PRIVATE-TOKEN", accessToken)
            json(hashMapOf<String, Any>().apply {
                put("branch", branch)
                put("content", content)
                put("commit_message", commitMessage)
            })
        }.runCatching { await() }
    }

    /**
     * [在仓库中创建新文件](https://docs.gitlab.cn/jh/api/repository_files.html#在仓库中创建新文件)
     * @param scope com.drake.net.NetCoroutine
     * @param filePath 文件的完整路径, eg: lib/class.rb
     * @param content 文件内容
     * @param branch 分支名称, 默认 main
     * @param commitMessage 提交信息
     * @return Result<String>
     */
    suspend fun create(
        scope: CoroutineScope,
        filePath: String,
        content: String,
        branch: String = "main",
        commitMessage: String = "create a new file"
    ): Result<String> {
        return scope.Post<String>(buildRequestUrl(filePath)) {
            addHeader("PRIVATE-TOKEN", accessToken)
            json(hashMapOf<String, Any>().apply {
                put("branch", branch)
                put("content", content)
                put("commit_message", commitMessage)
            })
        }.runCatching { await() }
    }

    /**
     * [从仓库获取文件](https://docs.gitlab.cn/jh/api/repository_files.html#从仓库获取文件)
     * @param scope com.drake.net.NetCoroutine
     * @param filePath 文件的完整路径, eg: lib/class.rb
     * @param ref 分支名称, 默认 main
     * @return [GitLabFile], 文件不存在时返回 null
     */
    suspend fun get(
        scope: CoroutineScope,
        filePath: String,
        ref: String = "main"
    ): GitLabFile? {
        return scope.Get<String>(buildRequestUrl(filePath)) {
            param("ref", ref)
            param("access_token", accessToken)
        }.runCatching {
            await()
        }.let {
            if (it.isFailure) null
            else GsonUtils.fromJson(it.getOrThrow(), GitLabFile::class.java)
        }
    }

    /**
     * [文件是否存在](https://docs.gitlab.cn/jh/api/repository_files.html#从仓库获取文件)
     * @param scope com.drake.net.NetCoroutine
     * @param filePath 文件的完整路径, eg: lib/class.rb
     * @param ref 分支名称, 默认 main
     * @return boolean
     */
    suspend fun isFileExists(scope: CoroutineScope, filePath: String, ref: String = "main"): Boolean {
        return scope.Head<Response>(buildRequestUrl(filePath)) {
            param("ref", ref)
            param("access_token", accessToken)
        }.await().isSuccessful
    }

    private fun buildRequestUrl(filePath: String): String {
        val finalBaseUrl = if (baseUrl.endsWith("/")) baseUrl.dropLast(1) else baseUrl
        val encodeFilePath = EncodeUtils.urlEncode(filePath)
        return "$finalBaseUrl/api/v4/projects/$projectId/repository/files/$encodeFilePath"
    }
}