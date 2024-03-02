package dev.tarna.skraps.api.config

import dev.dejvokep.boostedyaml.YamlDocument
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings
import dev.tarna.skraps.Skraps
import dev.tarna.skraps.api.util.Util
import java.io.File

object ConfigUtils {
    private val plugin = Skraps.get()

    fun createConfig(name: String): YamlDocument? {
        try {
            val config = YamlDocument.create(
                File(plugin.dataFolder, "$name.yml"),
                plugin.getResource("$name.yml") ?: error("Missing $name.yml in jar"),
                GeneralSettings.DEFAULT,
                LoaderSettings.builder()
                    .setAutoUpdate(true)
                    .build(),
                DumperSettings.DEFAULT,
                UpdaterSettings.builder()
                    .setVersioning(BasicVersioning("config-version"))
                    .setOptionSorting(UpdaterSettings.OptionSorting.SORT_BY_DEFAULTS)
                    .build()
            )

            config.update()
            config.save()
            return config
        } catch (e: Exception) {
            Util.log("<red>Failed to load $name.yml, disabling plugin!")
            plugin.server.pluginManager.disablePlugin(plugin)
            e.printStackTrace()
            return null
        }
    }
}