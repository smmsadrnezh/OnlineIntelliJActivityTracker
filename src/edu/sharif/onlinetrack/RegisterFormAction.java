package edu.sharif.onlinetrack;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

import com.intellij.history.LocalHistoryAction;

import javax.swing.*;

/**
 * Created by smmsadrnezh on 8/23/16.
 */
public class RegisterFormAction extends AnAction {
    private LocalHistoryAction myAction;

    public void actionPerformed(AnActionEvent e) {
        System.out.println(getEmailAddress());
    }

    private String getEmailAddress() {

        return (String) JOptionPane.showInputDialog(

                new JFrame(), "Please enter your email address:",

                "Online Track Registration",

                JOptionPane.QUESTION_MESSAGE,

                null,

                null, "");

    }
}
