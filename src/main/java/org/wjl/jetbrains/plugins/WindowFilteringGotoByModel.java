package org.wjl.jetbrains.plugins;

import com.intellij.ide.util.gotoByName.CustomMatcherModel;
import com.intellij.ide.util.gotoByName.FilteringGotoByModel;
import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.NlsContexts;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WindowFilteringGotoByModel extends FilteringGotoByModel<String>
        implements DumbAware, CustomMatcherModel {

    protected WindowFilteringGotoByModel(@NotNull Project project, ChooseByNameContributor @NotNull [] contributors) {
        super(project, contributors);
    }

    @Override
    protected @Nullable String filterValueFor(NavigationItem navigationItem) {
        return navigationItem.getName();
    }

    @Override
    public @Nls(capitalization = Nls.Capitalization.Sentence) String getPromptText() {
        return "请输入窗口名";
    }

    @Override
    public @NotNull @NlsContexts.Label String getNotInMessage() {
        return "no matches found";
    }

    @Override
    public @NotNull @NlsContexts.Label String getNotFoundMessage() {
        return "no matches found";
    }

    @Override
    public @Nullable @NlsContexts.Label String getCheckBoxName() {
        return null;
    }

    @Override
    public boolean loadInitialCheckBoxState() {
        return false;
    }

    @Override
    public void saveInitialCheckBoxState(boolean b) {

    }

    @Override
    public String @NotNull [] getSeparators() {
        return new String[]{"-", "_"};
    }

    @Override
    public @Nullable String getFullName(@NotNull Object o) {
        return getElementName(o);
    }

    @Override
    public boolean willOpenEditor() {
        return false;
    }

    @Override
    public boolean matches(@NotNull String s, @NotNull String s1) {
        return true;
    }
}
