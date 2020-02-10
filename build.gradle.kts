plugins {
    id("org.springframework.boot") version "2.2.4.RELEASE" apply false
    id("io.spring.dependency-management") version "1.0.9.RELEASE" apply false
    kotlin("jvm") version "1.3.61" apply false
    kotlin("plugin.spring") version "1.3.61" apply false
}

group = "github.chickenbane"
version = "0.0.1-SNAPSHOT"

// integration-test module requires a service running to test against
// run manually with: ./gradlew -Pintegration-test test
project("integration-test") {
    tasks.withType<Test> {
        onlyIf {
            project.hasProperty("integration-test")
        }
    }
}


// to force tests: ./gradlew -Pintegration-test --no-build-cache cleanTest test

