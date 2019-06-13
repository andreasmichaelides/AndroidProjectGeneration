import codegen.feature.createFeature
import codegen.model.MustacheModel
import com.github.mustachejava.DefaultMustacheFactory
import com.github.mustachejava.Mustache
import com.google.common.base.CaseFormat
import java.io.File
import java.io.PrintWriter
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path


fun main(args: Array<String>) {

    val featureName = args[0]
    val packageName = args[1]
    val corePackageName = args[2]

    val mustacheFactory = DefaultMustacheFactory()

    createFeature(featureName, packageName, corePackageName, mustacheFactory)
}