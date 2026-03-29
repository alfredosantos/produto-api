plugins {
	kotlin("jvm") version "2.2.21"
	kotlin("plugin.spring") version "2.2.21"
	id("org.springframework.boot") version "4.0.5"
	id("io.spring.dependency-management") version "1.1.7"
	jacoco
}

group = "com.br"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("com.h2database:h2:2.2.224")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.6")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(module = "mockito-core")
	}
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testImplementation("io.mockk:mockk:1.13.7")
	testImplementation("org.springframework.boot:spring-boot-test-autoconfigure")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.jacocoTestReport {
	dependsOn(tasks.test)
	classDirectories.setFrom(
		files(
			classDirectories.files.map {
				fileTree(it) {
					exclude("com/br/produto_api/ProdutoApiApplicationKt.class")
				}
			}
		)
	)
	reports {
		xml.required.set(true)
		html.required.set(true)
	}
}

tasks.jacocoTestCoverageVerification {
	dependsOn(tasks.test)
	classDirectories.setFrom(
		files(
			classDirectories.files.map {
				fileTree(it) {
					exclude("com/br/produto_api/ProdutoApiApplicationKt.class")
				}
			}
		)
	)
	violationRules {
		rule {
			limit {
				minimum = "1.0".toBigDecimal()
			}
		}
	}
}

jacoco {
	toolVersion = "0.8.13"
}

