// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id(Plugins.application) version "7.3.0" apply false
    id(Plugins.library) version "7.3.0" apply false
    id(Plugins.kotlin) version "1.7.10" apply false
    id(Plugins.safeargas) version "2.4.2" apply false
    id(Plugins.googleHilt) version "2.43.2" apply false
}

tasks.register("clean",Delete::class){
    delete(rootProject.buildDir)
}