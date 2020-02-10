import org.hidetake.gradle.swagger.generator.GenerateSwaggerCode
import org.springframework.boot.gradle.plugin.SpringBootPlugin

plugins {
    id("io.spring.dependency-management")
    id("org.hidetake.swagger.generator") version "2.18.1"
    `java-library`
}

dependencyManagement {
    imports {
        mavenBom(SpringBootPlugin.BOM_COORDINATES)
    }
}

dependencies {
    swaggerCodegen("io.swagger.codegen.v3:swagger-codegen-cli:3.0.5")
    implementation("io.swagger:swagger-annotations:1.5.22")
    implementation("org.springframework.boot:spring-boot-starter-web")
}

swaggerSources {
    register("mvc") {
        val validationTask = validation
        setInputFile(file("swagger.yaml"))
        code(delegateClosureOf<GenerateSwaggerCode> {
            language = "spring"
            configFile = file("config.json")
            components = listOf("models", "apis")
            dependsOn(validationTask)
        })
    }
}

tasks.named("compileJava").configure {
    dependsOn(tasks.named("generateSwaggerCode"))
}

sourceSets {
    val main by getting
    val mvc by swaggerSources.getting
    main.java.srcDir("${mvc.code.outputDir}/src/main/java")
}
