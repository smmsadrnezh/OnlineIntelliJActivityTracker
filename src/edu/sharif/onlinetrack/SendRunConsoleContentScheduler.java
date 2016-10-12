package edu.sharif.onlinetrack;

import com.intellij.execution.ExecutionManager;
import com.intellij.execution.process.ProcessAdapter;
import com.intellij.execution.process.ProcessEvent;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.process.ProcessOutputTypes;
import com.intellij.execution.ui.RunContentDescriptor;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.util.Key;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by smmsadrnezh on 10/10/16.
 */
public class SendRunConsoleContentScheduler implements ProjectComponent {
    private Project project;

    public SendRunConsoleContentScheduler(Project project) {
        this.project = project;
    }

    public void initComponent() {


    }

    public void disposeComponent() {
        // TODO: insert component disposal logic here
    }

    @NotNull
    public String getComponentName() {
        return "SendRunConsoleContentScheduler";
    }

    public void projectOpened() {
        ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();

        ses.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                RunContentDescriptor selectedContent = ExecutionManager.getInstance(project).getContentManager().getSelectedContent();
                if (selectedContent == null) return;
                ProcessHandler processHandler = selectedContent.getProcessHandler();
                if (processHandler == null || ! processHandler.isProcessTerminated()) return;
                System.out.println(selectedContent);
                processHandler.addProcessListener(new ProcessAdapter() {
                    public void processTerminated(ProcessEvent event) {
                        new Notification("error-message", "something", "happens", NotificationType.INFORMATION).notify(project);
                        System.out.println("text is available");
                        ProcessHandler processHandler = event.getProcessHandler();
                        String text = event.getText();
                        if (text != null && text.toLowerCase().contains("error")) {
                            new Notification("error-message", processHandler.toString(), text, NotificationType.INFORMATION).notify(project);
                        }
                    }
                });

            }
        }, 0, 5, TimeUnit.SECONDS); // 300 seconds = 5 minutes



    }

    public void projectClosed() {
        // called when project is being closed
    }
}
