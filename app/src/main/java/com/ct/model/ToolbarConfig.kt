package com.ct.model

sealed class ToolbarConfig {
    class Show(val customTitle: String? = null) : ToolbarConfig()
    object Hide : ToolbarConfig()
}