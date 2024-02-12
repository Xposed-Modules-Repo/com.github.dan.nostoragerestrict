package com.github.dan.NoStorageRestrict;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.findMethodBestMatch;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.callMethod;
import static de.robv.android.xposed.XposedHelpers.findAndHookConstructor;

public class XposedInit implements IXposedHookLoadPackage {
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {

        if (Constants.DOCUMENTSUI_PACKAGE_NAME.equals(lpparam.packageName)) {
            new ManagedModehooks().handleLoadPackage(lpparam);
        }

        if (Constants.DOCUMENTSUI_GOOGLE_PACKAGE_NAME.equals(lpparam.packageName)) {
            new ManagedModehooks().handleLoadPackage(lpparam);
        }

        if (Constants.STORAGEMANAGER_NAME.equals(lpparam.packageName)) {
            new FolderRestrictionhook().handleLoadPackage(lpparam);
        }

        if (Constants.STORAGEMANAGER_NAME.equals(lpparam.packageName)) {
            new DocumentHideBypass().handleLoadPackage(lpparam);
        }
    }
}