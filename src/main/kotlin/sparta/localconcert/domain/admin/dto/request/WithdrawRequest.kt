package sparta.localconcert.domain.admin.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class WithdrawRequest(
    @Schema(description = "회원 비밀번호", example = "pass1234")
    @NotBlank
    @field:Pattern(
        regexp = "^[a-zA-Z0-9!@#$%^&*]{8,16}$",
        message = "비밀번호는 8~16자의 영문 대소문자, 숫자, 특수문자로 이루어져야 합니다."
    )
    val password: String
)