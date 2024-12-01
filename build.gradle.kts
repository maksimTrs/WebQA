import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.0"
    id("io.qameta.allure") version "2.12.0"
}

group = "com.webqa"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Kotlin
    implementation(kotlin("stdlib"))

    // Testing
    testImplementation("org.testng:testng:7.7.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")

    // Assertions
    testImplementation("org.assertj:assertj-core:3.24.2")

    // Selenium
    implementation("org.seleniumhq.selenium:selenium-java:4.11.0")
    implementation("io.github.bonigarcia:webdrivermanager:5.4.1")

    // REST Assured
    implementation("io.rest-assured:rest-assured:5.3.1")
    implementation("io.rest-assured:kotlin-extensions:5.3.1")

    // Allure
    implementation("io.qameta.allure:allure-testng:2.29.0")
    implementation("io.qameta.allure:allure-rest-assured:2.29.0")

    // Data Generation
    implementation("net.datafaker:datafaker:2.0.1")

    // Configuration
    implementation("com.typesafe:config:1.4.2")

    // JSON
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.2")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.15.2")

    testImplementation("ch.qos.logback:logback-classic:1.4.11")
    testImplementation("org.slf4j:slf4j-api:2.0.9")
    testImplementation("org.codehaus.janino:janino:3.1.10") // For colored console output
}

tasks.test {
    useTestNG()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

allure {
    version.set("2.22.2")
}
