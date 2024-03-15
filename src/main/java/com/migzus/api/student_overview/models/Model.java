package com.migzus.api.student_overview.models;

public abstract class Model {
    public abstract boolean haveNullFields();
    public abstract void copyOverData(final Model model);
}
