package codegen.feature

import codegen.model.MustacheModel
import codegen.tools.*
import com.github.mustachejava.DefaultMustacheFactory
import java.io.File
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
        "$packagePath${File.separator}features${File.separator}${featureName.toLowerCase()}"

    val featurePresentationPath = "$featurePackagePath${File.separator}presentation"

    val featureMustacheModels = listOf(
        FeatureViewModel(
            "${featureName}FsViewModel",
            packageName,
            corePackageName,
            mustacheFactory.compile("featureViewModel.mustache"),
            featurePresentationPath,
            featureName,
            featureName.toLowerCase()
        ),
        FeatureActivity(
            "${featureName}Activity",
            packageName,
            corePackageName,
            mustacheFactory.compile("activity.mustache"),
            featurePresentationPath,
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
            featurePresentationPath,
            featureName,
            featureName.toLowerCase()
        ),
        Module(
            "${featureName}Module",
            packageName,
            corePackageName,
            mustacheFactory.compile("FeatureModule.kt.mustache"),
            featurePackagePath,
            featureName,
            featureName.toLowerCase(),
            featureName.toLowerCameCase()
        )
    )

    featureMustacheModels.forEach {
        val kotlinClassWriter = getFileWriter(it.filePath, it.className, ".kt")
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
    val mustacheModel = Manifest(featureName, "", "", mustache, "", featureName, featureName.toLowerCase())

    val writer = StringWriter()
    val activityEntry = mustache.execute(writer, mustacheModel)
    val activityEntryLine = findLastActivityEntryLine(androidAppResourcesPath) + 1
    val manifestFile = getManifestFile(androidAppResourcesPath)

    val mutableList = manifestFile.readLines().toMutableList()
    mutableList.addAll(activityEntryLine, activityEntry.toString().lines())

    writeFile(manifestFile.toPath(), mutableList)
}

fun writeActivityLayoutFile(androidAppModulePath: String, mustacheFactory: DefaultMustacheFactory, featureName: String) {
    val resourcesPath = "$androidAppModulePath/res/layout"
    val mustache = mustacheFactory.compile("activityLayout.mustache")
    val mustacheModel = MustacheModel("", "", "", mustache, "")
    val kotlinClassWriter = getFileWriter(resourcesPath, "activity_${featureName.toLowerUnderscore()}", ".xml")
    writeMustacheTemplate(mustache, mustacheModel, kotlinClassWriter)
}