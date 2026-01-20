plugins {
    id("java-library")
}

dependencies {
    api(project(":domain"))
    implementation("org.springframework.boot:spring-boot-starter")
    testImplementation("org.mockito:mockito-core:5.8.0")
    testImplementation("org.mockito:mockito-junit-jupiter:5.8.0")
}
