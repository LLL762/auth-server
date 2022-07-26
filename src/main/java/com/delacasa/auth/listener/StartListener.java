package com.delacasa.auth.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.delacasa.auth.config.AccRoleConfig;
import com.delacasa.auth.config.AccStatusConfig;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StartListener implements ApplicationListener<ContextRefreshedEvent> {

	private final AccStatusConfig statusConfig;
	private final AccRoleConfig roleConfig;

	@Override
	public void onApplicationEvent(final ContextRefreshedEvent event) {

		statusConfig.init();
		statusConfig.check();

		roleConfig.init();
		roleConfig.check();

	}

}
