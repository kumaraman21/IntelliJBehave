import org.jetbrains.changelog.Changelog
import org.jetbrains.changelog.markdownToHTML

fun properties(key: String) = project.findProperty(key).toString()

plugins {
    // Java support
    id("java")
    // Kotlin support
    id("org.jetbrains.kotlin.jvm") version "1.7.21"
    // Gradle IntelliJ Plugin
    id("org.jetbrains.intellij") version "1.13.3"
    // Gradle Changelog Plugin
    id("org.jetbrains.changelog") version "2.0.0"
}

group = properties("pluginGroup")
version = properties("pluginVersion")

// Configure project's dependencies
repositories {
    mavenCentral()
}

// Set the JVM language level used to build the project. Use Java 11 for 2020.3+, and Java 17 for 2022.2+.
kotlin {
    jvmToolchain(11)
}

dependencies {
    //https://kotlinlang.org/docs/reflection.html#jvm-dependency
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.7.21")
    implementation("org.jbehave:jbehave-core:5.1.1")
    testImplementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.7.21")
    testImplementation("org.assertj:assertj-core:3.20.2")
    testImplementation("junit:junit:4.13.2")
}

// Configure Gradle IntelliJ Plugin - read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    pluginName.set(properties("pluginName"))
    version.set(properties("platformVersion"))
    type.set(properties("platformType"))

    // Plugin Dependencies. Uses `platformPlugins` property from the gradle.properties file.
    plugins.set(properties("platformPlugins").split(',').map(String::trim).filter(String::isNotEmpty))
}

// Configure Gradle Changelog Plugin - read more: https://github.com/JetBrains/gradle-changelog-plugin
changelog {
    groups.set(emptyList())
    repositoryUrl.set(properties("pluginRepositoryUrl"))
    headerParserRegex.set("\\d+\\.\\d+(\\.\\d+)?")
}

tasks {
    wrapper {
        gradleVersion = properties("gradleVersion")
    }

    patchPluginXml {
        version.set(properties("pluginVersion"))
        sinceBuild.set(properties("pluginSinceBuild"))
        untilBuild.set(properties("pluginUntilBuild"))

        // Extract the <!-- Plugin description --> section from README.md and provide for the plugin's manifest
        pluginDescription.set(
                file("README.md").readText().lines().run {
                    val start = "<!-- Plugin description -->"
                    val end = "<!-- Plugin description end -->"

                    if (!containsAll(listOf(start, end))) {
                        throw GradleException("Plugin description section not found in README.md:\n$start ... $end")
                    }
                    subList(indexOf(start) + 1, indexOf(end))
                }.joinToString("\n").let { markdownToHTML(it) }
        )

        // Get the latest available change notes from the changelog file
        changeNotes.set(provider {
            with(changelog) {
                renderItem(
                        getOrNull(properties("pluginVersion"))
                                ?: runCatching { getLatest() }.getOrElse { getUnreleased() },
                        Changelog.OutputType.HTML,
                )
            }
        })
    }

    test {
        //Required for running tests in 2021.3 due to it not finding test classes properly.
        //See https://app.slack.com/client/T5P9YATH9/C5U8BM1MK/thread/C5U8BM1MK-1639934273.054400
        isScanForTestClasses = false
        include("**/*Test.class")
        exclude("**/StoryLocalizedLexer_FrenchTest.class")
    }

//    runPluginVerifier {
//        ideVersions.set(listOf("IC-232.7754.73"))
//    }
}
