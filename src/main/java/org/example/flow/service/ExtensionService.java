package org.example.flow.service;

import lombok.RequiredArgsConstructor;
import org.example.flow.common.exception.BusinessException;
import org.example.flow.common.exception.ErrorCode;
import org.example.flow.dto.response.ExtensionListResponseDto;
import org.example.flow.dto.response.ExtensionResponseDto;
import org.example.flow.entity.Extension;
import org.example.flow.repository.ExtensionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ExtensionService {

    private final ExtensionRepository extensionRepository;

    @Transactional(readOnly = true)
    public ExtensionListResponseDto getAllExtensions() {
        var extensions = extensionRepository.findAll();

        List<ExtensionResponseDto> customExtensionList = extensions.stream()
                .filter(Extension::isCustom)
                .map(ExtensionResponseDto::from)
                .toList();

        List<ExtensionResponseDto> fixedExtensionList = extensions.stream()
                .filter(ext -> !ext.isCustom())
                .map(ExtensionResponseDto::from)
                .toList();

        return new ExtensionListResponseDto(fixedExtensionList, customExtensionList);
    }

    @Transactional
    public void addExtension(String extensionName) {

        int maxExtensionStringLength = 20;
        int maxCustomExtensionCount = 200;

        if (extensionName.length() > maxExtensionStringLength || extensionName.isEmpty()) {
            throw new BusinessException(ErrorCode.EXTENSION_INVALID_LENGTH_INPUT);
        }

        if (extensionRepository.existsByName(extensionName)) {
            throw new BusinessException(ErrorCode.EXTENSION_ALREADY_EXISTS);
        }

        if (extensionRepository.countByIsCustomTrue() >= maxCustomExtensionCount) {
            throw new BusinessException(ErrorCode.EXTENSION_EXCEED_LIMIT);
        }

        Extension newExtension = new Extension(extensionName);
        extensionRepository.save(newExtension);

    }

    @Transactional
    public void deleteExtension(Long id) {
        Extension extension = extensionRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.EXTENSION_NOT_FOUND));
        if (!extension.isCustom()) {
            throw new BusinessException(ErrorCode.EXTENSION_FIXED_EXTENSION_CANNOT_BE_DELETED);
        }
        extensionRepository.deleteById(id);
    }

    @Transactional
    public void toggleFixedExtension(Long id, boolean isOn) {
        Extension extension = extensionRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.EXTENSION_NOT_FOUND));

        extension.toggleOn(isOn);
    }
}
