DOCKER:=$(shell grep docker /proc/self/cgroup)

PROJECT_NAME := IndexTracker

install:
ifdef DOCKER
	@$(MAKE) prep_install
	@$(MAKE) prep_compile
	@$(MAKE) compile
else
	@echo \"make install\" will only work inside docker!!!
endif

run:
ifdef DOCKER
	@echo -e "\n...Running $(PROJECT_NAME)..."
	java -Dfile.encoding=UTF-8 -cp "classes:lib/*" com.surenderthakran.indextracker.App
else
	@echo \"make run\" will only work inside docker!!!
endif

test:
	@echo -e "\n...Preparing to test $(PROJECT_NAME)..."
	@$(MAKE) compile
	java -Dfile.encoding=UTF-8 -cp "classes:lib/*" org.junit.runner.JUnitCore com.surenderthakran.indextracker.net.RouterTest

recompile:
	@$(MAKE) prep_compile
	@$(MAKE) format
	@$(MAKE) compile

reload:
	@$(MAKE) prep_compile
	@$(MAKE) format
	@$(MAKE) compile
	@$(MAKE) run

prep_install:
	@echo -e "\n...Preparing to install $(PROJECT_NAME)..."
	@mkdir -p classes

prep_compile:
	@echo -e "\n...Preparing to compile $(PROJECT_NAME)..."
	@rm -rf classes/*
	find -name "*.java" > sources.txt

format:
	@echo -e "\n...Formatting $(PROJECT_NAME)..."
	java -jar lib/google-java-format-1.7-all-deps.jar --replace @sources.txt

compile:
	@echo -e "\n...Compiling $(PROJECT_NAME)..."
	javac -Xdiags:verbose -Xlint:unchecked -classpath "lib/*" -d classes @sources.txt
