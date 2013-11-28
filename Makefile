PACKAGES := Database UserInterface Gameplay lightracer
.PHONY: all clean dist check docs clean-docs $(PACKAGES)

BUILD_CLASS_PATH := "$(CLASS_PATH):lib/base64-2.3.7.jar:lib/sqlite-jdbc-3.7.2.jar:$(JAVA_HOME)/jre/lib/jfxrt.jar"

RESOURCE_PATH := res
SOURCE_PATH := src
CLASS_PATH := build
DOC_PATH := docs
DIST_PATH := dist


SRCFILES := $(shell find $(SOURCE_PATH) -type f -name "\*.java")
DATABASE_FILES := $(shell find $(SOURCE_PATH)/Database -type f -name "\*.java")
UI_FILES := $(shell find $(SOURCE_PATH) -type f -name "\*.java")


docs: 
	javadoc -sourcepath $(SOURCE_PATH) -classpath $(BUILD_CLASS_PATH) -d $(DOC_PATH) -author -doctitle "Light Racer - Team 6" $(PACKAGES)

clean-docs:
	rm -fr $(DOC_PATH)/*
