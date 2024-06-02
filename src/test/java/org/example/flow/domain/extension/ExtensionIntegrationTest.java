package org.example.flow.domain.extension;

import org.example.flow.domain.extension.dto.request.ExtensionRequestDto;
import org.example.flow.domain.extension.dto.request.ExtensionToggleRequestDto;
import org.example.flow.domain.extension.dto.response.ExtensionListResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExtensionIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;


    private ResponseEntity<ExtensionListResponseDto> getExtensions() {
        return restTemplate.getForEntity("/api/extensions", ExtensionListResponseDto.class);
    }

    private ResponseEntity<ExtensionListResponseDto> createExtension(String extensionName) {
        ExtensionRequestDto extensionRequestDto = new ExtensionRequestDto(extensionName);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(extensionRequestDto, headers);

        return restTemplate.postForEntity("/api/extensions", requestEntity, ExtensionListResponseDto.class);
    }

    private ResponseEntity<Void> deleteExtension(Long id) {
        return restTemplate.exchange("/api/extensions/" + id, HttpMethod.DELETE, null, Void.class);
    }

    private ResponseEntity<Void> toggleExtension(Long id, boolean isOn) {
        ExtensionToggleRequestDto extensionToggleRequestDto = new ExtensionToggleRequestDto(isOn);
        var requestEntity = new HttpEntity<>(extensionToggleRequestDto);
        return restTemplate.exchange("/api/extensions/" + id, HttpMethod.PATCH, requestEntity, Void.class);
    }

    @Nested
    @DisplayName("확장자 조회 관련 테스트")
    class ExtensionGetTest {

        @Test
        @DisplayName("확장자 조회 테스트 - 확장자 조회 성공 및 고정 확장자는 기본적으로 unCheck 되어 있어야 함")
        public void testGetExtensions() {
            var response = getExtensions();
            assertEquals(HttpStatus.OK, response.getStatusCode());

            Objects.requireNonNull(response.getBody())
                    .fixedExtensionList()
                    .forEach(
                            extension -> {
                                assertFalse(extension.isOn());
                            }
                    );
        }


    }

    @Nested
    @DisplayName("확장자 생성 관련 테스트")
    class ExtensionCreateTest {

        @Test
        @DisplayName("확장자의 최대 입력 길이 테스트 - 20자 이상의 확장자는 입력 불가능해야 함")
        public void testExtensionMaxLength() {
            String extensionNameIn = "a".repeat(20);

            var result1 = createExtension(extensionNameIn);
            assertEquals(HttpStatus.CREATED, result1.getStatusCode());


            // 생성된 확장자 삭제
            var result = getExtensions();
            var extensionId = Objects.requireNonNull(result.getBody())
                    .customExtensionList()
                    .stream()
                    .filter(extension -> extension.name().equals(extensionNameIn))
                    .findFirst()
                    .orElseThrow(
                            () -> new RuntimeException("Extension not found")
                    )
                    .id();

            deleteExtension(extensionId);


            String extensionNameExceed = "a".repeat(21);

            var result2 = createExtension(extensionNameExceed);
            assertEquals(HttpStatus.BAD_REQUEST, result2.getStatusCode());
        }

        @Test
        @DisplayName("확장자의 최소 입력 길이 테스트 - 1자 미만의 확장자는 입력 불가능해야 함")
        public void testExtensionLength() {
            String minimumExtensionName = "";

            var result = createExtension(minimumExtensionName);
            assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());

        }

        @Test
        @DisplayName("확장자의 중복 입력 테스트 - 중복된 확장자는 입력 불가능해야 함")
        public void testExtensionDuplicate() {
            String extensionName = "pdf";

            var result1 = createExtension(extensionName);
            assertEquals(HttpStatus.CREATED, result1.getStatusCode());

            var result2 = createExtension(extensionName);
            assertEquals(HttpStatus.CONFLICT, result2.getStatusCode());

            // 생성된 확장자 삭제
            var result = getExtensions();
            var extensionId = Objects.requireNonNull(result.getBody())
                    .customExtensionList()
                    .stream()
                    .filter(extension -> extension.name().equals(extensionName))
                    .findFirst()
                    .orElseThrow(
                            () -> new RuntimeException("Extension not found")
                    )
                    .id();
            deleteExtension(extensionId);
        }

        @Test
        @DisplayName("커스텀 확장자는 200개 이상 생성 불가능해야 함")
        public void testExtensionMaxCount() {
            var extensions = getExtensions();
            var currentCustomExtensionCount = (long) Objects.requireNonNull(extensions.getBody()).customExtensionList().size();

            for (int i = 0; i < 200 - currentCustomExtensionCount; i++) {
                var result = createExtension("pdf" + i);
                assertEquals(HttpStatus.CREATED, result.getStatusCode());
            }

            var result = createExtension("pdf200");
            assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());

            var extensionList = getExtensions();
            Objects.requireNonNull(extensionList.getBody())
                    .customExtensionList()
                    .forEach(
                            extension -> {
                                deleteExtension(extension.id());
                            }
                    );
        }

    }


    @Nested
    @DisplayName("확장자 삭제 관련 테스트")
    class ExtensionDeleteTest {

        @Test
        @DisplayName("확장자 삭제 테스트 - 확장자 삭제 성공")
        public void testDeleteExtension() {
            createExtension("cda");
            var result = getExtensions();
            var extensionId = Objects.requireNonNull(result.getBody())
                    .customExtensionList()
                    .stream()
                    .filter(extension -> extension.name().equals("cda"))
                    .findFirst()
                    .orElseThrow(
                            () -> new RuntimeException("Extension not found")
                    )
                    .id();

            var deleteResult = deleteExtension(extensionId);
            assertEquals(HttpStatus.NO_CONTENT, deleteResult.getStatusCode());
        }

        @Test
        @DisplayName("확장자 삭제 테스트 - 존재하지 않는 확장자 삭제 시도")
        public void testDeleteExtensionNotExist() {
            var deleteResult = deleteExtension(999L);
            assertEquals(HttpStatus.NOT_FOUND, deleteResult.getStatusCode());
        }

        @Test
        @DisplayName("확장자 삭제 테스트 - 고정 확장자 삭제 시도")
        public void testDeleteFixedExtension() {
            var extensions = getExtensions();
            var fixedExtensionId = Objects.requireNonNull(extensions.getBody()).fixedExtensionList().getFirst().id();

            var deleteResult = deleteExtension(fixedExtensionId);
            assertEquals(HttpStatus.BAD_REQUEST, deleteResult.getStatusCode());
        }

    }


    @Nested
    @DisplayName("고정 확장자 토글 관련 테스트")
    class ExtensionToggleTest {

        @BeforeEach
        public void setUp() {
            restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        }

        @Test
        @DisplayName("고정 확장자 토글 테스트 - 고정 확장자 토글 성공")
        public void testToggleFixedExtension() {
            var extensions = getExtensions();
            var fixedExtensionId = Objects.requireNonNull(extensions.getBody()).fixedExtensionList().getFirst().id();

            var response = toggleExtension(fixedExtensionId, true);
            assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());


            var response2 = toggleExtension(fixedExtensionId, false);
            assertEquals(HttpStatus.NO_CONTENT, response2.getStatusCode());
        }

        @Test
        @DisplayName("고정 확장자 토글 테스트 - 존재하지 않는 확장자 토글 시도")
        public void testToggleExtensionNotExist() {
            var response = toggleExtension(999L, true);
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        }

        @Test
        @DisplayName("고정 확장자 토글 테스트 - 커스텀 확장자 토글 시도")
        public void testToggleCustomExtension() {
            createExtension("cda");
            var result = getExtensions();
            var extensionId = Objects.requireNonNull(result.getBody())
                    .customExtensionList()
                    .stream()
                    .filter(extension -> extension.name().equals("cda"))
                    .findFirst()
                    .orElseThrow(
                            () -> new RuntimeException("Extension not found")
                    )
                    .id();

            var response = toggleExtension(extensionId, true);
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        }

    }

}
