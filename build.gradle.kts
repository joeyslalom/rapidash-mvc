import com.palantir.gradle.gitversion.VersionDetails

plugins {
    id("org.springframework.boot") version "2.2.4.RELEASE" apply false
    id("io.spring.dependency-management") version "1.0.9.RELEASE" apply false
    kotlin("jvm") version "1.3.61" apply false
    kotlin("plugin.spring") version "1.3.61" apply false
    id("com.palantir.git-version") version "0.12.2" apply false
}

allprojects {
    apply(plugin = "com.palantir.git-version")

    fun getVersionDetails(): VersionDetails = (extra["versionDetails"] as groovy.lang.Closure<*>)() as VersionDetails

    group = "github.chickenbane"
    version = getVersionDetails().version
}
