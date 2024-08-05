package ru.nabokovsg.documentNK.model.template.common;

public enum TableType {

    SURVEYS_TABLE("Таблица обследований"),
    REPAIR_TABLE("Таблица ремонтов"),
    TABLE_VMS("Таблица визуального и измерительного контроля(для обследований)"),
    TABLE_VMC("Таблица визуального и измерительного контроля(для контроля качества)"),
    TABLE_UT("Таблица ультразвуковой толщинометрии"),
    MEASUREMENT_RP("Таблица измерений по реперам"),
    MEASUREMENT_CP("Таблица измерений по контрольным точкам"),
    TABLE_UC("Таблица ультразвукового контроля"),
    MEASUREMENT_HARDNESS("Таблица измерений твердости металла");

    public final String label;

    TableType(String label) {
        this.label = label;
    }
}
