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
        println('plugin ready to packaging apk...')
//        project.extensions.create()
    }
}
