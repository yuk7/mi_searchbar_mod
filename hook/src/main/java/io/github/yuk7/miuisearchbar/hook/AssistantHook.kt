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
class AssistantHook : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam?) {
        if (lpparam?.packageName != MIUI_HOME_PACKAGE) {
            return
        }
        XposedHelpers.findAndHookMethod(
            SEARCHBAR_XIAOAI_LAYOUT_CLASS,
            lpparam.classLoader,
            XIAOAI_METHOD,
            object : XC_MethodReplacement() {
                override fun replaceHookedMethod(param: MethodHookParam) {
                    val context =
                        XposedHelpers.getObjectField(param.thisObject, CONTEXT_FIELD) as Context
                    runCatching {
                        context.startActivity(
                            Intent(Intent.ACTION_VOICE_COMMAND)
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
        private const val SEARCHBAR_XIAOAI_LAYOUT_CLASS =
            "$MIUI_HOME_PACKAGE.launcher.SearchBarXiaoaiLayout"
        private const val XIAOAI_METHOD = "launchXiaoAi"
        private const val CONTEXT_FIELD = "mLauncher"
    }
}