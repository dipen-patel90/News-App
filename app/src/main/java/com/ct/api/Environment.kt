package com.ct.api

enum class Environment(val newsUrl: String) {
    BBC(newsUrl = "https://newsapi.org/v2/"),
    ABC(newsUrl = "https://newsapi.org/v2/");

    companion object {
        fun fromBuildFlavor(flavor: String): Environment {
            val env = enumValues<Environment>().find {
                flavor.equals(it.name, ignoreCase = true)
            }
            if (env != null) {
                return env
            }
            throw UnsupportedOperationException("Invalid flavor type $flavor")
        }
    }
}