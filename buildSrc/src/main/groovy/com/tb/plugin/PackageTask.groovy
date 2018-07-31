package com.tb.plugin

import com.android.build.gradle.api.ApplicationVariant
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

class PackageTask extends DefaultTask {

    @Input
    public Project targetProject

    @Input
    public ApplicationVariant variant

    def appVersion
    /**
     * 360加固工具存放目录
     */
    def JIAGU_ROOT_PATH
    /**
     * 加固完成后存放apk的目录
     */
    def JIAGU_COMPLETED_PATH
    /**
     * 用来存放渠道包的目录
     */
    def OUTPUT_CHANNEL_APK_PATH

    @TaskAction
    void packageTask() {
        //根据当前运行的系统判断使用哪个版本加固工具
        String os = System.getProperties().getProperty("os.name")
        println "os is: $os"
        if (os.toLowerCase().startsWith('mac')) {
            JIAGU_ROOT_PATH = './buildSrc/tools/360mac/jiagu'
        } else if (os.toLowerCase().startsWith('linux')) {
//            JIAGU_ROOT_PATH = './buildSrc/tools/360linux'
        } else {
            new RuntimeException('os not support')
            return
        }

        appVersion = targetProject.packageConfig.appVersion
        JIAGU_COMPLETED_PATH = "$JIAGU_ROOT_PATH/completed"
        OUTPUT_CHANNEL_APK_PATH = "./buildSrc/output"

        variant.outputs.all {
            def apkFile = it.outputFile
            if (apkFile == null || !apkFile.exists()) {
                throw new GradleException("$apkFile doesn't exists!!!")
            }

            def out = new StringBuilder()
            def err = new StringBuilder()

            //先移除目录，再新建
            def rm = "rm -rf $JIAGU_COMPLETED_PATH".execute()
            rm.waitForProcessOutput(out, err)
            println "tb===rm -rf $JIAGU_COMPLETED_PATH>>>$out>>>$err"
            def mkdir = "mkdir $JIAGU_COMPLETED_PATH".execute()
            mkdir.waitForProcessOutput()
            println "tb===mkdir $JIAGU_COMPLETED_PATH>>>$out>>>$err"

            //先登陆17348503014 tb1234567890会保存到db数据库，登陆一次即可
            //加固(必须先签名apk才能加固)，加固后需要重新签名
            //使用360自动渠道包+自动签名，需要先导入渠道信息和签名信息

            // 保证output文件夹存在
            "rm -rf $OUTPUT_CHANNEL_APK_PATH".execute().waitForProcessOutput(out, err)
            "mkdir $OUTPUT_CHANNEL_APK_PATH".execute().waitForProcessOutput(out, err)
            "chmod 777 $OUTPUT_CHANNEL_APK_PATH".execute().waitForProcessOutput(out, err)

            def cmd = "java -jar $JIAGU_ROOT_PATH/jiagu.jar -jiagu $apkFile $OUTPUT_CHANNEL_APK_PATH -autosign -automulpkg".execute()
            cmd.in.eachLine {
                println "tb===>>>$it"
            }

            //使用分步方式：加固、签名、渠道包========================================================================================
//            def jg = "java -jar $JIAGU_ROOT_PATH/jiagu.jar -jiagu $apkFile $JIAGU_COMPLETED_PATH".execute()
//            jg.in.eachLine {
//                println "tb>>>===$it"
//            }

            //此处也可以先签名，然后使用美团的瓦力去打渠道包，不过不是写入manifest文件的meta-data中，可以用sdk直接获取渠道号
//            def ls="ls $JIAGU_COMPLETED_PATH".execute()
//            ls.waitForProcessOutput(out,err)
//            println "tb===已加固文件：$out>>>$err"
        }

    }
}
