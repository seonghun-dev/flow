package org.example.flow.common.annotaion.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.example.flow.common.annotaion.ValidFileExtension;
import org.example.flow.common.exception.BusinessException;
import org.example.flow.common.exception.ErrorCode;
import org.example.flow.domain.extension.entity.Extension;
import org.example.flow.domain.extension.repository.ExtensionRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RequiredArgsConstructor
public class FileExtensionValidator implements ConstraintValidator<ValidFileExtension, MultipartFile> {

    private final ExtensionRepository extensionRepository;

    @Override
    @Transactional(readOnly = true)
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        String fileExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());

        if (fileExtension == null) {
            return false;
        }

        Optional<Extension> extension = extensionRepository.findByName(fileExtension);

        if (extension.isPresent()) {
            if (extension.get().isOn()) {
                throw new BusinessException(
                        ErrorCode.FILE_NOT_VALID_EXTENSION
                );
            }
        }
        return true;
    }
}
