package anonim.base.handler;


import anonim.base.BotService;
import anonim.base.command.CallbackCommand;
import anonim.base.command.Processor;
import anonim.base.command.TextCommand;
import anonim.command.*;
import anonim.command.inline.Answer;
import anonim.command.inline.Cancel;
import anonim.command.inline.ChooseLanguage;
import anonim.command.inline.Close;
import anonim.processor.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseHandler {

    protected final List<CallbackCommand> callbackCommands;
    protected final Map<String, Processor> processors;

    protected final List<TextCommand> textCommands;

    public BaseHandler(BotService service) {
        // add callback commands
        callbackCommands = new ArrayList<>();
        callbackCommands.add(new ChooseLanguage(service));
        callbackCommands.add(new Answer(service));
        callbackCommands.add(new Close(service));
        callbackCommands.add(new Cancel(service));
        // add text commands
        textCommands = new ArrayList<>();
        textCommands.add(new Start(service));
        textCommands.add(new FindId(service));
        textCommands.add(new SetId(service));
        textCommands.add(new LangCommand(service));
        textCommands.add(new SendAds(service));
        textCommands.add(new Help(service));
        textCommands.add(new Statistics(service));
        textCommands.add(new GiveId(service));
        textCommands.add(new Default(service));
        // add text commands
        processors = new HashMap<>();
        processors.put("document", new ProcessDocument(service));
        processors.put("photo", new ProcessPhoto(service));
        processors.put("video", new ProcessVideo(service));
        processors.put("videoNote", new ProcessVideoNote(service));
        processors.put("audio", new ProcessAudio(service));
        processors.put("sticker", new ProcessSticker(service));
        processors.put("voice", new ProcessVoice(service));
    }
}
