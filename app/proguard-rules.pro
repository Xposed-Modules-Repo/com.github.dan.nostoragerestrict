# Keep the Xposed entry point and all its members (constructor, methods)
-keep class com.github.dan.NoStorageRestrict.XposedInit {
    *;
}

# Keep all classes that implement Xposed interfaces
-keep class * implements de.robv.android.xposed.IXposedHookLoadPackage {
    public <init>();
    public void handleLoadPackage(de.robv.android.xposed.callbacks.XC_LoadPackage$LoadPackageParam);
}

# Keep the Xposed API itself
-keep class de.robv.android.xposed.** { *; }
-dontwarn de.robv.android.xposed.**

# Keep other hook classes in the same package to ensure they are not renamed
# which makes debugging hooks easier and avoids issues if they are called via reflection.
-keep class com.github.dan.NoStorageRestrict.* {
    *;
}
