package io.github.yuk7.miuisearchbar.hook

import android.content.Context
import android.content.Intent
import androidx.annotation.Keep
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodReplacement
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage
import io.github.yuk7.miuisearchbar.hook.Constants.GOOGLE_QSB_PACKAGE
import io.github.yuk7.miuisearchbar.hook.Constants.GOOGLE_VOICE_ACTIVITY
import io.github.yuk7.miuisearchbar.hook.utils.PreferenceUtils
import io.github.yuk7.miuisearchbar.model.AssistantType
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
                        when (PreferenceUtils().getAssistantType()) {
                            AssistantType.DEFAULT -> {
                                XposedBridge.invokeOriginalMethod(
                                    param.method,
                                    param.thisObject,
                                    param.args
                                )
                            }

                            AssistantType.OS_DEFAULT -> {
                                context.startActivity(
                                    Intent(Intent.ACTION_VOICE_COMMAND)
                                        .addFlags(
                                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        )
                                )
                            }

                            AssistantType.GOOGLE -> {
                                context.startActivity(
                                    Intent(Intent.ACTION_VOICE_COMMAND)
                                        .addFlags(
                                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        )
                                        .setPackage(GOOGLE_QSB_PACKAGE)
                                )
                            }

                            AssistantType.GOOGLE_VOICE_SEARCH -> {
                                context.startActivity(
                                    Intent(Intent.ACTION_MAIN)
                                        .addFlags(
                                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        )
                                        .setClassName(GOOGLE_QSB_PACKAGE, GOOGLE_VOICE_ACTIVITY)
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
        private const val SEARCHBAR_XIAOAI_LAYOUT_CLASS =
            "$MIUI_HOME_PACKAGE.launcher.SearchBarXiaoaiLayout"
        private const val XIAOAI_METHOD = "launchXiaoAi"
        private const val CONTEXT_FIELD = "mLauncher"
    }
}