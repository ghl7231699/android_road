package com.ghl.plugin

import com.android.build.api.transform.Format
import com.android.build.api.transform.JarInput
import com.android.build.api.transform.TransformInvocation
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils
import org.codehaus.groovy.runtime.ResourceGroovyMethods
import java.io.File
import java.io.IOException

/**
 * Time：2023/3/21
 * description:
 */
object EachEveryone {
    @Throws(IOException::class)
    fun each(
        transformInvocation: TransformInvocation,
        jarClosure: (JarInput, File) -> Unit,
        dirFileClosure: (File) -> Unit
    ) {
        val size = ".jar".length
        for (input in transformInvocation.inputs) {
            // jar包遍历
            for (jarInput in input.jarInputs) {
                var jarName = jarInput.name
                val md5Hex = DigestUtils.md5Hex(jarInput.file.absolutePath)
                if (jarName.endsWith(".jar")) {
                    jarName = jarName.substring(0, jarName.length - size)
                }
                // 得到复制后的文件
                val dest = transformInvocation.outputProvider.getContentLocation(
                    jarName + md5Hex,
                    jarInput.contentTypes,
                    jarInput.scopes,
                    Format.JAR
                )
                jarClosure.invoke(jarInput, dest)
                //所有的transform 后的文件都需要进行输出
                FileUtils.copyFile(jarInput.file, dest)
            }
            // 文件夹遍历
            for (directoryInput in input.directoryInputs) {
                // 得到复制后file实例
                val dest = transformInvocation.outputProvider.getContentLocation(
                    directoryInput.name,
                    directoryInput.contentTypes,
                    directoryInput.scopes,
                    Format.DIRECTORY
                )
                // 遍历文件夹中的所有文件
                //TODO  优化
                val file = directoryInput.file
//                ResourceGroovyMethods.eachFileRecurse(file, dirFileClosure)
                dirFileClosure.invoke(file)
                //所有的transform 后的文件都需要进行输出
                FileUtils.copyDirectory(file, dest)
            }
        }
    }
}