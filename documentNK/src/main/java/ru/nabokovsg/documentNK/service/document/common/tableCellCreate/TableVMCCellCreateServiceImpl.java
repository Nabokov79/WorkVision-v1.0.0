package ru.nabokovsg.documentNK.service.document.common.tableCellCreate;

import org.springframework.stereotype.Service;
import ru.nabokovsg.documentNK.dto.client.documentCreate.DocumentCreationDataDto;
import ru.nabokovsg.documentNK.model.document.common.CellTable;
import ru.nabokovsg.documentNK.model.document.common.DocumentTable;
import ru.nabokovsg.documentNK.model.template.common.TableTemplate;

import java.util.List;

@Service
public class TableVMCCellCreateServiceImpl implements TableVMCCellCreateService {

    @Override
    public List<CellTable> create(DocumentTable table, TableTemplate template, DocumentCreationDataDto documentDataDto) {
        return null;
    }
}