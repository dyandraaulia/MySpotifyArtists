package id.my.myspotifyartists.di

import id.my.myspotifyartists.data.Repository

object Injection {
    fun provideRepository(): Repository = Repository.getInstance()
}