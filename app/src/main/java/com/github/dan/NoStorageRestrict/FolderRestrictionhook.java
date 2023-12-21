package com.github.dan.NoStorageRestrict;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class FolderRestrictionhook {
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        try {
            XC_MethodHook.Unhook managedModeEnabled = XposedHelpers.findAndHookMethod(
                    "com.android.externalstorage.ExternalStorageProvider",
                    lpparam.classLoader,
                    "shouldBlockDirectoryFromTree", String.class,
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            param.setResult(false);
                        }
                    });
        } catch (NoSuchMethodError e) {
            XposedBridge.log("Trying B hook");
            try {
                XC_MethodHook.Unhook managedModeEnabledB = XposedHelpers.findAndHookMethod(
                        "com.android.externalstorage.ExternalStorageProvider",
                        lpparam.classLoader,
                        "shouldBlockFromTree", String.class,
                        new XC_MethodHook() {
                            @Override
                            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                                param.setResult(false);
                            }
                        });
            } catch (NoSuchMethodError eB) {
                XposedBridge.log("Both hooks failed");
                eB.printStackTrace(); // Print the stack trace for debugging purposes
            }
        }
    }
}
