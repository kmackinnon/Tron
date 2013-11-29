PACKAGES := database userinterface gameplay lightracer
.PHONY: all clean dist check clean-docs $(PACKAGES) todo libs

ifeq ($(OS),Windows_NT)
P_SEP := ;
JAVA_HOME := '$(JAVA_HOME)'
else
P_SEP := :
endif

RESOURCE_PATH := res
SOURCE_PATH := src
BUILD_PATH := build
DOC_PATH := docs
DIST_PATH := dist
LIB_PATH := lib
TEST_PATH := test

APP_CLASS := lightracer.LightRacer

DIST_LIBS := base64-2.3.7.jar sqlite-jdbc-3.7.2.jar

DIST_CLASS_PATH := lib/base64-2.3.7.jar$(P_SEP)lib/sqlite-jdbc-3.7.2.jar$(P_SEP)$(JAVA_HOME)/jre/lib/jfxrt.jar
BUILD_CLASS_PATH := $(BUILD_PATH)$(P_SEP)$(SOURCE_PATH)$(P_SEP)$(DIST_CLASS_PATH)
TEST_CLASS_PATH := lib/hamcrest-core-1.3.jar$(P_SEP)junit-4.11.jar$(P_SEP)$(BUILD_CLASS_PATH)

TEST_SOURCES := $(shell find $(TEST_PATH) -type f -name "*.java")
TEST_CLASSES := $(patsubst %.java,%.class,$(TEST_SOURCES))
SRCFILES := $(shell find $(SOURCE_PATH) -type f -name "*.java")
CLASSFILES := $(patsubst %.java,%.class,$(patsubst $(SOURCE_PATH)%,$(BUILD_PATH)%,$(SRCFILES)))

all: $(PACKAGES)
	-@cp -R $(RESOURCE_PATH)/* $(BUILD_PATH)

$(BUILD_PATH)/%.class: $(SOURCE_PATH)/%.java
	mkdir -p build
	javac $< -d $(BUILD_PATH) -cp $(BUILD_CLASS_PATH)

define PACKAGE_template
$(BUILD_PATH)/$(1): $(patsubst %.java,%.class,$(patsubst $(SOURCE_PATH)%,$(BUILD_PATH)%,$(shell find $(SOURCE_PATH)/$(1) -type f -name "*.java")))
$(1): $(BUILD_PATH)/$(1)
endef
	

$(foreach package,$(PACKAGES),$(eval $(call PACKAGE_template,$(package))))


docs: $(SRCFILES)
	javadoc -sourcepath $(SOURCE_PATH) -classpath $(BUILD_CLASS_PATH) -d $(DOC_PATH) -author -doctitle "Light Racer - Team 6" $(PACKAGES)

clean-docs:
	-@$(RM) -fr $(DOC_PATH)/*
	-@echo "Documentation Folder Cleaned"

clean:
	@$(RM) -fr $(BUILD_PATH)
	-@echo "Build Folder Cleaned"

todo:
	-@echo "TODO:"
	-@for file in $(SRCFILES); do fgrep -H -e TODO -e FIXME $$file; done; true

define DIST_template
$(1):
	-@mkdir -p $(DIST_PATH)/$(LIB_PATH)
	-@cp $(LIB_PATH)/$(1) $(DIST_PATH)/$(LIB_PATH)
endef

$(foreach lib,$(DIST_LIBS),$(eval $(call DIST_template,$(lib))))

dist: all $(DIST_LIBS)
	@javafxpackager -createjar -srcdir $(BUILD_PATH) -appclass $(APP_CLASS) -classpath $(DIST_CLASS_PATH) -outdir $(DIST_PATH) -outfile LightRacer

$(TEST_PATH)/%.class: $(TEST_PATH)/%.java
	javac $< -d $(TEST_PATH) -cp $(TEST_CLASS_PATH)

check: $(TEST_CLASSES)
