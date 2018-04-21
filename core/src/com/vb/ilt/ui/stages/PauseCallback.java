package com.vb.ilt.ui.stages;

import com.badlogic.ashley.core.EntitySystem;

import java.util.List;

public interface PauseCallback {

    void setSystemsDisabledAndShowPauseMenu(List<Class<? extends EntitySystem>> systems);
    void setSystemsEnabledAndClosePauseMenu(List<Class<? extends EntitySystem>> systems);
}
