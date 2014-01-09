package com.zepol.command;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Singleton class that shows how to configure settings via annotations at Runtime.
 * 
 * <p>When an instance of CommandCenter is created, the class searches through a list of
 * pre-defined packages for classes annotated with the annotation <code>@Command</code>,
 * attempts to instantiate those objects, and if successful, adds the instantiated objects
 * to a private <code>List<BaseCommand></code> object.
 * 
 * <p>Any class that makes use of the <code>@Command</code> must implement the 
 * <code>BaseCommand<code>, or else an UnsupportedOperationException will be thrown.
 * 
 * @see BaseCommand
 * @see Command
 * 
 * @author tlopez
 * 
 */
public class CommandCenter {

    private List<BaseCommand> commands;

    private static CommandCenter instance;

    /**
     * <p>In order for CommandCenter to find a class that contains the @Command annotation,
     * the package name containing that class must be added here.
     * 
     * <p><b>TODO:</b> This is obviously not good. Need a good solution to make this dynamic.
     */
    private static final String[] SCANNABLE_PACKAGES = { "com.zepol.commands" };

    public static CommandCenter getInstance() {
        if (instance == null) {
            instance = new CommandCenter();
        }
        return instance;
    }

    // Hidden Constructor
    private CommandCenter() {
        this.commands = new ArrayList<>();

        for (String packageName : SCANNABLE_PACKAGES) {
            this.load(packageName);
        }
    }

    /**
     * <p>Add a <code>BaseCommand</code> object to this <code>CommandCenter</code>'s list
     * of commands.
     * @param command The <code>BaseCommand</code> object to add.
     */
    public void add(BaseCommand command) {
        this.commands.add(command);
    }

    /**
     * <p>Cycles through this <code>CommandCenter</code>'s list of commands and
     * attempts to call the <code>BaseCommand.exec() method of each.</code>
     * 
     * @see BaseCommand
     */
    public void exec() {
        for (BaseCommand command : this.commands) {
            command.exec();
        }
    }

    /**
     * <p>When an instance of CommandCenter is created, this method is called. Searches through the
     * package denoted by <code>packageName</code> for classes annotated with the annotation 
     * <code>@Command</code> and attempts to instantiate those objects. If successful, the method
     * adds the instantiated objects to a private <code>List<BaseCommand></code> object.
     * 
     * <p><b>Note:</b> Any class that makes use of the <code>@Command</code> must implement the 
     * <code>BaseCommand<code>, or else an UnsupportedOperationException will be thrown.
     * 
     * @param packageName The package in which to search for.
     */
    private void load(String packageName) {
        // ***********************************************************
        // * First load all of class files in the specified package.
        // ***********************************************************
        URL root = Thread.currentThread().getContextClassLoader().getResource(packageName.replace(".", "/"));
        File[] files = new File(root.getFile()).listFiles(new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".class");
            }
        });

        // ************************************************************
        // * Sort through the classes and instantiate the appropriate
        // * ones only. Add the instantiated classes to the
        // * this instance's list of commands.
        // ************************************************************
        for (File file : files) {
            String className = file.getName().replaceAll(".class$", "");
            Class<?> clazz;
            try {
                clazz = Class.forName(packageName + "." + className);

                for (Annotation annotation : clazz.getAnnotations()) {
                    if (annotation instanceof Command) {
                        
                        // ************************************************************
                        // * If the user is trying to use an @Command annotation on
                        // * a class without implementing the required interface,
                        // * inform them.
                        // ************************************************************
                        boolean isInterfacingBaseCommand = false;
                        for (Class<?> iFace : clazz.getInterfaces()) {
                            if (iFace.equals(BaseCommand.class)) {
                                isInterfacingBaseCommand = true;
                            }
                        }
                        if (!isInterfacingBaseCommand) {
                            throw new UnsupportedOperationException("Failed to create BaseCommand for [" + className 
                                    + "]. Class must implement BaseCommand.class");
                        }
                        
                        // ************************************************************
                        // * If the enabled flag is set to false, do not add it to the
                        // * list of instantiated commands.
                        // ************************************************************
                        if (((Command) annotation).enabled()) {
                            BaseCommand command = (BaseCommand) clazz.newInstance();
                            this.commands.add(command);
                        } else {
                            // Logging for educational purposes.
                            System.out.println("Skipped instantiation for [" + className + "]. Set enabled to true" +
                                    " to instantiate this command.");
                        }
                    }
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                // ************************************************************
                // * General catch-all error. Don't halt the instantation
                // * process due to a single error. This can be further
                // * implemented as necessary.
                // ************************************************************
                System.out.println("Error instantiating command for [" + className + "] : " + e.getMessage());
            }
        }
    }
}
