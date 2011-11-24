/*
 * Copyright 2000-2011 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
