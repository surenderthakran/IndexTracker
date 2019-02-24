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
	@echo "Running $(PROJECT_NAME)..."
	java -cp "classes:lib/*" com.surenderthakran.indextracker.Server
else
	@echo \"make run\" will only work inside docker!!!
endif

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
	@echo "Preparing to install $(PROJECT_NAME)..."
	@mkdir -p classes

prep_compile:
	@echo "Preparing to compile $(PROJECT_NAME)..."
	@rm -rf classes/*
	find -name "*.java" > sources.txt

format:
	@echo "Formatting $(PROJECT_NAME)..."
	java -jar lib/google-java-format-1.7-all-deps.jar --replace @sources.txt

compile:
	@echo "Compiling $(PROJECT_NAME)..."
	javac -Xdiags:verbose -classpath "lib/*" -d classes @sources.txt
