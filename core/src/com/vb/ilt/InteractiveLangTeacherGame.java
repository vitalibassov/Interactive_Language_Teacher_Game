package com.vb.ilt;

import com.vb.ilt.screen.loading.LoadingScreen;

public class InteractiveLangTeacherGame extends GameBase {

	@Override
	public void postCreate() {
		setScreen(new LoadingScreen(this));
	}
}
