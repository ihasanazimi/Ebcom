package com.example.ebcom

import android.app.Application
import android.content.Context
import android.os.Handler
import com.example.ebcom.db.RoomDB
import com.example.ebcom.repository.RestaurantRepositoryImpl
import com.example.ebcom.repository.restaurant_datasource.local.RestaurantLocalDataSource
import com.example.ebcom.repository.restaurant_datasource.remote.RestaurantRemoteDataSource
import com.example.ebcom.services.http.apiService
import com.example.ebcom.ui.fragments.restaurants.RestaurantsVM
import com.example.ebcom.utility.util.ThemeUtils
import com.example.ebcom.utility.util.localizedContext
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

class ApplicationLoader : Application() {

    companion object {
        var context: Context? = null
        lateinit var applicationHandler: Handler
    }

    override fun onCreate() {
        super.onCreate()

        // init config for app
        localizedContext(this)
        context = this.applicationContext
        applicationHandler = Handler(this.mainLooper)
        ThemeUtils.changeTheme(false)
        RoomDB.getDataBase(this@ApplicationLoader)

        // 1- koin (Dependency Injection)
        val modules = module {
            single { apiService }
            single { RoomDB.getDataBase(this@ApplicationLoader) }
            single { RestaurantRepositoryImpl(RestaurantRemoteDataSource(get()), RestaurantLocalDataSource(get())) }
            viewModel<RestaurantsVM> { RestaurantsVM(get()) }
        }
        // 2- then -> start koin
        startKoin {
            androidContext(this@ApplicationLoader)
            modules(modules)
        }


    }
}