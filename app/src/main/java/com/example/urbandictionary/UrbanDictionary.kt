package com.example.urbandictionary

import android.app.Application
import com.example.urbandictionary.data.network.NetworkComponent
import com.example.urbandictionary.data.network.NetworkModule

class UrbanDictionary: Application() {
    companion object {
        lateinit var dictionaryInstance: UrbanDictionary
    }
    private lateinit var networkComponent: NetworkComponent

    override fun onCreate() {
        super.onCreate()
        dictionaryInstance = this
        NetworkComponent.instance.let {
            if (it == null) {
                NetworkComponent.initNetworkComponent(NetworkModule())
            }
            dictionaryInstance.networkComponent = NetworkComponent.instance as NetworkComponent
            networkComponent.inject(this)
        }
    }
}
