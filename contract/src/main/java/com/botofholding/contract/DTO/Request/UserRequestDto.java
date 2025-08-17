package com.botofholding.contract.DTO.Request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {

//    @NotNull(message = "Discord ID cannot be null")
//    private Long discordId;

    @NotNull(message = "Boh User Name cannot be null")
    @Size(min = 2, max = 32, message = "Username must be between 2 and 32 characters.")
    private String bohUserName;

    private String bohUserTag;

    private String bohGlobalUserName;

    public void setBohUserName(String bohUserName) {
        this.bohUserName = (bohUserName == null) ? null : bohUserName.trim();
    }
    public UserRequestDto(String bohUserName, String bohUserTag) {
        this.bohUserName = bohUserName;
        this.bohUserTag = bohUserTag;
    }

    public UserRequestDto(String bohUserName, String bohUserTag, Optional<String> bohGlobalUserName) {
        this(bohUserName, bohUserTag);

        if (bohGlobalUserName.isPresent())
            this.bohGlobalUserName = bohGlobalUserName.get();
    }
}
