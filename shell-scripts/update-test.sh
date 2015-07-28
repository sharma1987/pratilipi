git remote update

if [ $(git rev-parse HEAD) != $(git rev-parse origin) ]
then
	git fetch
	git reset --hard origin/master
	
	mvn clean
	mvn install
	ant
	
	# Update devo-pratilipi/default
	mvn appengine:update -Dapp.id=devo-pratilipi -Dapp.module=default
	
	# Update prod-pratilipi/gamma
	mvn appengine:update -Dapp.id=prod-pratilipi -Dapp.module=gamma
	
	cp src/main/webapp/WEB-INF/worker-web.xml			src/main/webapp/WEB-INF/web.xml
	cp src/main/webapp/WEB-INF/worker-appengine-web.xml	src/main/webapp/WEB-INF/appengine-web.xml
	cp src/main/webapp/WEB-INF/worker-queue.xml			src/main/webapp/WEB-INF/queue.xml
	
	# Update devo-pratilipi/worker
	mvn appengine:update -Dapp.id=devo-pratilipi -Dapp.module=worker
	
fi