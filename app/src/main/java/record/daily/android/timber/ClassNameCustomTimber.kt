package record.daily.android.timber

import timber.log.Timber

class ClassNameCustomTimber : Timber.DebugTree() {

    override fun createStackElementTag(element: StackTraceElement): String =
        "${element.fileName}:${element.lineNumber}#${element.methodName}"
}
