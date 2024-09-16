package io.github.yuk7.miuisearchbar.hook

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.annotation.Keep
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodReplacement
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage
import io.github.yuk7.miuisearchbar.hook.Constants.GOOGLE_QSB_PACKAGE
import io.github.yuk7.miuisearchbar.hook.Constants.INTENT_GLOBAL_SEARCH_ACTION
import io.github.yuk7.miuisearchbar.hook.utils.PreferenceUtils
import io.github.yuk7.miuisearchbar.model.Constants.MIUI_HOME_PACKAGE
import io.github.yuk7.miuisearchbar.model.SearchBoxType

@Keep
class SearchBoxHook : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam?) {
        if (lpparam?.packageName != MIUI_HOME_PACKAGE) {
            return
        }
        XposedHelpers.findAndHookMethod(
            SEARCHBAR_DESKTOP_LAYOUT_CLASS,
            lpparam.classLoader,
            ONCLICK_METHOD,
            View::class.java,
            object : XC_MethodReplacement() {
                override fun replaceHookedMethod(param: MethodHookParam) {
                    val context =
                        XposedHelpers.getObjectField(param.thisObject, CONTEXT_FIELD) as Context
                    runCatching {
                        when (PreferenceUtils().getSearchBoxType()) {
                            SearchBoxType.DEFAULT -> {
                                XposedBridge.invokeOriginalMethod(
                                    param.method,
                                    param.thisObject,
                                    param.args
                                )
                            }

                            SearchBoxType.OS_DEFAULT -> {
                                context.startActivity(
                                    Intent(INTENT_GLOBAL_SEARCH_ACTION)
                                        .addFlags(
                                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        )
                                )
                            }

                            SearchBoxType.GOOGLE -> {
                                context.startActivity(
                                    Intent(INTENT_GLOBAL_SEARCH_ACTION)
                                        .addFlags(
                                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        )
                                        .setPackage(GOOGLE_QSB_PACKAGE)
                                )
                            }
                        }
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
        private const val SEARCHBAR_DESKTOP_LAYOUT_CLASS =
            "$MIUI_HOME_PACKAGE.launcher.SearchBarDesktopLayout"
        private const val ONCLICK_METHOD = "onClick"
        private const val CONTEXT_FIELD = "mLauncher"
    }
}