dev-repl:
	lein with-profile +dev,+dev-local repl

build:
	lein with-profile +prod uberjar
	mv target/uberjar/*.jar target/uberjar/backend-standalone.jar
	docker build -t hsbackend .

run-prod:
	docker run \
	--env DATABASE_HOST=192.168.1.11 \
	--env DATABASE_PORT=5444 \
	--env DATABASE_NAME=health_samurai_app \
	--env DATABASE_USERNAME=postgres \
	--env DATABASE_PASSWORD=postgres \
	-p 8080:80 \
	hsbackend
