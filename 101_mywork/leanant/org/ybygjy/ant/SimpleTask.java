package org.ybygjy.ant;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

/**
 * 自定义Ant任务
 * @author WangYanCheng
 * @version 2011-10-20
 */
public class SimpleTask extends Task {
    private Project project;
    private List<FileList> fileListArr = new ArrayList<FileList>();
    @Override
    public void execute() throws BuildException {
        String message = project.getProperty("ant.project.name");
        project.log("Here is project " + message + ".", Project.MSG_ERR);
        project.log("My location is " + getLocation());
        for (Iterator<FileList> iterator = fileListArr.iterator(); iterator.hasNext();) {
            project.log(iterator.next().path, Project.MSG_INFO);
        }
    }
    public void setProject(Project proj) {
        this.project = proj;
    }
    public FileList createFileList() {
        FileList fileList = new FileList();
        fileListArr.add(fileList);
        return fileList;
    }
    /**
     * Nested Object
     * @author WangYanCheng
     * @version 2011-10-20
     */
    public class FileList {
        String path;
        public FileList(){}
        public String getPath() {
            return path;
        }
        public void setPath(String path) {
            this.path = path;
        }
    }
}
