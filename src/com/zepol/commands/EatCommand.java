package com.zepol.commands;

import com.zepol.command.BaseCommand;
import com.zepol.command.Command;

/**
 * <p>EatCommand
 * 
 * <p>Demonstrates use of the enabled() flag for the <code>@Command</code> annotation.
 * @author tlopez
 * 
 */
@Command(enabled = false)
public class EatCommand implements BaseCommand {

    @Override
    public void exec() {
        System.out.println("Performing Eat Command.");
    }

}
