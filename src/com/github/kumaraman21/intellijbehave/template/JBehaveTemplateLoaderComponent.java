package com.github.kumaraman21.intellijbehave.template;

import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.openapi.components.ApplicationComponent;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStreamReader;

import static com.github.kumaraman21.intellijbehave.language.StoryFileType.STORY_FILE_TYPE;
import static com.intellij.openapi.util.io.FileUtil.loadTextAndClose;

public class JBehaveTemplateLoaderComponent implements ApplicationComponent {
  @Override
  public void initComponent() {
    FileTemplate template = FileTemplateManager.getInstance().getTemplate(STORY_FILE_TYPE.getName());
    if (template == null) {
      template = FileTemplateManager.getInstance()
        .addTemplate(STORY_FILE_TYPE.getName(), STORY_FILE_TYPE.getDefaultExtension());
      try {
        template.setText(
          loadTextAndClose(new InputStreamReader(getClass().getResourceAsStream("/fileTemplates/JBehave Story.story.ft"))));
      }
      catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void disposeComponent() {
    // do nothing
  }

  @NotNull
  @Override
  public String getComponentName() {
    return this.getClass().getName();
  }
}
