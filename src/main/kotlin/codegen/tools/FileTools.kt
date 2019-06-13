package codegen.tools

import codegen.model.MustacheModel
import com.github.mustachejava.Mustache
import java.io.File
import java.io.PrintWriter
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path


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