package com.ghl.biz_home.ui

import com.ghl.lib_dirty.net.base.BaseBean
import retrofit2.Call
import retrofit2.http.GET

data class ResultBean(
        val basic: Basic?,
        val errorCode: Int?,
        val query: String?,
        val translation: List<String>?,
        val web: List<Web>?
)

data class Basic(
        val explains: List<String>?,
        val phonetic: String?
)

data class Web(
        val key: String?,
        val value: List<String>?
)


data class ArticleListInfo(
        val curPage: Int,
        val datas: List<Data>,
        val offset: Int,
        val over: Boolean,
        val pageCount: Int,
        val size: Int,
        val total: Int
)

data class Data(
        val apkLink: String,
        val audit: Int,
        val author: String,
        val canEdit: Boolean,
        val chapterId: Int,
        val chapterName: String,
        val collect: Boolean,
        val courseId: Int,
        val desc: String,
        val descMd: String,
        val envelopePic: String,
        val fresh: Boolean,
        val id: Int,
        val link: String,
        val niceDate: String,
        val niceShareDate: String,
        val origin: String,
        val prefix: String,
        val projectLink: String,
        val publishTime: Long,
        val realSuperChapterId: Int,
        val selfVisible: Int,
        val shareDate: Long,
        val shareUser: String,
        val superChapterId: Int,
        val superChapterName: String,
        val tags: List<Tag>,
        val title: String,
        val type: Int,
        val userId: Int,
        val visible: Int,
        val zan: Int
)

data class Tag(
        val name: String,
        val url: String
)


interface HomeService {
    @GET("openapi.do?keyfrom=Yanzhikai&key=2032414398&type=data&doctype=json&version=1.1&q=car")
    fun getCall(): Call<ResultBean>

    @GET("article/list/1/json")
    fun getList(): Call<BaseBean<ArticleListInfo>>
}