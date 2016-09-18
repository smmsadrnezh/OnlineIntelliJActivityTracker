package edu.sharif.onlinetrack;

import com.intellij.execution.ExecutionManager;
import com.intellij.execution.process.ProcessEvent;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.process.ProcessListener;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.ui.RunContentDescriptor;
import com.intellij.execution.ui.RunContentManager;
import com.intellij.history.LocalHistory;
import com.intellij.history.core.changes.ChangeSet;
import com.intellij.history.core.revisions.Difference;
import com.intellij.history.core.revisions.RecentChange;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.compiler.CompilerMessageImpl;
import com.intellij.compiler.ProblemsView;
import com.intellij.compiler.server.BuildManager;
import com.intellij.history.integration.IdeaGateway;
import com.intellij.history.integration.ui.models.EntireFileHistoryDialogModel;
import com.intellij.history.integration.ui.models.FileHistoryDialogModel;
import com.intellij.ide.plugins.PluginManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

import com.intellij.history.integration.LocalHistoryImpl;
import com.intellij.history.core.LocalHistoryStorage;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.compiler.CompilerManager;
import com.intellij.openapi.compiler.CompilerMessageCategory;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.util.Clock;

import com.intellij.history.core.LocalHistoryFacade;
import com.intellij.history.core.tree.RootEntry;

import com.intellij.history.core.revisions.Revision;
import com.intellij.compiler.server.BuildManager;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.File;
import java.io.IOException;
import java.security.Key;
import java.util.List;
import com.intellij.openapi.util.Computable;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.history.core.LocalHistoryFacade;
import com.intellij.history.integration.ui.actions.ShowHistoryAction;
import com.intellij.openapi.vfs.newvfs.impl.VirtualFileImpl;
import com.intellij.vcsUtil.VcsSelection;
import com.intellij.vcsUtil.VcsSelectionUtil;
import org.jetbrains.jps.incremental.messages.CompilerMessage;


/**
 * Created by smmsadrnezh on 8/23/16.
 */
public class RegisterFormAction extends AnAction {

    public void actionPerformed(AnActionEvent e) {
        try {
            System.out.println(getEmailAddress(e));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private String getEmailAddress(AnActionEvent e) throws IOException {

        myTestFunctions(e);

        return (String) JOptionPane.showInputDialog(

                new JFrame(), "Please enter your email address:",

                "Online Track Registration",

                JOptionPane.QUESTION_MESSAGE,

                null,

                null, "");

    }

    void myTestFunctions(AnActionEvent e) throws IOException {

//        printAllLocalChanges(e);

//        eachRunMetaInformation(e);

//        getBuildLogPath();

//        eachChangeMetaInformation();

//        printCurrentCodeContext(e);

//        printCurrentProjectAddress(e);

        printRevisionsInformation();

    }

    void printAllLocalChanges(AnActionEvent e) {
    }


    void eachRunMetaInformation(AnActionEvent e) {

        Project project = getEventProject(e);
        RunContentManager a = ExecutionManager.getInstance(project).getContentManager();
        System.out.println(a.getSelectedContent().getExecutionConsole().toString());

//        List<RunContentDescriptor> descriptors = executionManager.getContentManager().showRunContent(env.getExecutor(), descriptor);
//        System.out.println(executionManager.getContentManager());


//        CompilerMessageImpl c = new CompilerMessageImpl(project,CompilerMessageCategory.ERROR, null);
//        String b = c.toString();
//        System.out.println(b);

    }

    void getBuildLogPath() {
        ProjectManager projectManager = ProjectManager.getInstance();
        BuildManager buildManager = new BuildManager(projectManager);
        File buildDir = buildManager.getBuildLogDirectory();
        File buildLog = new File(buildDir.getPath() + "/build.log");
        /** Print build log location */
        System.out.println(buildLog);
    }

    void eachChangeMetaInformation() throws IOException {
        File storageDir = LocalHistoryImpl.getInstanceImpl().getStorageDir();
        LocalHistoryStorage myStorage = new LocalHistoryStorage(storageDir.getPath() + "/changes");
        int recordIterator = myStorage.getFirstRecord();
        while (myStorage.getPrevRecord(recordIterator) != myStorage.getLastRecord()) {
            DataInputStream s = myStorage.readStream(recordIterator);
            System.out.println(recordIterator + "\t" + myStorage.getTimestamp(recordIterator));
            System.out.println(s.readLine());
            System.out.println("Information collected in " + Clock.getTime());
            recordIterator = myStorage.getNextRecord(recordIterator);
        }
    }

    void printCurrentProjectAddress(AnActionEvent e) {
        // print project which event occures on
        Project myProject = getEventProject(e);
        System.out.println(myProject);

        // print all open projects
        ProjectManager projectManager = ProjectManager.getInstance();
        Project[] openProjects = projectManager.getOpenProjects();
        for (int i = 0; i < openProjects.length; i++) {
            System.out.println(openProjects[i]);
        }
    }

    void printCurrentCodeContext(AnActionEvent e) {
        Project myProject = getEventProject(e);
        String codeContext = FileEditorManager.getInstance(myProject).getSelectedTextEditor().getDocument().getText();
        System.out.println(codeContext);
    }

    void printRevisionsInformation() {
        LocalHistoryFacade facade = LocalHistoryImpl.getInstanceImpl().getFacade();
        RootEntry root = new RootEntry();
        List<RecentChange> recentChangesList = facade.getRecentChanges(root);

//        for (int i = 0; i < recentChangesList.size(); i++) {
//            System.out.println(recentChangesList.get(i).getChangeName());
//        }
//        System.out.println("====");
//        for (int i = 0; i < recentChangesList.size(); i++) {
//            System.out.println(recentChangesList.get(i).getRevisionBefore());
//        }

        List<ChangeSet> changeSetList = facade.getChangeListInTests().getChangesInTests();
        for (int i = 0; i < changeSetList.size(); i++) {
            System.out.println(changeSetList.get(i).getName());
            System.out.println(changeSetList.get(i).getAffectedPaths().get(0));
            System.out.println(changeSetList.get(i).getTimestamp());
//            System.out.println(changeSetList.get(i).getFirstChange().write(out));
            System.out.println();
        }

//        FileHistoryDialogModel myModel = createModel(facade,e);
//        for (int i = 0; i < myModel.getRevisions().size(); i++) {
//            System.out.println(myModel.getRevisions().toString());
//        }
    }

}
