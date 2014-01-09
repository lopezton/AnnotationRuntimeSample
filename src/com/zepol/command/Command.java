package com.zepol.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p><b>@Command</b>
 * 
 * <p>Implement this annotation on a class declaration for it to be loaded into <code>CommandCenter</code>. 
 * The class implementing this annotation must be an implementation of <code>BaseCommand</code>.<br>
 * 
 * <p><b>Options:</b>
 * <ul>
 * <li><code>enabled</code> - Set to false to exclude from <code>CommandCenter</code> scan for objects.</li>
 * </ul>
 * 
 * @see CommandCenter
 * @see BaseCommand
 * @author tlopez
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Command {

    /**
     * <p>Sets this Command's enabled flag.
     * <p>When set to <code>true</code>, this class will be included in <code>CommandCenter</code>'s
     * command list during the scan for <code>@Command</code> objects.  When <code>false</code>, it
     * will not.
     * <p>This flag is set to <code>true</code> by default.
     */
    boolean enabled() default true;
}
