PACKAGES := Database UserInterface Gameplay lightracer
.PHONY: all clean dist check clean-docs $(PACKAGES) todo libs

RESOURCE_PATH := res
SOURCE_PATH := src
BUILD_PATH := build
DOC_PATH := docs
DIST_PATH := dist
LIB_PATH := lib

APP_CLASS := lightracer.LightRacer

DIST_LIBS := base64-2.3.7.jar sqlite-jdbc-3.7.2.jar

DIST_CLASS_PATH := lib/base64-2.3.7.jar:lib/sqlite-jdbc-3.7.2.jar:$(JAVA_HOME)/jre/lib/jfxrt.jar
BUILD_CLASS_PATH := $(BUILD_PATH):$(SOURCE_PATH):$(DIST_CLASS_PATH)

SRCFILES := $(shell find $(SOURCE_PATH) -type f -name "*.java")
CLASSFILES := $(patsubst %.java,%.class,$(patsubst $(SOURCE_PATH)%,$(BUILD_PATH)%,$(SRCFILES)))

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

all: $(PACKAGES)
	-@cp -R $(RESOURCE_PATH)/* $(BUILD_PATH)

define DIST_template
$(1):
	-@mkdir -p $(DIST_PATH)/$(LIB_PATH)
	-@cp $(LIB_PATH)/$(1) $(DIST_PATH)/$(LIB_PATH)
endef

$(foreach lib,$(DIST_LIBS),$(eval $(call DIST_template,$(lib))))

dist: all $(DIST_LIBS)
	@javafxpackager -createjar -srcdir $(BUILD_PATH) -appclass $(APP_CLASS) -classpath $(DIST_CLASS_PATH) -outdir $(DIST_PATH) -outfile LightRacer
