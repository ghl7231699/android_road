import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register
import task.HelloTask

class CustomPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.tasks.register<HelloTask>("hello")

    }
}