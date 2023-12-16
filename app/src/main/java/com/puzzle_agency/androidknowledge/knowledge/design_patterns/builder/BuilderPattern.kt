package com.puzzle_agency.androidknowledge.knowledge.design_patterns.builder

class HttpRequest private constructor(
    val method: String,
    val url: String,
    val headers: Map<String, String>,
    val body: String?
) {

    class Builder {
        private var method: String? = null
        private var url: String? = null
        private var headers: MutableMap<String, String> = mutableMapOf()
        private var body: String? = null

        fun setMethod(method: String) = apply { this.method = method }
        fun setUrl(url: String) = apply { this.url = url }
        fun addHeader(key: String, value: String) = apply { headers[key] = value }
        fun setBody(body: String?) = apply { this.body = body }

        fun build() = HttpRequest(
            method = method ?: throw IllegalArgumentException("Method is mandatory"),
            url = url ?: throw IllegalArgumentException("Url is mandatory"),
            headers = headers.toMap(),
            body = body
        )
    }
}