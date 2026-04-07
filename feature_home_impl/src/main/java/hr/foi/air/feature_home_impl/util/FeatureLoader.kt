package hr.foi.air.feature_home_impl.util

import android.content.Context
import android.content.pm.PackageManager
import hr.foi.air.core.feature.ExpenseViewFeature

object FeatureLoader {

    fun load(context: Context): List<ExpenseViewFeature> {
        val features = mutableListOf<ExpenseViewFeature>()

        val appInfo = context.packageManager
            .getApplicationInfo(
                context.packageName,
                PackageManager.GET_META_DATA
            )

        val metaData = appInfo.metaData

        metaData?.keySet()?.forEach { key ->
            if (key.startsWith("expense_feature")) {

                val className = metaData.getString(key)

                val clazz = Class.forName(className!!)
                val instance = clazz.getDeclaredConstructor().newInstance()

                features.add(instance as ExpenseViewFeature)
            }
        }

        return features
    }
}