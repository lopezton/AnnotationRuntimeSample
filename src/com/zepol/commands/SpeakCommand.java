package com.zepol.commands;

import com.zepol.command.BaseCommand;
import com.zepol.command.Command;

/**
 * <p>SpeakCommand
 * 
 * <p>Demonstrates the functionality of the <code>@Command</code> annotation.
 * 
 * @author tlopez
 * 
 */
@Command
public class SpeakCommand implements BaseCommand {

    @Override
    public void exec() {
        System.out.println("Performing Speak.");
    }

}
