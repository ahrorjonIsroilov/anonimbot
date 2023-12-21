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
import anonim.entity.session.SessionUserRepository;
import anonim.processor.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseHandler {

    protected final List<CallbackCommand> callbackCommands;
    protected final Map<String, Processor> processors;

    protected final List<TextCommand> textCommands;

    public BaseHandler(BotService service, SessionUserRepository repository) {
        // add callback commands
        callbackCommands = new ArrayList<>();
        callbackCommands.add(new ChooseLanguage(service, repository));
        callbackCommands.add(new Answer(service, repository));
        callbackCommands.add(new Close(service, repository));
        callbackCommands.add(new Cancel(service, repository));
        // add text commands
        textCommands = new ArrayList<>();
        textCommands.add(new Start(service, repository));
        textCommands.add(new FindId(service, repository));
        textCommands.add(new SetId(service, repository));
        textCommands.add(new LangCommand(service, repository));
        textCommands.add(new SendAds(service, repository));
        textCommands.add(new Help(service, repository));
        textCommands.add(new Statistics(service, repository));
        textCommands.add(new GiveId(service, repository));
        textCommands.add(new Default(service, repository));
        // add text commands
        processors = new HashMap<>();
        processors.put("document", new ProcessDocument(service, repository));
        processors.put("photo", new ProcessPhoto(service, repository));
        processors.put("video", new ProcessVideo(service, repository));
        processors.put("videoNote", new ProcessVideoNote(service, repository));
        processors.put("audio", new ProcessAudio(service, repository));
        processors.put("sticker", new ProcessSticker(service, repository));
        processors.put("voice", new ProcessVoice(service, repository));
    }
}
