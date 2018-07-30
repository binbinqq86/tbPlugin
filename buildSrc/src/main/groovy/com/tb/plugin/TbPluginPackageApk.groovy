package com.tb.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * @author tb
 * @time 2018/7/3 下午5:20
 * @des 打包apk插件
 */
class TbPluginPackageApk implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.extensions.create("packageConfig", PackageExtension)

        project.android.applicationVariants.all{
            def variantName = it.name.capitalize()
            PackageTask task = project.tasks.create("assemble${variantName}Package", PackageTask.class)
            task.targetProject = project
            task.variant = it

            //依赖assemble，需要先编译出所有的包
            task.dependsOn it.assemble

            it.outputs.all{
                println "appName>>>$it.outputFileName"
            }
        }
    }
}
