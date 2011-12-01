package com.github.kumaraman21.intellijbehave.settings;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class JBehaveConfigurable implements SearchableConfigurable {
  private JBehaveSettingsPanel jBehaveSettingsPanel;

  @NotNull
  @Override
  public String getId() {
    return getHelpTopic();
  }

  @Override
  public Runnable enableSearch(String s) {
    return null;
  }

  @Nls
  @Override
  public String getDisplayName() {
    return "JBehave";
  }

  @Override
  public Icon getIcon() {
    return null;
  }

  @Override
  public String getHelpTopic() {
    return "reference.settingsdialog.project.jbehave";
  }

  @Override
  public JComponent createComponent() {
    if (jBehaveSettingsPanel == null) {
      jBehaveSettingsPanel = new JBehaveSettingsPanel();
    }
    reset();
    return jBehaveSettingsPanel.getContentPane();
  }

  @Override
  public boolean isModified() {
    if(jBehaveSettingsPanel != null) {
      return jBehaveSettingsPanel.isModified();
    }
    return false;
  }

  @Override
  public void apply() throws ConfigurationException {
    if(jBehaveSettingsPanel != null) {
      jBehaveSettingsPanel.apply();
    }
  }

  @Override
  public void reset() {
    if(jBehaveSettingsPanel != null) {
      jBehaveSettingsPanel.reset();
    }
  }

  @Override
  public void disposeUIResources() {
    jBehaveSettingsPanel = null;
  }
}
