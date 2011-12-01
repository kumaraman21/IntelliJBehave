package com.github.kumaraman21.intellijbehave.settings;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;

@State(
  name = "JBehaveSettings",
  storages = {@Storage(
    id = "main",
    file = "$APP_CONFIG$/jbehave_settings.xml"
  )}
)
public class JBehaveSettings implements PersistentStateComponent<JBehaveSettings> {
  private String storyRunner;

  public static JBehaveSettings getInstance(){
    return ServiceManager.getService(JBehaveSettings.class);
  }

  @Override
  public JBehaveSettings getState() {
    return this;
  }

  @Override
  public void loadState(JBehaveSettings jBehaveSettings) {
    XmlSerializerUtil.copyBean(jBehaveSettings, this);
  }

  public String getStoryRunner() {
    return storyRunner;
  }

  public void setStoryRunner(String storyRunner) {
    this.storyRunner = storyRunner;
  }
}
