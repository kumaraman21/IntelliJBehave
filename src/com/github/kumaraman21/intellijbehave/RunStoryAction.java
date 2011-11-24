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
package com.github.kumaraman21.intellijbehave;

import com.intellij.execution.*;
import com.intellij.execution.application.ApplicationConfiguration;
import com.intellij.execution.executors.DefaultRunExecutor;
import com.intellij.execution.impl.RunManagerImpl;
import com.intellij.execution.impl.RunnerAndConfigurationSettingsImpl;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.runners.ProgramRunner;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.search.GlobalSearchScope;
import org.apache.commons.lang.StringUtils;

public class RunStoryAction extends AnAction {

  private static final String MY_RUNNER = "MyRunner";

  public void actionPerformed(AnActionEvent e) {
    Application application = ApplicationManager.getApplication();
    JBehaveSettings component = application.getComponent(JBehaveSettings.class);

    String mainClassName = component.getStoryRunner();
    if (mainClassName == null || mainClassName.isEmpty()) {
      say("Set the class of the runner in the My Runner settings.");
      return;
    }

    Project project = e.getData(PlatformDataKeys.PROJECT);
    //String moduleNameOfRunner = component.getModuleNameOfRunner();
    final PsiClass mainClass = JavaPsiFacade.getInstance(project).findClass(mainClassName,
                                                                               GlobalSearchScope.allScope(project));
    String moduleNameOfRunner = ProjectRootManager.getInstance(project).getFileIndex().getModuleForFile(mainClass.getContainingFile().getVirtualFile()).getName();
    if (moduleNameOfRunner == null || moduleNameOfRunner.isEmpty()) {
      say("Specify the name of the module where the runner class can be found. Check the settings.");
    }

    VirtualFile file = e.getData(PlatformDataKeys.VIRTUAL_FILE);

    if (file == null) {
      say("Select a file or focus on the file being edited to feed it to My Runner.");
      return;
    }

    RunManagerImpl runManager = (RunManagerImpl)RunManager.getInstance(project);
    Module module = ModuleManager.getInstance(project).findModuleByName(moduleNameOfRunner);

    if (module == null) {
      say("Could not find the module of the runner with name '" +
          moduleNameOfRunner +
          "'. Check settings." +
          "\n\nHere is the list of modules that were found:\n   " +
          StringUtils.join(ModuleManager.getInstance(project).getModules(), "\n   "));
      return;
    }

    RunnerAndConfigurationSettingsImpl runnerAndConfigurationSettings = findConfigurationByName(MY_RUNNER, runManager);
    ApplicationConfiguration conf = null;

    if (runnerAndConfigurationSettings != null) {
      conf = (ApplicationConfiguration)runnerAndConfigurationSettings.getConfiguration();
      updateConfiguration(mainClassName, file, module, conf);

    }
    else {
      StoryRunnerConfigurationType type = application.getComponent(StoryRunnerConfigurationType.class);
      runnerAndConfigurationSettings =
        (RunnerAndConfigurationSettingsImpl)runManager.createRunConfiguration(MY_RUNNER, type.getConfigurationFactories()[0]);
      conf = (ApplicationConfiguration)runnerAndConfigurationSettings.getConfiguration();
      updateConfiguration(mainClassName, file, module, conf);
      runManager.addConfiguration(runnerAndConfigurationSettings, true);
    }

    runManager.setActiveConfiguration(runnerAndConfigurationSettings);

    Executor executor = DefaultRunExecutor.getRunExecutorInstance();
    ProgramRunner runner = RunnerRegistry.getInstance().getRunner(executor.getId(), conf);
    //TODO: remove deprecated call
    ExecutionEnvironment environment = new ExecutionEnvironment(runner, runnerAndConfigurationSettings, e.getDataContext());

    try {
      runner.execute(executor, environment);
    }
    catch (ExecutionException e1) {
      JavaExecutionUtil.showExecutionErrorMessage(e1, "Error", project);
    }
  }

  private RunnerAndConfigurationSettingsImpl findConfigurationByName(String name, RunManagerImpl runManager) {
    for (RunnerAndConfigurationSettings settings : runManager.getSortedConfigurations()) {
      if (settings.getName().equals(name)) return (RunnerAndConfigurationSettingsImpl)settings;
    }
    return null;
  }

  private void updateConfiguration(String mainClassName, VirtualFile file, Module module, ApplicationConfiguration conf) {
    conf.setMainClassName(mainClassName);
    conf.setProgramParameters(file.getPath());
    conf.setModule(module);
  }

  public void say(String message) {
    Messages.showMessageDialog(message, "Info", Messages.getInformationIcon());
  }
}
