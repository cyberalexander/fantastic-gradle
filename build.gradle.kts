import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.util.capitalizeDecapitalize.toUpperCaseAsciiOnly

plugins {
	id("org.springframework.boot") version "2.6.7"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
}

group = "com.leonovich.fantasticgradle"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-rest")
	implementation("org.springframework.boot:spring-boot-starter-hateoas")
	implementation("org.springframework.boot:spring-boot-starter-web-services")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	implementation("org.springframework.session:spring-session-core")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

/**
 * The task dedicated to print "Hello World!" phrase in console after successful execution.
 */
tasks.register("hello") {
    dependsOn("build")
	doLast {
		println("Hello World!")
	}
}

/**
 * The task retrieves system property user.name and converts the value to upper case.
 */
tasks.register("upper") {
    dependsOn("hello")
	doLast {
		val name = System.getProperty("user.name")
        println("name: $name")
        println("upper: ${name.toUpperCaseAsciiOnly()}")
	}
}

/**
 * The tasks prints the numbers in sequetial order in a row till the limit configured in 'repeat' input is reached.
 */
tasks.register("counter") {
    dependsOn("upper")
    doLast {
        repeat(5) {
            print("$it ")
        }
    }
}

/**
 * Generate 3 tasks: [task_0, task_1, task_2]
 */
repeat(3) {counter ->
	tasks.register("task_$counter") {
		doLast {
			println("I'm task number $counter")
		}
	}
}

/**
 * Configure the task_0 to be executed after task_1 and task_2.
 *
 * Run {@sample gradle task_0}
 *
 * Expected to be printed in terminal:
 * I'm task number 1
 * I'm task number 2
 * I'm task number 0
 */
tasks.named("task_0") {
	dependsOn("task_1", "task_2")
}

/**
 * Custom task example.
 */
abstract class GreetingTask: DefaultTask() {
	@TaskAction
	fun greet() {
		println("Hello from GreetingTask")
	}
}

tasks.register<GreetingTask>("helloFromGreetingTask")
