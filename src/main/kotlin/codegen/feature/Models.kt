package codegen.feature

import codegen.model.MustacheModel
import com.github.mustachejava.Mustache


data class FeatureViewModel(
    override val className: String,
    override val packageName: String,
    override val corePackageName: String,
    override val mustache: Mustache,
    override val filePath: String,
    val featureName: String,
    val featureNameLowerCase: String
) : MustacheModel(className, packageName, corePackageName, mustache, filePath)

data class FeatureUiModel(
    override val className: String,
    override val packageName: String,
    override val corePackageName: String,
    override val mustache: Mustache,
    override val filePath: String,
    val featureName: String,
    val featureNameLowerCase: String
) : MustacheModel(className, packageName, corePackageName, mustache, filePath)

data class FeatureActivity(
    override val className: String,
    override val packageName: String,
    override val corePackageName: String,
    override val mustache: Mustache,
    override val filePath: String,
    val featureName: String,
    val featureNameLowerCase: String,
    val featureNameCamelCaseLower: String,
    val featureNameLowerUnderscore: String
) : MustacheModel(className, packageName, corePackageName, mustache, filePath)

data class Manifest(
    override val className: String,
    override val packageName: String,
    override val corePackageName: String,
    override val mustache: Mustache,
    override val filePath: String,
    val featureName: String,
    val featureNameLowerCase: String
) : MustacheModel(className, packageName, corePackageName, mustache, filePath)

data class Module(
    override val className: String,
    override val packageName: String,
    override val corePackageName: String,
    override val mustache: Mustache,
    override val filePath: String,
    val featureName: String,
    val featureNameLowerCase: String,
    val featureNameCamelCaseLower: String
) : MustacheModel(className, packageName, corePackageName, mustache, filePath)