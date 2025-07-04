plugins {
    java
    id("org.springframework.boot") version "3.5.3"
    id("io.spring.dependency-management") version "1.1.7"
    id("com.github.spotbugs") version "6.2.0"
}

group = "dompoo"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // web
    implementation("org.springframework.boot:spring-boot-starter-web")

    // jpa
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // validation
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // db
    runtimeOnly("com.h2database:h2")
    runtimeOnly("com.mysql:mysql-connector-j")

    // snowflake
    implementation("com.github.f4b6a3:tsid-creator:5.2.6")

    // lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // springboot docker compose
    developmentOnly("org.springframework.boot:spring-boot-docker-compose")

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // archunit
    testImplementation("com.tngtech.archunit:archunit-junit5:1.4.1")
}

tasks.named("check") {
    dependsOn(tasks.withType(com.github.spotbugs.snom.SpotBugsTask::class.java))
}

tasks.withType<Test> {
    useJUnitPlatform()
    jvmArgs(
        "--add-opens=java.base/java.lang=ALL-UNNAMED",
        "--add-opens=java.base/java.util=ALL-UNNAMED",
        "-javaagent:${classpath.find { it.name.contains("mockito-core") }?.absolutePath}"
    )
}
