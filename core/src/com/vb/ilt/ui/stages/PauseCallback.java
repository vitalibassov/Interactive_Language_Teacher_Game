package com.vb.ilt.ui.stages;

import com.badlogic.ashley.core.EntitySystem;

public interface PauseCallback {

    void setSystemsDisabledAndShowPauseMenu(Class<? extends EntitySystem>... systems);
    void setSystemsEnabledAndClosePauseMenu(Class<? extends EntitySystem>... systems);
}
