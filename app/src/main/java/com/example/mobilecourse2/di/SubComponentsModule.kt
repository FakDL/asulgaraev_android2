package com.example.mobilecourse2.di

import com.example.mobilecourse2.di.main.MainComponent
import dagger.Module

@Module(
    subcomponents = [
        MainComponent::class
    ]
)
class SubComponentsModule
