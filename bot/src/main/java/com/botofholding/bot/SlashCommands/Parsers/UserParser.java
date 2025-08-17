package com.botofholding.bot.SlashCommands.Parsers;


import com.botofholding.contract.DTO.Response.UserSettingsDto;

public interface UserParser extends Parser, EphemeralSettingProvider {

    @Override
    default boolean extractEphemeralSetting(UserSettingsDto settings) {
        return settings.isEphemeralUser();
    }
}
