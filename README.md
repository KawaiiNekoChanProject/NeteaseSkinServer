<div align="center"><h1>NeteaseSkinServer</h1></div>

一个简单的实现

允许**非Netease版Minecraft**进入**Netease版Minecraft**的服务器时显示玩家皮肤


# 快速配置 (使用公共服务)

1.为你的 Minecraft 实例安装 [CustomSkinLoader](https://modrinth.com/mod/customskinloader) 模组, 并启动一次游戏以生成默认配置

2.进入游戏根目录 `/CustomSkinLoader/CustomSkinLoader.json`(未开启版本隔离) 或 `/versions/<你的游戏版本>/CustomSkinLoader/CustomSkinLoader.json`(开启版本隔离) 并打开此文件

3.将下方配置添加到 `loadlist` 最前方

````
    {
      "name": "NeteaseSkin",
      "type": "Legacy",
      "checkPNG": false,
      "model": "auto",
      "skin": "https://skin.nekocurit.net/download/{STANDARD_UUID}"
    }
````

**本服务为公益性服务, 因此不保障 SLA (7/24在线), 请不要滥用**

# 本地部署配置

1.为你的 Minecraft 实例安装 [CustomSkinLoader](https://modrinth.com/mod/customskinloader) 模组, 并启动一次游戏以生成默认配置

2.进入游戏根目录 `/CustomSkinLoader/CustomSkinLoader.json`(未开启版本隔离) 或 `/versions/<你的游戏版本>/CustomSkinLoader/CustomSkinLoader.json`(开启版本隔离) 并打开此文件

3.将下方配置添加到 `loadlist` 最前方

````
    {
      "name": "NeteaseSkin",
      "type": "Legacy",
      "checkPNG": false,
      "model": "auto",
      "skin": "http://localhost:8080/download/{STANDARD_UUID}"
    }
````

4.启动 NeteaseSkinServer 并进行相关配置 注意不要和脱盒用同一个账号 (会顶号)
