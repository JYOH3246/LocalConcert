package sparta.localconcert.domain.admin.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class SignUpWithLoginRequest(
    @Schema(description = "회원 메일", example = "example@mail.com")
    @NotBlank
    @field:Email(
        regexp = "^([a-zA-Z0-9+-_.]{3,30})+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$",
        message = "메일 주소 형식을 넣어주세요."
    )
    val email: String,


    @Schema(description = "회원 비밀번호", example = "pass1234")
    @NotBlank
    @field:Pattern(
        regexp = "^[a-zA-Z0-9!@#$%^&*]{8,16}$",
        message = "비밀번호는 8~16자의 영문 대소문자, 숫자, 특수문자로 이루어져야 합니다."
    )
    val password: String
)

