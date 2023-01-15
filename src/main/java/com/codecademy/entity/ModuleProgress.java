package com.codecademy.entity;

public class ModuleProgress {
    private Module module;
    private Student student;
    private byte progress;

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public byte getProgress() {
        return progress;
    }

    public void setProgress(byte progress) {
        this.progress = progress;
    }
}
