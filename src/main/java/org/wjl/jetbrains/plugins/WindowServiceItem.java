package org.wjl.jetbrains.plugins;

import com.intellij.icons.AllIcons;
import com.intellij.navigation.ItemPresentation;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.ToggleAction;
import com.intellij.openapi.util.NlsSafe;
import com.intellij.openapi.wm.impl.ProjectWindowAction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class WindowServiceItem implements NavigationItem {

    private final String name;

    private final ToggleAction navigationElement;

    private final AnActionEvent event;

    public WindowServiceItem(String name, ToggleAction navigationElement, AnActionEvent event) {
        this.name = name;
        this.navigationElement = navigationElement;
        this.event = event;
    }

    @Override
    public @Nullable @NlsSafe String getName() {
        return name;
    }

    @Override
    public @Nullable ItemPresentation getPresentation() {
        return new ItemPresentation() {

            @Nullable
            @Override
            public String getPresentableText() {
                return name;
            }

            @Override
            public String getLocationString() {
                ProjectWindowAction pwa = (ProjectWindowAction) navigationElement;
                return name + " in " + pwa.getProjectLocation();
            }

            @NotNull
            @Override
            public Icon getIcon(boolean unused) {
                return AllIcons.Icons.Ide.NextStep;
            }
        };
    }

    @Override
    public void navigate(boolean b) {
        if (navigationElement != null) {
            navigationElement.setSelected(event, true);
        }
    }

    @Override
    public boolean canNavigate() {
        return true;
    }

    @Override
    public boolean canNavigateToSource() {
        return true;
    }
}
