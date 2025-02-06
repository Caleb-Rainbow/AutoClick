# AutoClick：一款强大的 Android 自动化点击工具

![GitHub license](https://img.shields.io/github/license/Caleb-Rainbow/AutoClick)
![GitHub stars](https://img.shields.io/github/stars/Caleb-Rainbow/AutoClick)
![GitHub forks](https://img.shields.io/github/forks/Caleb-Rainbow/AutoClick)
![GitHub issues](https://img.shields.io/github/issues/Caleb-Rainbow/AutoClick)
![GitHub last commit](https://img.shields.io/github/last-commit/Caleb-Rainbow/AutoClick)

## 项目简介

**AutoClick** 是一款基于 Android 平台的自动化点击工具，它允许用户通过录制和配置一系列的点击、滑动等操作，实现自动化执行任务。无需 Root 权限，简单易用，满足各种场景下的自动化需求。

## 主要特性

*   **自动化操作录制：** 用户可以通过简单的操作录制点击、滑动等动作序列。
*   **灵活的脚本配置：** 支持自定义脚本的名称、执行次数、随机延迟等参数。
*   **定时/循环执行：** 可以设置脚本的执行次数，也可配置为无限循环执行。
*   **随机延迟：** 支持设置随机延迟时间，模拟真实用户的操作习惯，避免被目标应用检测到。
*   **悬浮窗控制：** 提供悬浮窗界面，方便用户随时启动、停止或取消正在执行的自动化脚本。
*   **无障碍服务支持：** 基于 Android 无障碍服务实现，无需 Root 权限即可运行。
*   **Compose UI：** 使用 Jetpack Compose 构建 UI，现代化开发方式。
* **Koin依赖注入:** 使用Koin框架进行依赖注入。

## 技术栈

*   **Kotlin：** 主要开发语言。
*   **Jetpack Compose：** 构建现代 Android UI。
*   **Android Accessibility Service：** 提供无障碍服务。
* **Koin:** 用于依赖注入。
*   **Coroutine:** 用于异步操作。
*   **FloatingX:** 用于创建悬浮窗。
*   **Json:** 用于序列化和反序列化。

## 使用方法

1.  **安装应用：** 下载并安装 AutoClick APK 文件到你的 Android 设备。
2.  **开启无障碍服务：** 在系统设置中开启 AutoClick 的无障碍服务权限。
3.  **录制脚本：** 在应用中，开始录制你需要自动化的操作。
4.  **配置脚本：** 设置脚本的名称、延迟时间、执行次数等参数。
5.  **启动脚本：** 在悬浮窗界面启动已配置好的脚本。
6. **停止脚本：** 点击悬浮窗的停止按钮停止正在执行的脚本。
7. **取消脚本:** 点击悬浮窗的关闭按钮，关闭该脚本的悬浮窗