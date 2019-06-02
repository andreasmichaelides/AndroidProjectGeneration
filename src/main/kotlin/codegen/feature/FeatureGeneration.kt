package codegen.feature

import codegen.model.MustacheModel
import codegen.tools.findLastActivityEntryLine
import com.github.mustachejava.DefaultMustacheFactory
import com.github.mustachejava.Mustache
import com.google.common.collect.Iterables.skip
import com.google.common.collect.Multiset
import getFileWriter
import org.xmlpull.mxp1.MXParserFactory
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import toLowerCameCase
import toLowerUnderscore
import writeMustacheTemplate
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.StringWriter




fun createFeature(
    featureName: String,
    packageName: String,
    corePackageName: String,
    mustacheFactory: DefaultMustacheFactory
) {
    val androidAppModulePath = "src/main/java/"
    val androidAppResourcesPath = "src/main/"
    val packagePath = androidAppModulePath + packageName.replace(".", "/")
    val featurePackagePath =
        "$packagePath${File.separator}features${File.separator}${featureName.toLowerCase()}${File.separator}presentation"
    val featureMustacheModels = listOf(
        FeatureViewModel(
            "${featureName}FsViewModel",
            packageName,
            corePackageName,
            mustacheFactory.compile("featureViewModel.mustache"),
            featureName,
            featureName.toLowerCase()
        ),
        FeatureActivity(
            "${featureName}Activity",
            packageName,
            corePackageName,
            mustacheFactory.compile("activity.mustache"),
            featureName,
            featureName.toLowerCase(),
            featureName.toLowerUnderscore(),
            featureName.toLowerCameCase()
        ),
        FeatureUiModel(
            "${featureName}UiModel",
            packageName,
            corePackageName,
            mustacheFactory.compile("uiModel.mustache"),
            featureName,
            featureName.toLowerCase()
        )
    )

    featureMustacheModels.forEach {
        val kotlinClassWriter = getFileWriter(featurePackagePath, it.className, ".kt")
        writeMustacheTemplate(it.mustache, it, kotlinClassWriter)
    }

    writeActivityLayoutFile(androidAppResourcesPath, mustacheFactory, featureName)
    addActivityToManifest(androidAppResourcesPath, mustacheFactory, featureName)
}

fun addActivityToManifest(
    androidAppResourcesPath: String,
    mustacheFactory: DefaultMustacheFactory,
    featureName: String
) {
    val mustache = mustacheFactory.compile("AndroidManifest_ActivityPartial.xml.mustache")
    val mustacheModel = Manifest(featureName, "", "", mustache, featureName.toLowerCase())
//    val kotlinClassWriter = getFileWriter(androidAppResourcesPath, "AndroidManifest", ".xml")

    val writer = StringWriter()
    val activityEntry = mustache.execute(writer, mustacheModel)
    val activityEntryLine = findLastActivityEntryLine(androidAppResourcesPath) + 1

//    System.out.println("Linenumber: $lineNumber")
}





fun writeActivityLayoutFile(androidAppModulePath: String, mustacheFactory: DefaultMustacheFactory, featureName: String) {
    val resourcesPath = "$androidAppModulePath/res/layout"
    val mustache = mustacheFactory.compile("activityLayout.mustache")
    val mustacheModel = MustacheModel("", "", "", mustache)
    val kotlinClassWriter = getFileWriter(resourcesPath, "activity_${featureName.toLowerUnderscore()}", ".xml")
    writeMustacheTemplate(mustache, mustacheModel, kotlinClassWriter)
}


data class FeatureViewModel(
    override val className: String,
    override val packageName: String,
    override val corePackageName: String,
    override val mustache: Mustache,
    val featureName: String,
    val featureNameLowerCase: String
) : MustacheModel(className, packageName, corePackageName, mustache)

data class FeatureUiModel(
    override val className: String,
    override val packageName: String,
    override val corePackageName: String,
    override val mustache: Mustache,
    val featureName: String,
    val featureNameLowerCase: String
) : MustacheModel(className, packageName, corePackageName, mustache)

data class FeatureActivity(
    override val className: String,
    override val packageName: String,
    override val corePackageName: String,
    override val mustache: Mustache,
    val featureName: String,
    val featureNameLowerCase: String,
    val featureNameCamelCaseLower: String,
    val featureNameLowerUnderscore: String
) : MustacheModel(className, packageName, corePackageName, mustache)

data class Manifest(
    override val className: String,
    override val packageName: String,
    override val corePackageName: String,
    override val mustache: Mustache,
    val featureNameLowerCase: String
) : MustacheModel(className, packageName, corePackageName, mustache)