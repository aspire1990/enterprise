package com.mindskip.iiacs.viewmodel.admin.message;

import com.mindskip.iiacs.base.BasePage;



public class MessagePageRequestVM extends BasePage {
    private String sendUserName;

    public String getSendUserName() {
        return sendUserName;
    }

    public void setSendUserName(String sendUserName) {
        this.sendUserName = sendUserName;
    }
}
