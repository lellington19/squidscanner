param([System.String]$a, [System.String]$f) 



set-item -path Env:CLASSPATH -value "C:/Classes/KNW/RoboticsLibrary;C:'/Program Files/'Java/jre1.8.0_60/lib/ext;C:/Classes/KNW/RoboticsLibrary/RXTXRobot.jar"
if($a -eq "run"){
    java $f}
if($a -eq "comp"){
    javac $f".java"}