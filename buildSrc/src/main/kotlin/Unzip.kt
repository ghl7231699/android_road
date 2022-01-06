import org.gradle.api.artifacts.transform.TransformAction
import org.gradle.api.artifacts.transform.TransformOutputs
import org.gradle.api.artifacts.transform.TransformParameters

abstract class Unzip : TransformAction<TransformParameters.None> {
    override fun transform(outputs: TransformOutputs) {
        println("S------------------->B")
    }
}