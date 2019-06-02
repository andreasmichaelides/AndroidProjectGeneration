package codegen.model

import com.github.mustachejava.Mustache


open class MustacheModel(open val className: String,
                         open val packageName: String,
                         open val corePackageName: String,
                         open val mustache: Mustache
)