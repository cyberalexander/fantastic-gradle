import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.util.capitalizeDecapitalize.toUpperCaseAsciiOnly

println("Processing 'build.gradle.kts' during the configuration phase.")
//----------------------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
// Plugins section BEGIN -----------------------------------------------------------------------------------------------
plugins {
	base
	pmd
	checkstyle
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
	id("org.springframework.boot") version "2.6.7"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
}
// Plugins section END -------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
// Configurations section BEGIN ----------------------------------------------------------------------------------------
group = "com.leonovich.fantasticgradle"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

java {
	withSourcesJar()
}

/**
 * PMD plugin extension configuration.
 * @link https://docs.gradle.org/current/userguide/pmd_plugin.html
 */
pmd {
	isConsoleOutput = true
	toolVersion = project.property("pmd_plugin_version").toString()
	rulesMinimumPriority.set(3)
	ruleSetConfig = resources.text.fromFile(project.property("pmd_rules_location").toString())
}

/**
 * 'afterProject' notification is received regardless of whether
 * the project evaluates successfully or fails with an exception.
 */
gradle.afterProject {
	if (state.failure != null) {
		println("Evaluation of $project failed!")
	} else {
		println("Evaluation of $project succeeded.")
	}
}

gradle.taskGraph.beforeTask {
	//println("\nExecuting $this ...")
}

gradle.taskGraph.afterTask {
//	if (state.failure != null) {
//		println("$this failed!")
//	} else {
//		println("$this succeed!")
//	}
}
// Configurations section END ------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
// Dependencies section BEGIN ------------------------------------------------------------------------------------------
repositories {
	mavenCentral()
	mavenLocal()
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
// Dependencies section END --------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
// Build Script section BEGIN ------------------------------------------------------------------------------------------
/**
 * The buildScript block determines which plugins, task classes, and other classes are available for use in the rest
 * of the build script. Without a buildScript block, you can use everything that ships with Gradle out-of-the-box.
 * If you additionally want to use third-party plugins, task classes, or other classes (in the build script!),
 * you have to specify the corresponding dependencies in the buildScript block.
 *
 * @link https://docs.gradle.org/current/userguide/tutorial_using_tasks.html#sec:build_script_external_dependencies
 */
buildscript {
	repositories {
		mavenCentral()
		mavenLocal()
	}

	dependencies {
		classpath("commons-codec:commons-codec:1.2")
	}
}
// Build Script section END --------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
// Tasks section BEGIN -------------------------------------------------------------------------------------------------
tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.jar {
	manifest {
		attributes(
			mapOf(
				"Implementation-Title" to project.name,
				"Implementation-Version" to project.version
			)
		)
	}
}

/**
 * The task dedicated to print "Hello World!" phrase in console after successful execution.
 */
tasks.register("hello") {
	group = JavaBasePlugin.DOCUMENTATION_GROUP
	description = "Says 'Hello World!' as the result of invocation. As well it's accessing and printing custom project property."
	dependsOn("build")
	doLast {
		println("fantastic_gradle_version: ${project.property("fantastic_gradle_version")}")
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

	@get:Input
	abstract val greeting: Property<String>

	init {
		greeting.convention("Hello from GreetingTask")
	}

	@TaskAction
	fun greet() {
		println(greeting.get())
	}
}

tasks.register<GreetingTask>("helloFromGreetingTaskDefault")

tasks.register<GreetingTask>("helloFromGreetingTaskCustom") {
	greeting.set("Hello from 'helloFromGreetingTaskCustom'")
}

/**
 * @see https://docs.gradle.org/current/dsl/org.gradle.api.tasks.bundling.Tar.html
 * @command 'gradle tarTextFiles --rerun-tasks'
 */
tasks.register<Tar>("tarTextFiles") {
	compression = Compression.GZIP
	//println("tarTextFiles#root:$rootDir")
	from(rootDir)
		.include("*.txt")
		.into("text")
		.rename("(.+).txt", "$1.text")

	doFirst {
		println("Creating TAR file")
	}
}

/**
 * Aggregation task. Do nothing itself, but just calls some set of other tasks.
 */
tasks.register("createArchive") {
	dependsOn("tarTextFiles")
}

tasks.register("encode") {
	doLast {
		val encodedString = org.apache.commons.codec.binary.Base64().encode("Hello World!\n".toByteArray())
		println(encodedString)
	}
}
// Tasks section END ---------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------