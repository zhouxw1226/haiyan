
call mvn deploy:deploy-file -Durl=http://1.1.9.4:9000/nexus/content/repositories/thirdparty-releases -Dversion=0.0.1-SNAPSHOT -Dpackaging=jar -DgroupId=haiyan -DartifactId=haiyan-orm -Dfile=haiyan-orm.jar

pause