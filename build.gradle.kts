import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "1.9.21"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("xyz.jpenilla.run-paper") version "2.2.2"
}

group = "dev.tarna"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public")
    maven("https://repo.skriptlang.org/releases")
    maven("https://repo.destroystokyo.com/repository/maven-public")
    maven("https://repo.aikar.co/content/groups/aikar")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
    compileOnly("com.github.SkriptLang:Skript:2.8.2")
    implementation("dev.dejvokep:boosted-yaml:1.3")
    implementation("co.aikar:acf-paper:0.5.1-SNAPSHOT")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}

tasks.compileKotlin {
    kotlinOptions.javaParameters = true
}

tasks.withType<ShadowJar> {
    relocate("co.aikar.commands", "dev.tarna.skraps.api.acf")
    relocate("co.aikar.locales", "dev.tarna.skraps.api.locales")
}

tasks.runServer {
    minecraftVersion("1.20.4")
}