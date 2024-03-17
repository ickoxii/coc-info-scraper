JAR := target/coc-info-scraper-0.0.1-SNAPSHOT.jar

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
