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

//    val featureName = "CheckIn"
//    val packageName = "src/main/kotlin"
    val featureName = args[0]
    val packageName = args[1]
    val corePackageName = args[2]

    val mustacheFactory = DefaultMustacheFactory()

    createFeature(featureName, packageName, corePackageName, mustacheFactory)
}

fun writeFile(path: Path, lines: List<String>) {
    Files.write(path, lines, StandardCharsets.UTF_8)
}

fun writeMustacheTemplate(mustache: Mustache, mustacheModel: MustacheModel, writer: PrintWriter) {
    mustache.execute(writer, mustacheModel).flush()
}

fun getFileWriter(filePath: String, fileName: String, fileExtension: String): PrintWriter {
    val packagePath = createFolder(filePath).path
    val fileToWrite = File("$packagePath${File.separator}$fileName$fileExtension")
    return fileToWrite.printWriter()
}

private fun createFolder(path: String): File {
    val file = File(path)
    file.mkdirs()
    return file
}

fun String.toLowerUnderscore(): String {
    return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, this)
}

fun String.toLowerCameCase(): String {
    return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, this)
}