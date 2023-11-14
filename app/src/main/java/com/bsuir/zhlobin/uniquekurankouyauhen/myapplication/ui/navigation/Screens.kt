package com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.ui.navigation

import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.R



/**
 * This class described screen navigation objects
 * @param screenName - deeplink screen representation
 * @param titleResourceId - resource to name tabbar or appbar navigation title
 */
sealed class Screen(val screenName: String, val titleResourceId: Int) {
    object Home: Screen("Home", -1)
    object Weather: Screen("Weather", R.string.weather)
    object Favorite: Screen("Favorite", -1)
    object About: Screen("About App", R.string.aboutApp)

}