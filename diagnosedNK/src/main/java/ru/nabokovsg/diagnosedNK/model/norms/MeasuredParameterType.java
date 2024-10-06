package ru.nabokovsg.diagnosedNK.model.norms;

public enum MeasuredParameterType {

    LENGTH("длина"),
    WIDTH("ширина"),
    HEIGHT("высота"),
    DEPTH("глубина"),
    DIAMETER("диаметр"),
    AREA("площадь"),
    QUANTITY("количество"),
    DIRECTION("направление");

    public final String label;

    MeasuredParameterType(String label) {
        this.label = label;
    }
}