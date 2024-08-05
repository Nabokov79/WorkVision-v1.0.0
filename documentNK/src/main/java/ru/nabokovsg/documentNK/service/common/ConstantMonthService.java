package ru.nabokovsg.documentNK.service.common;

import java.time.LocalDate;

public interface ConstantMonthService {

    String getName(LocalDate date);

    String getNumber(LocalDate date);
}