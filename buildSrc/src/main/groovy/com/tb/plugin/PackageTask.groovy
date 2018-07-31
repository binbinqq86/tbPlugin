package com.tb.plugin

import com.android.build.gradle.api.ApplicationVariant
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

class PackageTask extends DefaultTask {

    @Input
    public Project targetProject

    @Input
    public ApplicationVariant variant

    def appVersion

    @TaskAction
    void packageTask() {
        appVersion=targetProject.packageConfig.appVersion
        println "packaging...$targetProject.name...$variant.versionName...$variant.buildType.name...$variant.name"
    }
}
