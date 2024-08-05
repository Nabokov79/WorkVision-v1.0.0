package ru.nabokovsg.laboratoryNK.service;

import ru.nabokovsg.laboratoryNK.exceptions.BadRequestException;
import ru.nabokovsg.laboratoryNK.model.DocumentStatus;

public class DiagnosticDocumentStatusFactory {

    private final static String WAITING_DOCUMENT = "ожидаются результаты обследования";
    private final static String VERIFICATION_DOCUMENT = "ожидается проверка документа";
    private final static String WAITING_DRAWING = "ожидается чертеж";
    private final static String NOT_DRAWING = "чертеж не требуется";
    private final static String VERIFICATION_DRAWING = "ожидается проверка чертежа";
    private final static String ACCEPTED = "проверено, замечаний нет";
    private final static String REMARK = "есть замечания";

    protected String getDiagnosticDocumentStatus(DocumentStatus status) {
        switch (status) {
            case NEW_DOCUMENT-> {
                return WAITING_DOCUMENT;
            }
            case VERIFICATION -> {
                return VERIFICATION_DOCUMENT;
            }
            case REMARK -> {
                return REMARK;
            }
            case ACCEPTED -> {
                return ACCEPTED;
            }
            default -> throw new BadRequestException(String.format("Unknown document status=%s", status));
        }
    }
}