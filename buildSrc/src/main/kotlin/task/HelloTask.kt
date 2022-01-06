package task

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

abstract class HelloTask : DefaultTask() {
    @TaskAction
    fun taskAction() {
        println("Hello \"${project.parent?.name}\" from task!")
    }
}