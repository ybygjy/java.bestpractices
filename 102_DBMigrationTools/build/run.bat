@echo 启动数据迁移工具
java -cp ./DBMigrationTools.jar;./lib/ojdbc6.jar;./lib/sqljdbc4.jar org.ybygjy.ui.UIDataMigration
pause