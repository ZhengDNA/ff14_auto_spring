# ff14_auto_spring
Spring 重构 C语言版的 ff14_auto

具有C语言版的所有功能，进一步支持 JSON 和 YAML 格式的曲谱文件。

和C语言版的不同之处是，它会判断连在一起的音符是否合法。如果不合法还会提醒你出错音符的具体位置，非常人性化。

C++编译动态链接库用的 IDE 是 DEV C++。

用 exe4j 打包成了 exe 文件。要注意的是，src 文件夹要和主程序同级，还有主程序要以管理员身份运行。

需要 Java11 运行环境，下载 jdk11，把里面的东西(和 bin 文件夹同级的东西)都放到 C:/jdk11 就行，不需要修改环境变量。

### JSON 示例

```json
[
  {
    "clef": "C",
    "bpm": 120,
    "notes": [
      "1",
      "1",
      "5",
      "5",
      "6",
      "6",
      "5"
    ]
  },
  {
    "clef": "D",
    "bpm": 120,
    "notes": [
      "4",
      "4",
      "3",
      "3",
      "2",
      "2",
      "1"
    ]
  }
]
```

### YAML 示例

```yaml
-
  clef: "C"
  bpm: 120
  notes: 
    - "1"
    - "2"
    - "3"
    - "4"
-
  clef: "D"
  bpm: 120
  notes:
    - "1"
    - "2"
    - "3"

```
手写的话，相比原格式和JSON，我更推荐写YAML。VS Code 装一个yaml插件就能自动缩进，自动补全 ‘-’，写起来非常舒服。

关于曲谱的更多信息请看 <a href="https://github.com/ChenzDNA/ff14_auto">ChenzDNA/ff14_auto</a>