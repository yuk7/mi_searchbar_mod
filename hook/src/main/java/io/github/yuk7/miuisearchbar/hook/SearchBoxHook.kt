package io.github.yuk7.miuisearchbar.hook

import android.content.Context
import android.content.Intent
import androidx.annotation.Keep
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodReplacement
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage
import io.github.yuk7.miuisearchbar.model.Constants.MIUI_HOME_PACKAGE

@Keep
class SearchBoxHook : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam?) {
        if (lpparam?.packageName != MIUI_HOME_PACKAGE) {
            return
        }
        XposedHelpers.findAndHookMethod(
            SEARCHBAR_DESKTOP_LAYOUT_CLASS,
            lpparam.classLoader,
            SEARCH_METHOD,
            java.lang.String::class.java,
            java.lang.String::class.java,
            object : XC_MethodReplacement() {
                override fun replaceHookedMethod(param: MethodHookParam) {
                    val context =
                        XposedHelpers.getObjectField(param.thisObject, CONTEXT_FIELD) as Context
                    runCatching {
                        context.startActivity(
                            Intent(INTENT_SEARCH_ACTION)
                                .addFlags(
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                )
                        )
                    }.onFailure {
                        XposedBridge.invokeOriginalMethod(
                            param.method,
                            param.thisObject,
                            param.args
                        )
                    }
                }
            }
        )
    }

    companion object {
        private const val INTENT_SEARCH_ACTION = "android.search.action.GLOBAL_SEARCH"

        private const val SEARCHBAR_DESKTOP_LAYOUT_CLASS =
            "$MIUI_HOME_PACKAGE.launcher.SearchBarDesktopLayout"
        private const val SEARCH_METHOD = "launchGlobalSearch"
        private const val CONTEXT_FIELD = "mLauncher"
    }
}