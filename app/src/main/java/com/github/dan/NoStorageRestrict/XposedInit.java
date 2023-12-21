package com.github.dan.NoStorageRestrict;

import android.app.Application;
import android.app.Instrumentation;

import java.io.IOException;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.getIntField;
import static de.robv.android.xposed.XposedHelpers.getObjectField;
import static de.robv.android.xposed.XposedHelpers.callMethod;
import static de.robv.android.xposed.XposedHelpers.findMethodBestMatch;
import android.graphics.Color;
import android.widget.TextView;
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

import static de.robv.android.xposed.XposedHelpers.callMethod;
import static de.robv.android.xposed.XposedHelpers.findAndHookConstructor;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class XposedInit implements IXposedHookLoadPackage {
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {

        if (Constants.DOCUMENTSUI_PACKAGE_NAME.equals(lpparam.packageName)) {
            new ManagedModehooks().handleLoadPackage(lpparam);
        }

        if (Constants.DOCUMENTSUI_GOOGLE_PACKAGE_NAME.equals(lpparam.packageName)) {
            new ManagedModehooks().handleLoadPackage(lpparam);
        }

        if (Constants.STORAGEMANAGER_NAME.equals(lpparam.packageName)) {
            new FolderRestrictionhookA14().handleLoadPackage(lpparam);
        }

        if (Constants.STORAGEMANAGER_NAME.equals(lpparam.packageName))
            new FolderRestrictionhook().handleLoadPackage(lpparam);
        }

}