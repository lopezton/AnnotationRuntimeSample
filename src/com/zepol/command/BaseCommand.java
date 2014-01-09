package com.zepol.command;

/**
 * <p>The base interface for any <code>@Command</code> implemented objects.
 * 
 * <p>This class can and should be implemented when attempting to use <code>@Command<code>
 * in the class declaration header.  Failure to do so will result in an 
 * <code>UnsuportedOperationExpception</code> to be thrown.
 * 
 * @author tlopez
 * @see CommandCenter
 * @see Command
 * 
 */
public interface BaseCommand {

    /**
     * <p>Performs this command's execution method.
     */
    public void exec();
}
