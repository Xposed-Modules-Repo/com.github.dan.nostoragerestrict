package com.github.dan.NoStorageRestrict;

import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import de.robv.android.xposed.XposedHelpers;

public class ManagedModehooks implements IXposedHookLoadPackage {

    private static final String DOCUMENT_STACK = "com.android.documentsui.base.DocumentStack";
    public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
        XposedHelpers.findAndHookMethod("com.android.documentsui.ActivityConfig", lpparam.classLoader, "managedModeEnabled", DOCUMENT_STACK, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                        param.setResult(true);
                    }
                });
    }
}
