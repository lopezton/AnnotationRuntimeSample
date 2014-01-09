package com.zepol.commands;

import com.zepol.command.BaseCommand;
import com.zepol.command.Command;

/**
 * <p>Demonstrates the functionality of the <code>@Command</code> annotation
 * on a class that extends an already instantiated <code>BaseCommand</code>
 * object.
 * 
 * @author tlopez
 *
 */
@Command
public class SpeakExtendedCommand extends SpeakCommand implements BaseCommand {
    
    @Override
    public void exec() {
        System.out.println("Performing extended Speak.");
    }
}
