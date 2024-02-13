val springbootstarter = "3.2.2"
val junit = "5.10.1"
val mapstruct = "1.5.3.Final"
val h2 = "2.2.224"
val flyway = "10.7.2"
val mockito = "3.6.0"

plugins {
    id("java")
    id("org.springframework.boot") version "3.2.2"
}
apply(plugin = "io.spring.dependency-management")


group = "org.phonetesting"
version = "1.0-SNAPSHOT"


repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:$springbootstarter")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:$springbootstarter")
    implementation("org.mapstruct:mapstruct:$mapstruct") // Adjust version if needed
    annotationProcessor("org.mapstruct:mapstruct-processor:$mapstruct")
    runtimeOnly("com.h2database:h2:$h2")
    implementation("org.flywaydb:flyway-core:$flyway")

    testImplementation("org.springframework.boot:spring-boot-starter-test:$springbootstarter")
    testImplementation(platform("org.junit:junit-bom:$junit"))
    testImplementation("org.junit.jupiter:junit-jupiter:$junit")
    testImplementation("org.mockito:mockito-junit-jupiter:$mockito")
    testImplementation("org.mockito:mockito-core:$mockito")
    testImplementation("com.h2database:h2:$h2")
}

tasks.test {
    useJUnitPlatform()
}
