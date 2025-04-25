plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<Test> {
    systemProperty("file.encoding", "UTF-8")
}

dependencies {
    // JUnit 5
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    
    // Selenium - use the latest version
    testImplementation("org.seleniumhq.selenium:selenium-java:4.31.0")
    
    // WebDriverManager - use the latest version
    testImplementation("io.github.bonigarcia:webdrivermanager:6.0.1")
    
    // AssertJ for fluent assertions
    testImplementation("org.assertj:assertj-core:3.24.2")
    
    // SLF4J for logging
    implementation("org.slf4j:slf4j-api:2.0.9")
    implementation("org.slf4j:slf4j-simple:2.0.9")

    // Mockito for mocking
    testImplementation("org.mockito:mockito-core:5.8.0")
    testImplementation("org.mockito:mockito-junit-jupiter:5.8.0")
}

tasks.test {
    useJUnitPlatform()
    
    // Set system property for browser - default is chrome
    systemProperty("browser", System.getProperty("browser", "chrome"))
    
    // Optional: configure test logging
    testLogging {
        events("passed", "skipped", "failed")
    }
    
    // Don't fail the build if tests fail
    ignoreFailures = true
}