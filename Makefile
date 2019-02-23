DOCKER:=$(shell grep docker /proc/self/cgroup)

PROJECT_NAME := IndexTracker

install:
ifdef DOCKER
	$(MAKE) prep_install
	$(MAKE) compile
else
	@echo \"make install\" will only work inside docker!!!
endif

run:
ifdef DOCKER
	@echo "\n...Running $(PROJECT_NAME)..."
	java -cp classes com.surenderthakran.indextracker.Server
else
	@echo \"make run\" will only work inside docker!!!
endif

prep_install:
	@echo "\n...Preparing to install $(PROJECT_NAME)..."
	mkdir -p classes

compile:
	@echo "\n...Compiling $(PROJECT_NAME)..."
	rm -rf classes/*
	find -name "*.java" > sources.txt
	javac -d classes @sources.txt
