plugins {
    id("org.openapi.generator")
}

springBoot {
    mainClass.set("com.university.support.infrastructure.ApplicationKt")
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":application"))
    
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.swagger.core.v3:swagger-annotations:2.2.15")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.3")
    implementation("io.swagger.core.v3:swagger-annotations-jakarta:2.2.21")
    implementation("jakarta.validation:jakarta.validation-api:3.0.2")
}

openApiGenerate {
    generatorName.set("kotlin-spring")
    inputSpec.set("$projectDir/src/main/resources/api/openapi.yaml")
    outputDir.set("$buildDir/generated")
    apiPackage.set("com.university.support.infrastructure.api")
    modelPackage.set("com.university.support.infrastructure.model")
    configOptions.set(mapOf(
        "interfaceOnly" to "true",
        "useSpringBoot3" to "true",
        "delegatePattern" to "true",
        "serializationLibrary" to "jackson",
        "enumPropertyNaming" to "original"
    ))
}

sourceSets {
    main {
        kotlin {
            srcDir("$buildDir/generated/src/main/kotlin")
        }
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    dependsOn("openApiGenerate")
}
