export M2_HOME=/usr/local/apache-maven/apache-maven-3.3.3
export M2=$M2_HOME/bin
export MAVEN_OPTS="-Xms256m -Xmx512m"
export PATH=$M2:$PATH

git remote update

if [ $(git rev-parse HEAD) != $(git rev-parse origin) ]
then
	git fetch
	git reset --hard origin/master
	
	mvn clean
	mvn install
	ant
	
	cp src/main/webapp/WEB-INF/gamma-web.xml			src/main/webapp/WEB-INF/web.xml
	cp src/main/webapp/WEB-INF/gamma-appengine-web.xml	src/main/webapp/WEB-INF/appengine-web.xml
	
	# Update prod-pratilipi/gamma
	mvn appengine:update -Dapp.id=prod-pratilipi -Dapp.module=gamma
	
	
	cp src/main/webapp/WEB-INF/gamma-android-web.xml			src/main/webapp/WEB-INF/web.xml
	cp src/main/webapp/WEB-INF/gamma-android-appengine-web.xml	src/main/webapp/WEB-INF/appengine-web.xml
	
	# Update prod-pratilipi/gamma
	mvn appengine:update -Dapp.id=prod-pratilipi -Dapp.module=gamma-android
fi
