package edu.sharif.onlinetrack;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by smmsadrnezh on 10/10/16.
 */
public class SendChangesDataScheduler implements ProjectComponent {
    public SendChangesDataScheduler(Project project) {
    }

    public void initComponent() {

    }

    public void disposeComponent() {
        // TODO: insert component disposal logic here
    }

    @NotNull
    public String getComponentName() {
        return "SendChangesDataScheduler";
    }

    public void projectOpened() {

        ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();

        ses.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("salam");
            }
        }, 0, 300, TimeUnit.SECONDS); // 300 seconds = 5 minutes

    }

    public void projectClosed() {
    }
}
