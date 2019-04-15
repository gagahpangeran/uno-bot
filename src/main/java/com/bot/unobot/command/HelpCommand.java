package com.bot.unobot.command;

import com.bot.unobot.handler.HandlerController;

public class HelpCommand implements CommandController {

    private HandlerController handler;

    public HelpCommand(HandlerController handler) {
        this.handler = handler;
    }

    @Override
    public void execute() {
        this.handler.replyMessage("Ini fungsi help");
    }
}
