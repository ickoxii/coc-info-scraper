JAR := target/coc-info-scraper-1.0.0-SNAPSHOT.jar

build:
	mvn package
.PHONY: build

run:
	java -jar $(JAR)
.PHONY: run

br:
	make build
	make run
.PHONY: br
