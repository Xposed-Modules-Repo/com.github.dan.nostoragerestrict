package com.github.dan.NoStorageRestrict;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class FolderRestrictionhookA14 {
        public void handleLoadPackage (XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {

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
        }

}
