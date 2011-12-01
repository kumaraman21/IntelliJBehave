package com.github.kumaraman21.intellijbehave.runner;

import com.intellij.execution.application.ApplicationConfigurationType;
import org.jetbrains.annotations.NotNull;

public class StoryRunnerConfigurationType extends ApplicationConfigurationType {

  public static final String JBEHAVE_STORY_RUNNER = "JBehave Story Runner";

  @Override
  public String getDisplayName() {
    return JBEHAVE_STORY_RUNNER;
  }

  @Override
  public String getConfigurationTypeDescription() {
    return "Runs a JBehave story file";
  }

  @NotNull
  @Override
  public String getId() {
    return "intellijbehave.storyrunner";
  }
}
