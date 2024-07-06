这是一个实验性质的探索project leyden的启动性能和预热性能

如果你想要在你的本机上建议至少使用Oracle graalvm 22 以及 最新的leyden仓库的代码自行构建一个JDK

本项目一般是先用Oracle graalvm 22 进行编译和native-image 再使用leyden进行训练

最后比较native，uber-jar以及leyden预训练过的性能

比较用例包含莱布尼茨算法计算pi，加载class，启动类优化，代理类，lambda表达式加载优化作为基础的性能对比

进阶有SpringBoot的Uber jar,fast-jar,leyden的对比

Quarkus也是如此