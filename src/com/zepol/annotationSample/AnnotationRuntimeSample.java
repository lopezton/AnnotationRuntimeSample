package com.zepol.annotationSample;

import com.zepol.command.CommandCenter;

/**
 * <p>Sample application to demonstrate how to create custom annotations and perform
 * operations at runtime based on where the annotations are placed and the values
 * provided alongside them.
 * 
 * <p>I enjoy feedback! If you have any please feel free to reach out at
 * tony.lopez@gmail.com.  Or find me on github at https://github.com/lopezton
 * 
 * @author tlopez
 *
 */
public class AnnotationRuntimeSample {

    public static void main(String[] args) {
        CommandCenter commandCenter = CommandCenter.getInstance();
        commandCenter.exec();
    }
}
