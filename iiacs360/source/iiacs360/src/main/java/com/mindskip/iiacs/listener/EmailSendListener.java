package com.mindskip.iiacs.listener;

import com.mindskip.iiacs.domain.User;
import com.mindskip.iiacs.event.OnRegistrationCompleteEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;


/**
 * @version 3.5.0
 * @description: The type Email send listener.
 * Copyright (C), 2020-2021, ces科技有限公司
 * @date 2021/12/25 9:45
 */
@Component
public class EmailSendListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    @Override
    @NonNull
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        System.out.println("User register Email sender :" + user.getUserName());
    }
}
