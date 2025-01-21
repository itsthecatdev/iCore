plugins {
    kotlin("jvm") version "2.1.20-Beta1"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.github.itsthecatdev"
version = "0.1-ALPHA"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.codemc.org/repository/maven-public/")
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://repo.nexomc.com/releases")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.3-R0.1-SNAPSHOT")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("dev.jorel:commandapi-bukkit-kotlin:9.7.0")
    implementation("dev.jorel:commandapi-bukkit-shade:9.7.0")
    compileOnly("com.nexomc:nexo:0.7.0")
}

val targetJavaVersion = 21
kotlin {
    jvmToolchain(targetJavaVersion)
}

tasks.build {
    dependsOn("shadowJar")
}

tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
        expand(props)
    }
}
