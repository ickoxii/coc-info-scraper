JAR := target/coc-info-scraper-0.0.1-SNAPSHOT.jar
OUT := target/output/logs

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

brp:
	mvn package
	java -jar $(JAR) > $(OUT)
	cat $(OUT)
.PHONY: brp
