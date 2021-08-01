package org.wjl.jetbrains.plugins;

import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.impl.ProjectWindowAction;
import com.intellij.openapi.wm.impl.ProjectWindowActionGroup;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GotoWindowContributor implements ChooseByNameContributor {

    private final Module module;

    private List<WindowServiceItem> itemList;

    private AnActionEvent event;

    public GotoWindowContributor(Module module, AnActionEvent event) {
        this.module = module;
        this.event = event;
    }

    @NotNull
    @Override
    public String[] getNames(Project project, boolean includeNonProjectItems) {

        itemList = new ArrayList<>();
        ProjectWindowActionGroup pwag = (ProjectWindowActionGroup) ActionManager.getInstance()
                .getActionOrStub("OpenProjectWindows");

        List<ProjectWindowAction> activeWindowActions = new ArrayList<>();
        if (pwag != null) {
            AnAction[] children = pwag.getChildren(event);
            for (AnAction child : children) {
                if (child instanceof ProjectWindowAction) {
                    activeWindowActions.add((ProjectWindowAction) child);
                }
            }
        }

        List<String> nameList = new ArrayList<>();
        for (ProjectWindowAction activeWindowAction : activeWindowActions) {
            itemList.add(new WindowServiceItem(activeWindowAction.getProjectName(), activeWindowAction, event));
            nameList.add(activeWindowAction.getProjectName());
        }


        return nameList.toArray(new String[0]);
    }

    @NotNull
    @Override
    public NavigationItem[] getItemsByName(String name, String pattern, Project project, boolean includeNonProjectItems) {
        return itemList.toArray(new NavigationItem[0]);
    }
}
