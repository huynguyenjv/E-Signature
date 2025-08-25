package com.backend.esignature.mapper.helper;

import com.backend.esignature.entities.Documents;
import com.backend.esignature.repositories.document.DocumentsRepository;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DocumentMapperHelper {

    @Autowired
    private DocumentsRepository documentRepository;

    @Named("mapDocumentById")
    public Documents mapDocumentById(String documentId) {
        if (documentId == null) return null;
        return documentRepository.findById(documentId)
                .orElse(null);
    }
}

