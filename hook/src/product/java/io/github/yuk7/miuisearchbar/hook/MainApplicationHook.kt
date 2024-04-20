package io.github.yuk7.miuisearchbar.hook

import androidx.annotation.Keep
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodReplacement
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage
import io.github.yuk7.miuisearchbar.model.Constants.APP_PACKAGE_NAME

@Keep
class MainApplicationHook : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam?) {
        if (lpparam?.packageName != APP_PACKAGE_NAME) {
            return
        }
        XposedHelpers.findAndHookMethod(
            MAIN_APPLICATION_CLASS,
            lpparam.classLoader,
            FRAMEWORK_VERSION_METHOD,
            XC_MethodReplacement.returnConstant(XposedBridge.getXposedVersion())
        )
    }

    companion object {
        const val MAIN_APPLICATION_CLASS = "$APP_PACKAGE_NAME.MainApplication"
        const val FRAMEWORK_VERSION_METHOD = "getFrameworkVersion"
    }
}