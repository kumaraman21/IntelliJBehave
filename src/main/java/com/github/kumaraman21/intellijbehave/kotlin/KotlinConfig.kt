package com.github.kumaraman21.intellijbehave.kotlin

import com.intellij.ide.plugins.PluginManager
import com.intellij.openapi.extensions.PluginId

/**
 * Created by Rodrigo Quesada on 20/09/15.
 */
private val kotlinPluginId = "org.jetbrains.kotlin"

val pluginIsEnabled = PluginManager.getPlugin(PluginId.getId(kotlinPluginId))?.isEnabled ?: false