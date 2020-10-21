package com.ghl.sensors.plugin

class AutoMatchUtils {
    public String className
    boolean isShouldModify = false
    boolean isSensorsDataAPI = false
    boolean isSensorsDataUtils = false
    boolean isSALog = false
    def methodCells = new ArrayList<AutoMethodCell>()

    AutoMatchUtils(String className) {
        this.className = className
        isSensorsDataAPI = (className == 'com.sensorsdata.analytics.android.sdk.SensorsDataAPI')
        isSensorsDataUtils = (className == 'com.sensorsdata.analytics.android.sdk.util.SensorsDataUtils')
        isSALog = (className == 'com.sensorsdata.analytics.android.sdk.SALog')
    }

    boolean isSDKFile() {
        return isSALog || isSensorsDataAPI || isSensorsDataUtils
    }

    boolean isLeanback() {
        return className.startsWith("android.support.v17.leanback") || className.startsWith("androidx.leanback")
    }

    boolean isAndroidGenerated() {
        return className.contains('R$') ||
                className.contains('R2$') ||
                className.contains('R.class') ||
                className.contains('R2.class') ||
                className.contains('BuildConfig.class')
    }

}