package org.wjl.jetbrains.plugins;

import com.intellij.ide.util.gotoByName.ChooseByNameFilterConfiguration;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.components.StoragePathMacros;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

/**
 * Configuration for file type filtering popup in "Go to | Service" action.
 *
 * @author WJL
 */
@State(name = "GotoWindowConfiguration", storages = @Storage(StoragePathMacros.NON_ROAMABLE_FILE))
public class GotoWindowConfiguration extends ChooseByNameFilterConfiguration<String> {

    /**
     * Get configuration instance
     *
     * @param project a project instance
     * @return a configuration instance
     */
    public static GotoWindowConfiguration getInstance(Project project) {
        return project.getService(GotoWindowConfiguration.class);
    }

    @Override
    protected String nameForElement(@NotNull String type) {
        return type;
    }
}