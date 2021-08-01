package org.wjl.jetbrains.plugins;

import com.intellij.featureStatistics.FeatureUsageTracker;
import com.intellij.icons.AllIcons;
import com.intellij.ide.actions.GotoActionBase;
import com.intellij.ide.util.gotoByName.ChooseByNameFilter;
import com.intellij.ide.util.gotoByName.ChooseByNameItemProvider;
import com.intellij.ide.util.gotoByName.ChooseByNamePopup;
import com.intellij.ide.util.gotoByName.DefaultChooseByNameItemProvider;
import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GoToWindowAction extends GotoActionBase implements DumbAware {

    public GoToWindowAction() {
        getTemplatePresentation().setText("switch window");
        getTemplatePresentation().setDescription("search & switch opened window");
        getTemplatePresentation().setIcon(AllIcons.Actions.Search);
    }

    @Override
    protected void gotoActionPerformed(@NotNull AnActionEvent e) {

        Project project = e.getProject();
        if (project == null) {
            return;
        }

        // 显示featureId对应的Tips
        FeatureUsageTracker.getInstance().triggerFeatureUsed("navigation.popup.service");

        ChooseByNameContributor[] contributors = {
                new GotoWindowContributor(e.getData(LangDataKeys.MODULE), e),
        };

        WindowFilteringGotoByModel model = new WindowFilteringGotoByModel(project, contributors);

        GotoActionCallback<String> callback = new GotoActionCallback<>() {

            @NotNull
            @Contract("_ -> new")
            @Override
            protected ChooseByNameFilter<String> createFilter(@NotNull ChooseByNamePopup popup) {
                return new GotoRequestMappingFilter(popup, model, project);
            }

            @Override
            public void elementChosen(ChooseByNamePopup chooseByNamePopup, Object element) {
                if (element instanceof WindowServiceItem) {
                    WindowServiceItem navigationItem = (WindowServiceItem) element;
                    if (navigationItem.canNavigate()) {
                        navigationItem.navigate(true);
                    }
                }
            }
        };

        DefaultChooseByNameItemProvider provider = new DefaultChooseByNameItemProvider(getPsiContext(e));
        showNavigationPopup(
                e, model, callback,
                "search.FindUsagesTitle",
                true,
                true,
                (ChooseByNameItemProvider) provider
        );
    }

    protected static class GotoRequestMappingFilter extends ChooseByNameFilter<String> {

        GotoRequestMappingFilter(final ChooseByNamePopup popup,
                                 WindowFilteringGotoByModel model, final Project project) {
            super(popup, model, GotoWindowConfiguration.getInstance(project), project);
        }

        @Override
        @NotNull
        protected List<String> getAllFilterValues() {
            return Stream.of(ProjectManager.getInstance().getOpenProjects())
                    .map(Project::getName)
                    .collect(Collectors.toList());
        }

        @Override
        protected String textForFilterValue(@NotNull String value) {
            return value;
        }

        @Override
        protected Icon iconForFilterValue(@NotNull String value) {
            return AllIcons.Icons.Ide.MenuArrow;
        }
    }
}
